package cz.muni.fi.pa165.w2018.dndtroops.api.facade;


import cz.muni.fi.pa165.w2018.dndtroops.api.dto.GroupChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.GroupDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroDTO;

import java.util.List;

/**
 * GroupFacade interface
 *
 * @author Dusan Hetlerovic
 */
public interface GroupFacade {

    /**
     * Retrieve group by id
     *
     * @param id of the group
     * @return group with given id
     */
    GroupDTO getById(long id);

    /**
     * Retrieve group by name
     *
     * @param name of the group
     * @return group with given name
     */
    GroupDTO getByName(String name);

    /**
     * Returns groups where no hero has any of the given roles
     *
     * @param roles to be omitted
     * @return List of groups without given roles
     */
    List<GroupDTO> getGroupsWithoutRoles(List<String> roles);

    /**
     * Retrieve all groups
     *
     * @return List of all groups
     */
    List<GroupDTO> getAll();

    /**
     * Retrieves all heroes in a group
     *
     * @param id of the group
     * @return List of heroes in the group
     */
    List<HeroDTO> getHeroesFromGroup(long id);

    /**
     * Creates a new group
     *
     * @param group to be created
     * @return id of the created group
     */
    long create(GroupChangeDTO group);

    /**
     * Updates an existing group
     *
     * @param group to be updated
     */
    void update(GroupChangeDTO group);

    /**
     * Deletes group by its id
     *
     * @param id of the group to be removed
     */
    void delete(long id);

    /**
     * Adds hero to group
     *
     * @param groupId id of the group
     * @param hero to be added
     */
    void addHero(long groupId, HeroDTO hero);

    /**
     * Removes hero from group
     *
     * @param groupId id of the group
     * @param hero to be removed
     */
    void removeHero(long groupId, HeroDTO hero);

}
