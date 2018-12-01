package cz.muni.fi.pa165.w2018.dndtroops.api.facade;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.UserDTO;

import java.util.List;

/**
 * Interface for UserFacade
 *
 * @author Marek Valko
 */
public interface UserFacade {

    /**
     * Get user by id
     * @param id of the user
     * @return user or {@code null}, if user doesnt exist
     */
    UserDTO getById(long id);

    /**
     * Get user by its login
     * @param login of the user
     * @return user or {@code null}, if user doesnt exist
     */
    UserDTO getByLogin(String login);

    /**
     * Get all users
     * @return list of users
     */
    List<UserDTO> getAll();

    /**
     * Create given user with given password
     *
     * @param user          to be created
     * @param plainPassword users password
     */
    long createUser(UserDTO user, String plainPassword);

    /**
     * Update given user
     *
     * @param user to be updated
     */
    void update(UserDTO user);

    /**
     * Delete user with igven id
     *
     * @param id of the user
     */
    void delete(long id);

    /**
     * Checks if user with given id is administrator
     *
     * @param id of the user
     * @return true if user is administrator
     */
    boolean isUser(long id);

    /**
     * Checks if user is regular user
     *
     * @param id of the user
     * @return true if user is regular user (not admin)
     */
    boolean isAdmin(long id);

    /**
     * Authenticate user
     * @param user to be authenticated
     * @param plainPassword given password
     * @return ture if password hash matches stored password hash of the user
     */
    boolean authenticateUser(UserAuthenticateDTO user, String plainPassword);


}
