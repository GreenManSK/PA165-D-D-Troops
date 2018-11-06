package cz.muni.fi.pa165.w2018.dndtroops.api.facade;


import cz.muni.fi.pa165.w2018.dndtroops.api.dto.RoleChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.RoleDTO;

import java.util.List;

/**
 * Interface for RoleFacade
 *
 * @author Marek Valko
 */
public interface RoleFacade {
    /**
     * Retrieve Role by the id
     * @param id of the Role
     * @return Role or {@code null}, if there is no Role with requested id
     */
    RoleDTO getById(long id);

    /**
     * Return all Roles
     * @return List of RoleDTOs
     */
    List<RoleDTO> getAll();

    /**
     * Creates new Role
     * @param roleChangeDTO RoleChangeDTO
     * @return ID of new Role
     */
    long create(RoleChangeDTO roleChangeDTO);

    /**
     * Updates the Role specified by the ID of the DAO object
     * @param roleChangeDTO RoleChangeDTO
     */
    void update(RoleChangeDTO roleChangeDTO);

    /**
     * Deletes Role by its ID
     * @param id ID of the Role
     */
    void delete(long id);

    /**
     * Returns all Roles by name
     * @param name Name of the Role
     * @return List of RoleDTOs
     */
    List<RoleDTO> getAllByName(String name);
}
