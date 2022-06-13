package com.pegasystems.vote.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.pegasystems.vote.domain.Vote} entity.
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO implements Serializable {

    @NotNull
    private String key;

    @NotNull
    private Integer year;

    @NotNull
    private String countryFrom;

    @NotNull
    private String countryTo;

    private Long count;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoteDTO)) {
            return false;
        }

        VoteDTO voteDTO = (VoteDTO) o;
        if (this.key == null) {
            return false;
        }
        return Objects.equals(this.key, voteDTO.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.key);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoteDTO{" +
            "key=" + getKey() +
            ", year=" + getYear() +
            ", countryFrom='" + getCountryFrom() + "'" +
            ", countryTo='" + getCountryTo() + "'" +
            ", count=" + getCount() +
            "}";
    }
}
