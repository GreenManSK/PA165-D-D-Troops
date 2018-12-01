package cz.muni.fi.pa165.w2018.dndtroops.api.dto;

import cz.muni.fi.pa165.w2018.dndtroops.api.enums.UserType;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * User Data Transfer Object
 *
 * @author Marek Valko
 */
public class UserDTO {

    @NotNull
    private Long id;

    @NotNull
    private String login;

    @NotNull
    private UserType tyoe;

    @NotNull
    private String passwordHash;

    private HeroDTO heroDTO;

    public UserDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(getLogin(), userDTO.getLogin()) &&
                getTyoe() == userDTO.getTyoe() &&
                Objects.equals(getPasswordHash(), userDTO.getPasswordHash());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getTyoe(), getPasswordHash());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserType getTyoe() {
        return tyoe;
    }

    public void setTyoe(UserType tyoe) {
        this.tyoe = tyoe;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public HeroDTO getHeroDTO() {
        return heroDTO;
    }

    public void setHeroDTO(HeroDTO heroDTO) {
        this.heroDTO = heroDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String name = heroDTO == null ? "NULL" : heroDTO.getName();
        return "UserDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", tyoe=" + tyoe +
                ", passwordHash='" + passwordHash + '\'' +
                ", hero='"+ name +'\'' +
                '}';
    }

}
