package com.ds.project.apothecary.security;

import com.ds.project.apothecary.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The type User details.
 */
public class UserDetailsImpl
        implements UserDetails {
    /**
     * The constant serialVersionUID.
     */
    private static final long
            serialVersionUID =
            1L;

    /**
     * The Id.
     */
    private final Long
            id;

    /**
     * The Username.
     */
    private final String
            username;

    /**
     * The Password.
     */
    @JsonIgnore
    private final String
            password;

    /**
     * The First name.
     */
    private final String
            firstName;

    /**
     * The Last name.
     */
    private final String
            lastName;

    /**
     * The Authorities.
     */
    private final Collection<? extends GrantedAuthority>
            authorities;

    /**
     * Instantiates a new User details.
     *
     * @param pId          the id
     * @param pUsername    the username
     * @param pPassword    the password
     * @param pAuthorities the authorities
     * @param pFirstName   the first name
     * @param pLastName    the last name
     */
    public UserDetailsImpl(
            final Long pId,
            final String pUsername,
            final String pPassword,
            final Collection<? extends GrantedAuthority> pAuthorities,
            final String pFirstName,
            final String pLastName) {
        this.id =
                pId;
        this.username =
                pUsername;
        this.password =
                pPassword;
        this.authorities =
                pAuthorities;
        this.firstName =
                pFirstName;
        this.lastName =
                pLastName;
    }

    /**
     * Build user details.
     *
     * @param user the user
     * @return the user details
     */
    public static UserDetailsImpl build(final User user) {
        List<GrantedAuthority>
                authorities =
                new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserType().name()));

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities,
                user.getFirstName(),
                user.getLastName());
    }

    /**
     * Gets authorities.
     *
     * @return the authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Is account non expired boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Is account non locked boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Is credentials non expired boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Is enabled boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }
}
