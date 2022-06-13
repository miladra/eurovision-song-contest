package com.pegasystems.vote.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.pegasystems.vote.domain.VoteHistory} entity.
 */
@Getter @Setter
public class VoteHistoryDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer userID;

    @NotNull
    private String countryFrom;

    @NotNull
    private String countryTo;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoteHistoryDTO)) {
            return false;
        }

        VoteHistoryDTO voteHistoryDTO = (VoteHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, voteHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoteHistoryDTO{" +
            "id=" + getId() +
            ", userID=" + getUserID() +
            ", countryFrom='" + getCountryFrom() + "'" +
            ", countryTo='" + getCountryTo() + "'" +
            "}";
    }
}
