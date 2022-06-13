package com.pegasystems.vote.repository;

import com.pegasystems.vote.domain.VoteHistory;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the VoteHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoteHistoryRepository extends JpaRepository<VoteHistory, Long> {
    Optional<VoteHistory> findByUserIDAndYear(Integer userId,Integer year);
}
