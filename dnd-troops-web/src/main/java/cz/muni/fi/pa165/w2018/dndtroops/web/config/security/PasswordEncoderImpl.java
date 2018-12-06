package cz.muni.fi.pa165.w2018.dndtroops.web.config.security;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Validates password using UserFacade authentication method
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class PasswordEncoderImpl implements PasswordEncoder {

    @Autowired
    private UserFacade userFacade;

    @Override
    public String encode(CharSequence login) {
        return login.toString();
    }

    @Override
    public boolean matches(CharSequence password, String login) {
        UserAuthenticateDTO userAuthenticateDTO = new UserAuthenticateDTO();
        userAuthenticateDTO.setLogin(login);
        try {
            return userFacade.authenticateUser(userAuthenticateDTO, password.toString());
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
