package com.pegasystems.vote.service;

import com.pegasystems.vote.domain.VoteHistory;
import com.pegasystems.vote.service.dto.VoteHistoryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pegasystems.vote.domain.VoteHistory}.
 */
public interface VoteHistoryService {
    /**
     * Save a voteHistory.
     *
     * @param voteHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    VoteHistoryDTO save(Integer year,VoteHistoryDTO voteHistoryDTO) throws Exception;


    /**
     * Find VoteHistory by User and Year
     *
     * @param userId the userId of the entity.
     * @param year   the year   of the entity.
     */
    Optional<VoteHistoryDTO> findByUserIDAndYear(Integer userId, Integer year);
}
