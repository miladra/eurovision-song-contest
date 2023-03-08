package com.amazing.vote.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

class VoteHistoryMapperTest {

    private VoteHistoryMapper voteHistoryMapper;

    @BeforeEach
    public void setUp() {
        voteHistoryMapper = new VoteHistoryMapperImpl();
    }
}
