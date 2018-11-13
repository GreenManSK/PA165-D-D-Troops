package cz.muni.fi.pa165.w2018.dndtroops.service;


import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Group;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Hero;

import java.util.List;

/**
 * Group service interface
 *
 * @author Dusan Hetlerovic
 */
public interface GroupService {

    /**
     * Get group by id
     * @param id of the group
     * @return group with given id (if exists) or null
     */
    Group getById(long id);

    /**
     * Get group with given name
     *
     * @param name of the group
     * @return group with given name
     */
    Group getByName(String name);

    /**
     * Get groups where no hero has any of the given roles
     *
     * @param roles ids of roles to be omitted
     * @return List of groups without given roles
     */
    List<Group> getGroupsWithoutRoles(List<Long> roles);

    /**
     * Get all groups
     *
     * @return List of all existing groups
     */
    List<Group> getAll();

    /**
     * Stores a new group
     *
     * @param group to be stored
     */
    void create(Group group);

    /**
     * Update an existing group
     *
     * @param group to be updated
     */
    void update(Group group);

    /**
     * Deletes stored group with given id
     *
     * @param id of the group to be deleted
     */
    void delete(long id);

    /**
     * Adds an existing hero to an existing group
     *
     * @param groupId id of the group
     * @param heroId id of the hero
     */
    void addHero(long groupId, long heroId);

    /**
     * Remove hero from existing group
     *
     * @param groupId id of the group
     * @param heroId id of the hero
     */
    void removeHero(long groupId, long heroId);


    /**
     * Retrieves all heroes in a group
     *
     * @param id of the group
     * @return List of heroes in the group
     */
    List<Hero> getHeroesFromGroup(long id);

}
