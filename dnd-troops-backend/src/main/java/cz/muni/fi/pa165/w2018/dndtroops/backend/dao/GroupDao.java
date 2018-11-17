package cz.muni.fi.pa165.w2018.dndtroops.backend.dao;

import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Group;

import java.util.List;

/**
 * DAO for entity Group
 *
 * @author Marek Valko <valko.marek@gmail.com>
 */

public interface GroupDao {

    /**
     * Finds Group by id
     * @param id of group
     * @return group or null, if there is no group with given id
     */
    Group getById(long id);

    /**
     * Finds group by name
     * @param name of the group
     * @return group or null, if there is no group with given id
     */
    Group getByName(String name);

    /**
     * Finds all Groups
     * @return List of all groups
     */
    List<Group> getAll();

    /**
     * Stores new Group
     * @param group to be created
     */
    void create(Group group);

    /**
     * Updates existing Group
     * @param group to be updated
     */
    void update(Group group);

    /**
     * Removes Group
     * @param group to be deleted
     */
    void remove(Group group);
}
