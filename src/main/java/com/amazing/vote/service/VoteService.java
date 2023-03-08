package com.amazing.vote.service;

import com.amazing.vote.domain.Vote;
import com.amazing.vote.service.dto.VoteDTO;

import java.util.Map;
import java.util.Optional;

/**
 * Service Interface for managing {@link Vote}.
 */
public interface VoteService {

    /**
     * Annual Report.
     *
     * @param year the id of the entity.
     */
    Map<String, String> annualReport(Integer year);

    /**
     * Top Three Favorite Songs.
     *
     * @param year the id of the entity.
     */
    Map<String, String> topThreeFavoriteSongsEachCountry(Integer year, String countryFrom);

    /**
     * Get the vote of Year, CountryFrom and CountryTo
     *
     * @param key the key of the entity.
     * @return the entity.
     */
    Optional<VoteDTO> findByKey(String key);

    /**
     * Save a vote.
     *
     * @param voteDTO the entity to save.
     * @return the persisted entity.
     */
    VoteDTO save(VoteDTO voteDTO);

    /**
     * Updates a vote.
     *
     * @param voteDTO the entity to update.
     * @return the persisted entity.
     */
    VoteDTO update(VoteDTO voteDTO);
}
