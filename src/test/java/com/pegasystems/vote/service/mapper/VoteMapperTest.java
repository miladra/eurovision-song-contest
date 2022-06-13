package com.pegasystems.vote.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoteMapperTest {

    private VoteMapper voteMapper;

    @BeforeEach
    public void setUp() {
        voteMapper = new VoteMapperImpl();
    }
}
