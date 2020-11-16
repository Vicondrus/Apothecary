package com.ds.project.apothecary.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * The type Address.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long
            id;

    /**
     * The Street.
     */
    private String
            street;

    /**
     * The Number.
     */
    private Integer
            number;

    /**
     * The City.
     */
    private String
            city;

    /**
     * The Country.
     */
    private String
            country;

    /**
     * The Inhabitants.
     */
    @OneToMany(mappedBy = "address",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<User>
            inhabitants;
}
