package com.pegasystems.vote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pegasystems.vote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoteDTO.class);
        VoteDTO voteDTO1 = new VoteDTO();
        voteDTO1.setKey("1L");
        VoteDTO voteDTO2 = new VoteDTO();
        assertThat(voteDTO1).isNotEqualTo(voteDTO2);
        voteDTO2.setKey(voteDTO1.getKey());
        assertThat(voteDTO1).isEqualTo(voteDTO2);
        voteDTO2.setKey("2L");
        assertThat(voteDTO1).isNotEqualTo(voteDTO2);
        voteDTO1.setKey(null);
        assertThat(voteDTO1).isNotEqualTo(voteDTO2);
    }
}
