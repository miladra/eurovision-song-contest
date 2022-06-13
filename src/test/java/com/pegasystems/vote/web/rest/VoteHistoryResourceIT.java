package com.pegasystems.vote.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.benmanes.caffeine.cache.Cache;
import com.pegasystems.vote.IntegrationTest;
import com.pegasystems.vote.domain.VoteHistory;
import com.pegasystems.vote.repository.VoteHistoryRepository;
import com.pegasystems.vote.service.dto.VoteHistoryDTO;
import com.pegasystems.vote.service.mapper.VoteHistoryMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VoteHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
class VoteHistoryResourceIT {

    private static final Integer DEFAULT_USER_ID = 1;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_USER_ID = 2;
    private static final Integer UPDATED_YEAR = 2;

    private static final String DEFAULT_COUNTRY_FROM = "AAAAAAAAAA";

    private static final String DEFAULT_COUNTRY_TO = "BBBBBBBBBB";

    private static final String UPDATED_COUNTRY_FROM = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_TO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/v1/votes";
    private static final String ENTITY_API_URL_YEAR = ENTITY_API_URL + "/{year}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VoteHistoryRepository voteHistoryRepository;

    @Autowired
    private VoteHistoryMapper voteHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoteHistoryMockMvc;

    private VoteHistory voteHistory;

    @Autowired
    private Cache<String, Long> cache;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoteHistory createEntity(EntityManager em) {
        VoteHistory voteHistory = new VoteHistory()
            .userID(DEFAULT_USER_ID)
            .year(DEFAULT_YEAR)
            .countryFrom(DEFAULT_COUNTRY_FROM)
            .countryTo(DEFAULT_COUNTRY_TO);
        return voteHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoteHistory createUpdatedEntity(EntityManager em) {
        VoteHistory voteHistory = new VoteHistory()
            .userID(UPDATED_USER_ID)
            .year(UPDATED_YEAR)
            .countryFrom(UPDATED_COUNTRY_FROM)
            .countryTo(UPDATED_COUNTRY_TO);
        return voteHistory;
    }

    @BeforeEach
    public void initTest() {
        voteHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createVoteHistory() throws Exception {
        cache.invalidateAll();
        int databaseSizeBeforeCreate = voteHistoryRepository.findAll().size();
        // Create the VoteHistory
        VoteHistoryDTO voteHistoryDTO = voteHistoryMapper.toDto(voteHistory);
        restVoteHistoryMockMvc
            .perform(
                post(ENTITY_API_URL_YEAR,DEFAULT_YEAR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(voteHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VoteHistory in the database
        List<VoteHistory> voteHistoryList = voteHistoryRepository.findAll();
        assertThat(voteHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        VoteHistory testVoteHistory = voteHistoryList.get(voteHistoryList.size() - 1);
        assertThat(testVoteHistory.getUserID()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testVoteHistory.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testVoteHistory.getCountryFrom()).isEqualTo(DEFAULT_COUNTRY_FROM);
        assertThat(testVoteHistory.getCountryTo()).isEqualTo(DEFAULT_COUNTRY_TO);
    }

    @Test
    @Transactional
    void createVoteHistoryWithExistingId() throws Exception {
        // Create the VoteHistory with an existing ID
        voteHistory.setId(1L);
        VoteHistoryDTO voteHistoryDTO = voteHistoryMapper.toDto(voteHistory);

        int databaseSizeBeforeCreate = voteHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoteHistoryMockMvc
            .perform(
                    post(ENTITY_API_URL_YEAR,DEFAULT_YEAR)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.convertObjectToJsonBytes(voteHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoteHistory in the database
        List<VoteHistory> voteHistoryList = voteHistoryRepository.findAll();
        assertThat(voteHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUserIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = voteHistoryRepository.findAll().size();
        // set the field null
        voteHistory.setUserID(null);

        // Create the VoteHistory, which fails.
        VoteHistoryDTO voteHistoryDTO = voteHistoryMapper.toDto(voteHistory);

        restVoteHistoryMockMvc
            .perform(
                    post(ENTITY_API_URL_YEAR,DEFAULT_YEAR)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.convertObjectToJsonBytes(voteHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<VoteHistory> voteHistoryList = voteHistoryRepository.findAll();
        assertThat(voteHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = voteHistoryRepository.findAll().size();
        // set the field null
        voteHistory.setYear(null);

        // Create the VoteHistory, which fails.
        VoteHistoryDTO voteHistoryDTO = voteHistoryMapper.toDto(voteHistory);

        restVoteHistoryMockMvc
            .perform(
                    post(ENTITY_API_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.convertObjectToJsonBytes(voteHistoryDTO))
            )
            .andExpect(status().isNotFound());

        List<VoteHistory> voteHistoryList = voteHistoryRepository.findAll();
        assertThat(voteHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = voteHistoryRepository.findAll().size();
        // set the field null
        voteHistory.setCountryFrom(null);

        // Create the VoteHistory, which fails.
        VoteHistoryDTO voteHistoryDTO = voteHistoryMapper.toDto(voteHistory);

        restVoteHistoryMockMvc
            .perform(
                post(ENTITY_API_URL_YEAR , DEFAULT_YEAR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(voteHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<VoteHistory> voteHistoryList = voteHistoryRepository.findAll();
        assertThat(voteHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryToIsRequired() throws Exception {
        int databaseSizeBeforeTest = voteHistoryRepository.findAll().size();
        // set the field null
        voteHistory.setCountryTo(null);

        // Create the VoteHistory, which fails.
        VoteHistoryDTO voteHistoryDTO = voteHistoryMapper.toDto(voteHistory);

        restVoteHistoryMockMvc
            .perform(
                post(ENTITY_API_URL_YEAR , DEFAULT_YEAR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(voteHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<VoteHistory> voteHistoryList = voteHistoryRepository.findAll();
        assertThat(voteHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getNonExistingVoteHistory() throws Exception {
        // Get the voteHistory
        restVoteHistoryMockMvc.perform(get(ENTITY_API_URL_YEAR, Long.MAX_VALUE)).andExpect(status().isBadRequest());
    }
}
