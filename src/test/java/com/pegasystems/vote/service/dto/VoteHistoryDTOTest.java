package com.pegasystems.vote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pegasystems.vote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoteHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoteHistoryDTO.class);
        VoteHistoryDTO voteHistoryDTO1 = new VoteHistoryDTO();
        voteHistoryDTO1.setId(1L);
        VoteHistoryDTO voteHistoryDTO2 = new VoteHistoryDTO();
        assertThat(voteHistoryDTO1).isNotEqualTo(voteHistoryDTO2);
        voteHistoryDTO2.setId(voteHistoryDTO1.getId());
        assertThat(voteHistoryDTO1).isEqualTo(voteHistoryDTO2);
        voteHistoryDTO2.setId(2L);
        assertThat(voteHistoryDTO1).isNotEqualTo(voteHistoryDTO2);
        voteHistoryDTO1.setId(null);
        assertThat(voteHistoryDTO1).isNotEqualTo(voteHistoryDTO2);
    }
}
