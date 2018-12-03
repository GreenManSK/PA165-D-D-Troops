package cz.muni.fi.pa165.w2018.dndtroops.backend.entity;

import cz.muni.fi.pa165.w2018.dndtroops.backend.enums.UserType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * User entity
 *
 * Marek Valko
 */
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String login;

    @NotNull
    private String passwordHash;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hero_id")
    private Hero hero;

    @NotNull
    @Enumerated
    private UserType type;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isUser() {
        return getType() == UserType.USER;
    }

    public boolean isAdmin() {
        return getType() == UserType.ADMIN;
    }

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getPasswordHash(), user.getPasswordHash()) &&
                getType() == user.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPasswordHash(), getType());
    }

}
