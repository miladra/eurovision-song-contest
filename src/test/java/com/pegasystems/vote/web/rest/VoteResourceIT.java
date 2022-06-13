package com.pegasystems.vote.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pegasystems.vote.IntegrationTest;
import com.pegasystems.vote.domain.Vote;
import com.pegasystems.vote.repository.VoteRepository;
import com.pegasystems.vote.service.VoteService;
import com.pegasystems.vote.service.dto.VoteDTO;
import com.pegasystems.vote.service.mapper.VoteMapper;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VoteResourceIT} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
class VoteResourceIT {

    private static final Integer DEFAULT_YEAR = 2020;
    private static final Integer UPDATED_YEAR = 2;

    private static final String DEFAULT_COUNTRY_FROM = "AAAAAAAAA";
    private static final String UPDATED_COUNTRY_FROM = "CCCCCCCCC";

    private static final String DEFAULT_COUNTRY_TO   = "BBBBBBBBB";
    private static final String UPDATED_COUNTRY_TO   = "DDDDDDDDD";

    private static final Long DEFAULT_COUNT = 1L;
    private static final Long UPDATED_COUNT = 2L;

    private static final String ENTITY_API_URL = "/api/v1/votes";

    private static final String ENTITY_API_URL_YEAR = ENTITY_API_URL + "/{year}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MockMvc restVoteMockMvc;
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private VoteService voteService;

    private Vote vote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vote createEntity(EntityManager em) {
        Vote vote = new Vote()
                .year(DEFAULT_YEAR)
                .countryFrom(DEFAULT_COUNTRY_FROM)
                .countryTo(DEFAULT_COUNTRY_TO)
                .count(DEFAULT_COUNT);
        return vote;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vote createUpdatedEntity(EntityManager em) {
        Vote vote = new Vote()
                .id(1l)
                .year(UPDATED_YEAR)
                .countryFrom(UPDATED_COUNTRY_FROM)
                .countryTo(UPDATED_COUNTRY_TO)
                .count(UPDATED_COUNT);
        return vote;
    }

    @BeforeEach
    public void initTest() {
        vote = createEntity(em);
    }

    @Test
    @Transactional
    void createVote() throws Exception {
        int databaseSizeBeforeCreate = voteRepository.findAll().size();
        // Create the Vote
        VoteDTO voteDTO = voteMapper.toDto(vote);
        voteDTO.setKey("2018" + voteDTO.getCountryFrom() + voteDTO.getCountryTo());
        voteService.save(voteDTO);

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeCreate + 1);
        Vote testVote = voteList.get(voteList.size() - 1);
        assertThat(testVote.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testVote.getCountryFrom()).isEqualTo(DEFAULT_COUNTRY_FROM);
        assertThat(testVote.getCountryTo()).isEqualTo(DEFAULT_COUNTRY_TO);
        assertThat(testVote.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    void getAnnualReport() throws Exception {
        int databaseSizeBeforeCreate = voteRepository.findAll().size();
        // Create the Vote
        VoteDTO voteDTO = voteMapper.toDto(vote);
        voteDTO.setKey("2020" + voteDTO.getCountryFrom() + voteDTO.getCountryTo());
        voteDTO.setCount(10l);
        voteService.save(voteDTO);

        voteDTO.setCountryFrom("CCCCCCCCC");
        voteDTO.setKey("2020" + voteDTO.getCountryFrom() + voteDTO.getCountryTo());
        voteDTO.setCount(10l);
        voteService.save(voteDTO);


        voteDTO.setCountryFrom("DDDDDDDD");
        voteDTO.setCountryTo("EEEEEEEE");
        voteDTO.setKey("2020" + voteDTO.getCountryFrom() + voteDTO.getCountryTo());
        voteDTO.setCount(10l);
        voteService.save(voteDTO);

        restVoteMockMvc
                .perform(
                        get(ENTITY_API_URL_YEAR , DEFAULT_YEAR)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.['first']").value("BBBBBBBBB"))
                .andExpect(jsonPath("$.['second']").value("EEEEEEEE"));

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeCreate + 3);
    }

    @Test
    @Transactional
    void topThreeFavoriteSongsEachCountry() throws Exception {
        int databaseSizeBeforeCreate = voteRepository.findAll().size();
        // Create the Vote
        VoteDTO voteDTO = voteMapper.toDto(vote);
        voteDTO.setKey("2020" + voteDTO.getCountryFrom() + voteDTO.getCountryTo());
        voteDTO.setCount(10l);
        voteService.save(voteDTO);

        voteDTO.setCountryTo("CCCCCCCC");
        voteDTO.setKey("2020" + voteDTO.getCountryFrom() + voteDTO.getCountryTo());
        voteDTO.setCount(9l);
        voteService.save(voteDTO);


        voteDTO.setCountryTo("EEEEEEEE");
        voteDTO.setKey("2020" + voteDTO.getCountryFrom() + voteDTO.getCountryTo());
        voteDTO.setCount(8l);
        voteService.save(voteDTO);

        restVoteMockMvc
                .perform(
                        get(ENTITY_API_URL_YEAR + "/" + voteDTO.getCountryFrom(), DEFAULT_YEAR)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.['first']").value("BBBBBBBBB"))
                .andExpect(jsonPath("$.['second']").value("CCCCCCCC"))
                .andExpect(jsonPath("$.['third']").value("EEEEEEEE"));

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeCreate + 3);
    }
}
