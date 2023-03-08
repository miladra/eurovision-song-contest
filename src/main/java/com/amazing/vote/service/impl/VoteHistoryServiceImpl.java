package com.amazing.vote.service.impl;

import com.amazing.vote.domain.VoteHistory;
import com.amazing.vote.repository.VoteHistoryRepository;
import com.amazing.vote.service.mapper.VoteHistoryMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.amazing.vote.service.VoteHistoryService;
import com.amazing.vote.service.VoteService;
import com.amazing.vote.service.dto.VoteDTO;
import com.amazing.vote.service.dto.VoteHistoryDTO;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VoteHistory}.
 */
@Service
@Transactional
public class VoteHistoryServiceImpl implements VoteHistoryService {

    private final Logger log = LoggerFactory.getLogger(VoteHistoryServiceImpl.class);

    private final VoteHistoryRepository voteHistoryRepository;

    private final VoteHistoryMapper voteHistoryMapper;
    private final Cache<String, Long> cache;

    private final VoteService voteService;

    public VoteHistoryServiceImpl(VoteHistoryRepository voteHistoryRepository, VoteHistoryMapper voteHistoryMapper, Cache<String, Long> cache, VoteService voteService) {
        this.voteHistoryRepository = voteHistoryRepository;
        this.voteHistoryMapper = voteHistoryMapper;
        this.cache = cache;
        this.voteService = voteService;
    }

    @Override
    @Transactional
    public VoteHistoryDTO save(Integer year, VoteHistoryDTO voteHistoryDTO) throws Exception {
        log.debug("Request to save VoteHistory : {}", voteHistoryDTO);
        String cacheKey = getKey(year, voteHistoryDTO);
        Long cacheValue = cache.getIfPresent(cacheKey);
        try {
            if (Objects.isNull(cacheValue)) {
                Optional<VoteDTO> voteDTO = voteService.findByKey(cacheKey);
                if (voteDTO.isPresent()) {
                    cache.put(cacheKey, voteDTO.get().getCount() + 1);
                    voteDTO.get().setCount(voteDTO.get().getCount() + 1);
                    voteService.update(voteDTO.get());
                } else {
                    VoteDTO newVoteDTO = new VoteDTO();
                    newVoteDTO.setKey(cacheKey);
                    newVoteDTO.setYear(year);
                    newVoteDTO.setCount(Long.valueOf(1));
                    newVoteDTO.setCountryFrom(voteHistoryDTO.getCountryFrom());
                    newVoteDTO.setCountryTo(voteHistoryDTO.getCountryTo());
                    voteService.save(newVoteDTO);
                    cache.put(cacheKey, Long.valueOf(1));
                }
            } else {
                VoteDTO newVoteDTO = new VoteDTO();
                newVoteDTO.setKey(cacheKey);
                newVoteDTO.setYear(year);
                newVoteDTO.setCountryFrom(voteHistoryDTO.getCountryFrom());
                newVoteDTO.setCountryTo(voteHistoryDTO.getCountryTo());
                newVoteDTO.setCount(cacheValue + 1);
                voteService.update(newVoteDTO);
                cache.put(cacheKey, cacheValue + 1);
            }

            VoteHistory voteHistory = voteHistoryMapper.toEntity(voteHistoryDTO);
            voteHistory.setYear(year);
            voteHistory = voteHistoryRepository.save(voteHistory);
            return voteHistoryMapper.toDto(voteHistory);
        } catch (Exception ex) {
            cache.put(cacheKey, cacheValue);
            throw new Exception(ex.getMessage());
        }
    }


    /**
     * Find VoteHistory by User and Year
     *
     * @param userId the userId of the entity.
     * @param year   the year   of the entity.
     */
    @Override
    public Optional<VoteHistoryDTO> findByUserIDAndYear(Integer userId, Integer year) {
        return voteHistoryRepository.findByUserIDAndYear(userId , year).map(voteHistoryMapper::toDto);
    }
    
    private String getKey(Integer year, VoteHistoryDTO voteHistoryDTO) {
        StringBuilder sb = new StringBuilder();
        return sb.append(year)
                .append(voteHistoryDTO.getCountryFrom())
                .append(voteHistoryDTO.getCountryTo()).toString();
    }  
}
