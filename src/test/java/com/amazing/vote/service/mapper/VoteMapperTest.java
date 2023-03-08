package com.amazing.vote.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

class VoteMapperTest {

    private VoteMapper voteMapper;

    @BeforeEach
    public void setUp() {
        voteMapper = new VoteMapperImpl();
    }
}
