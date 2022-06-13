package com.pegasystems.vote.service.mapper;

import com.pegasystems.vote.domain.VoteHistory;
import com.pegasystems.vote.service.dto.VoteHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VoteHistory} and its DTO {@link VoteHistoryDTO}.
 */
@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoteHistoryMapper extends EntityMapper<VoteHistoryDTO, VoteHistory> {}
