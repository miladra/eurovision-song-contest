package com.amazing.vote.service;

import com.amazing.vote.domain.VoteHistory;
import com.amazing.vote.service.dto.VoteHistoryDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link VoteHistory}.
 */
public interface VoteHistoryService {
    /**
     * Save a voteHistory.
     *
     * @param voteHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    VoteHistoryDTO save(Integer year, VoteHistoryDTO voteHistoryDTO) throws Exception;


    /**
     * Find VoteHistory by User and Year
     *
     * @param userId the userId of the entity.
     * @param year   the year   of the entity.
     */
    Optional<VoteHistoryDTO> findByUserIDAndYear(Integer userId, Integer year);
}
