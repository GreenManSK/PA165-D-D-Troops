package cz.muni.fi.pa165.w2018.dndtroops.api.dto;

import java.util.Objects;

/**
 * User Authentication Data Transfer Object
 *
 * @author Marek Valko
 */
public class UserAuthenticateDTO {

    private String login;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAuthenticateDTO)) return false;
        UserAuthenticateDTO that = (UserAuthenticateDTO) o;
        return Objects.equals(getLogin(), that.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
