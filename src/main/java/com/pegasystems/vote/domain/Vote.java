package com.pegasystems.vote.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * A Vote.
 */
@Entity
@Table(name = "vote")
@Getter @Setter
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // I use this key to keep count in cache
    @Column(name = "key", nullable = false)
    private String key;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    @NotNull
    @Column(name = "country_from", nullable = false)
    private String countryFrom;

    @NotNull
    @Column(name = "country_to", nullable = false)
    private String countryTo;

    @Column(name = "count")
    private Long count;

    public Vote id(Long id) {
        this.setId(id);
        return this;
    }
    public Vote key(String key) {
        this.setKey(key);
        return this;
    }
    public Vote year(Integer year) {
        this.setYear(year);
        return this;
    }

    public Vote countryFrom(String countryFrom) {
        this.setCountryFrom(countryFrom);
        return this;
    }

    public Vote countryTo(String countryTo) {
        this.setCountryTo(countryTo);
        return this;
    }

    public Vote count(Long count) {
        this.setCount(count);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vote)) {
            return false;
        }
        return id != null && id.equals(((Vote) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Vote{" +
            ", key=" + getKey() +
            ", year=" + getYear() +
            ", countryFrom='" + getCountryFrom() + "'" +
            ", countryTo='" + getCountryTo() + "'" +
            ", count=" + getCount() +
            "}";
    }
}
