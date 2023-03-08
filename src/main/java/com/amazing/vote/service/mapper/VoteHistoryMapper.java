package com.amazing.vote.service.mapper;

import com.amazing.vote.domain.VoteHistory;
import com.amazing.vote.service.dto.VoteHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VoteHistory} and its DTO {@link VoteHistoryDTO}.
 */
@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoteHistoryMapper extends EntityMapper<VoteHistoryDTO, VoteHistory> {}
