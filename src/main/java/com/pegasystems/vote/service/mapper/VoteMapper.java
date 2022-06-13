package com.pegasystems.vote.service.mapper;

import com.pegasystems.vote.domain.Vote;
import com.pegasystems.vote.service.dto.VoteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vote} and its DTO {@link VoteDTO}.
 */
@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoteMapper extends EntityMapper<VoteDTO, Vote> {}
