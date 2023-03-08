package com.amazing.vote.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A VoteHistory.
 */
@Entity
@Getter @Setter
@Table(name = "vote_history")
public class VoteHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userID;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    @NotNull
    @Column(name = "country_from", nullable = false)
    private String countryFrom;

    @NotNull
    @Column(name = "country_to", nullable = false)
    private String countryTo;


    public VoteHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public VoteHistory userID(Integer userID) {
        this.setUserID(userID);
        return this;
    }

    public VoteHistory year(Integer year) {
        this.setYear(year);
        return this;
    }


    public VoteHistory countryFrom(String countryFrom) {
        this.setCountryFrom(countryFrom);
        return this;
    }

    public VoteHistory countryTo(String countryTo) {
        this.setCountryTo(countryTo);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoteHistory)) {
            return false;
        }
        return id != null && id.equals(((VoteHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoteHistory{" +
            "id=" + getId() +
            ", userID=" + getUserID() +
            ", year=" + getYear() +
            ", countryFrom='" + getCountryFrom() + "'" +
            ", countryTo='" + getCountryTo() + "'" +
            "}";
    }
}
