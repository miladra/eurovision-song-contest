package com.pegasystems.vote.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pegasystems.vote.domain.Vote} entity.
 */
public interface ReportConverter {

     String getCountryTo();
     Long getVoteNumber();
     Integer getVoteNumberRank();

}
