package com.ds.project.apothecary.entities;

import com.ds.project.apothecary.enums.Gender;
import com.ds.project.apothecary.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * The type User.
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ap_user")
public class User {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long
            id;

    /**
     * The Username.
     */
    @Column(nullable = false,
            unique = true)
    private String
            username;

    /**
     * The Password.
     */
    private String
            password;

    /**
     * The First name.
     */
    private String
            firstName;

    /**
     * The Last name.
     */
    private String
            lastName;

    /**
     * The Birth date.
     */
    private Date
            birthDate;

    /**
     * The Gender.
     */
    private Gender
            gender;

    /**
     * The Address.
     */
    @ManyToOne(fetch = FetchType.LAZY,
            optional = true,
            cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id",
            nullable = true)
    private Address
            address;

    /**
     * The User type.
     */
    private UserType
            userType;
}
