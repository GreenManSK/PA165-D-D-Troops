package cz.muni.fi.pa165.w2018.dndtroops.backend.dao;

import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.User;

import java.util.List;

/**
 * DAO for User entities
 *
 * @author Marek Valko
 */
public interface UserDao {

    /**
     * Find user by its id
     *
     * @param id if the user
     * @return user or {@code null}, if there is no user with requested id
     */
    User getById(long id);

    /**
     * Find all users
     * @return List of all users
     */
    List<User> getAll();

    /**
     * Stores new user
     * @param user to be created
     */
    void create(User user);

    /**
     * Updates existing User
     * @param user to be updated
     */
    void update(User user);

    /**
     * Removes user
     * @param user to be deleted
     */
    void remove(User user);

    /**
     * Find user by its login
     * @param login of the user
     * @return user or {@code null}, if there is no user with given login
     */
    User getByLogin(String login);

}
