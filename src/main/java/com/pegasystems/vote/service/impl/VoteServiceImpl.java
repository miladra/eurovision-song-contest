package com.pegasystems.vote.service.impl;

import com.pegasystems.vote.domain.Vote;
import com.pegasystems.vote.repository.VoteRepository;
import com.pegasystems.vote.service.VoteService;
import com.pegasystems.vote.service.dto.ReportConverter;
import com.pegasystems.vote.service.dto.VoteDTO;
import com.pegasystems.vote.service.mapper.VoteMapper;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Vote}.
 */
@Service
@Transactional
public class VoteServiceImpl implements VoteService {

    private final Logger log = LoggerFactory.getLogger(VoteServiceImpl.class);
    String[] words = new String[]{"first" , "second" , "third"};
    private final VoteRepository voteRepository;

    private final VoteMapper voteMapper;

    public VoteServiceImpl(VoteRepository voteRepository, VoteMapper voteMapper) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
    }

    @Override
    public Map<String, String> annualReport(Integer year) {
        log.debug("Request to get annualReport : {}", year);
        List<ReportConverter> queryResult = voteRepository.annualReport(year);
        Map<String , String> result = new TreeMap<>();
        queryResult.forEach(t-> log.info(t.getCountryTo() +":" + t.getVoteNumberRank() +":" + t.getVoteNumber() +":"));
        queryResult.forEach(t-> {
                    if(result.containsKey(words[t.getVoteNumberRank() - 1])){
                        result.put(words[t.getVoteNumberRank() - 1], result.get(words[t.getVoteNumberRank() - 1]) +","+t.getCountryTo());
                    } else {
                        result.put(words[t.getVoteNumberRank() - 1], t.getCountryTo());
                    }
                }
        );
        return result;
    }

    @Override
    public Map<String, String> topThreeFavoriteSongsEachCountry(Integer year, String countryFrom) {
        log.debug("Request to get top three favorite songs each country : {}", year);
        List<ReportConverter> queryResult = voteRepository.topThreeFavoriteSongsEachCountry(year , countryFrom);
        Map<String , String> result = new TreeMap<>();
        queryResult.forEach(t-> {
                    if(result.containsKey(words[t.getVoteNumberRank() - 1])){
                        result.put(words[t.getVoteNumberRank() - 1], result.get(words[t.getVoteNumberRank() - 1]) +","+t.getCountryTo());
                    } else {
                        result.put(words[t.getVoteNumberRank() - 1], t.getCountryTo());
                    }
                }
        );
        return result;
    }
    @Override
    public Optional<VoteDTO> findByKey(String key){
        log.debug("Request to get Vote : {}",key);
        Optional<Vote> vote = voteRepository.findByKey(key);
        if(vote.isPresent())
            return Optional.of(voteMapper.toDto(vote.get()));
        else
            return Optional.empty();
    }

    @Override
    public VoteDTO save(VoteDTO voteDTO) {
        log.debug("Request to save Vote : {}", voteDTO);
        Vote vote = voteMapper.toEntity(voteDTO);
        vote = voteRepository.save(vote);
        return voteMapper.toDto(vote);
    }

    @Override
    public VoteDTO update(VoteDTO voteDTO) {
        log.debug("Request to save Vote : {}", voteDTO);
        Vote vote = voteMapper.toEntity(voteDTO);
        vote = voteRepository.save(vote);
        return voteMapper.toDto(vote);
    }
}
