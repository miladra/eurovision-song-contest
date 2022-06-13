package com.pegasystems.vote.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pegasystems.vote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vote.class);
        Vote vote1 = new Vote();
        vote1.setId(1L);
        Vote vote2 = new Vote();
        vote2.setId(vote1.getId());
        assertThat(vote1).isEqualTo(vote2);
        vote2.setId(2L);
        assertThat(vote1).isNotEqualTo(vote2);
        vote1.setId(null);
        assertThat(vote1).isNotEqualTo(vote2);
    }
}
