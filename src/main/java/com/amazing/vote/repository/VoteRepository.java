package com.amazing.vote.repository;

import com.amazing.vote.domain.Vote;
import com.amazing.vote.service.dto.ReportConverter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the Vote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByKey(String key);

    @Query(value = "SELECT * FROM ( " +
                   "select  voted.countryTo as countryTo, voted.voteNumber as voteNumber, DENSE_RANK() OVER (ORDER BY voted.voteNumber DESC) as voteNumberRank " +
                   "from ( SELECT v.country_to as countryTo, sum(v.count) as voteNumber " +
                          "FROM vote as v " +
                          "WHERE v.year = :year and v.country_from = :countryFrom " +
                          "GROUP BY v.country_from, v.country_to " +
                          "ORDER by voteNumber DESC) as voted " +
                    ") as rank where rank.voteNumberRank <= 3 ",
            nativeQuery = true)
    List<ReportConverter> topThreeFavoriteSongsEachCountry(Integer year , String countryFrom);

     @Query(value = "SELECT * FROM ( " +
                    "select  voted.countryTo as countryTo, voted.voteNumber as voteNumber, DENSE_RANK() OVER (ORDER BY voted.voteNumber DESC) as voteNumberRank " +
                    "from ( SELECT v.country_to as countryTo, sum(v.count) as voteNumber " +
                          "FROM vote as v " +
                          "WHERE v.year = :year " +
                          "GROUP BY v.country_to " +
                          "ORDER by voteNumber DESC) as voted " +
                    ") as rank where rank.voteNumberRank <= 3 ",
             nativeQuery = true)
    List<ReportConverter> annualReport(Integer year);

}
