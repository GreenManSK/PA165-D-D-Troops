package cz.muni.fi.pa165.w2018.dndtroops.service;


import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Role;

import java.util.List;

/**
 * Interface for Role Service
 *
 * @author Marek Valko
 */
public interface RoleService {
    /**
     * Retrieve Role by id
     * @param id of the Role
     * @return hero or {@code null}, if there is no hero with given id
     */
    Role getById(long id);

    /**
     * Return all Roles
     * @return List of roles
     */
    List<Role> getAll();

    /**
     * Store new Role
     * @param role Role
     */
    void create(Role role);

    /**
     * Update the Role already in the database
     * @param role Role
     */
    void update(Role role);

    /**
     * Delete Role by its id
     * @param id of the Role
     */
    void delete(long id);

}
