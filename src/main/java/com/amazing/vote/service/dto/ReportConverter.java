package com.amazing.vote.service.dto;

import com.amazing.vote.domain.Vote;

/**
 * A DTO for the {@link Vote} entity.
 */
public interface ReportConverter {

     String getCountryTo();
     Long getVoteNumber();
     Integer getVoteNumberRank();

}
