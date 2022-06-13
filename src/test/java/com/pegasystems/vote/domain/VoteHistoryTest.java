package com.pegasystems.vote.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pegasystems.vote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoteHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoteHistory.class);
        VoteHistory voteHistory1 = new VoteHistory();
        voteHistory1.setId(1L);
        VoteHistory voteHistory2 = new VoteHistory();
        voteHistory2.setId(voteHistory1.getId());
        assertThat(voteHistory1).isEqualTo(voteHistory2);
        voteHistory2.setId(2L);
        assertThat(voteHistory1).isNotEqualTo(voteHistory2);
        voteHistory1.setId(null);
        assertThat(voteHistory1).isNotEqualTo(voteHistory2);
    }
}
