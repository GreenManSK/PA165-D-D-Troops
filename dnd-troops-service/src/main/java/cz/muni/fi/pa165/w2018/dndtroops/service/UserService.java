package cz.muni.fi.pa165.w2018.dndtroops.service;


import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.User;

import java.util.List;

/**
 * Interface for User Service
 */
public interface UserService {

    /**
     * Get user by its id
     * @param Id of the usee
     * @return user or {@code null}, if such user doesnt exist
     */
    User getById(long Id);

    /**
     * Get all registered users
     * @return list of users
     */
    List<User> getAll();

    /**
     * Register the user with given password
     * @param user to register
     * @param plainPassword password of the user
     */
    void createUser(User user, String plainPassword);

    /**
     * Updates the user
     * @param user to be updated
     */
    void update(User user);

    /**
     * Deletes user with given id
     * @param id of the user
     */
    void delete(long id);

    /**
     * Authentcate given user with given password
     *
     * @param user          to be authenticated
     * @param plainPassword password the user entered
     * @return true if given password matches stored password
     */
    boolean authenticate(User user, String plainPassword);

    /**
     * Gets user with given login or {@code null}, if such user doesnt exist
     *
     * @param login of the user
     * @return user
     */
    User getByLogin(String login);

    /**
     * checkc if user is admin
     * @param user to be checked
     * @return true if user is admin
     */
    boolean isAdmin(User user);

    /**
     * Checks if user is only user
     * @param user to be checked
     * @return true if user is not admin
     */
    boolean isUser(User user);

}
