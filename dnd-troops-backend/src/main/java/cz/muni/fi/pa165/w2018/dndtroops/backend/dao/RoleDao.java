package cz.muni.fi.pa165.w2018.dndtroops.backend.dao;

import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Role;

import java.util.List;

/**
 * Dao for Role entities
 *
 * @ Daniel Fecko
 */
public interface RoleDao {

	/**
	 * Find role by its id
	 *
	 * @param id of role
	 * @return role or null if not found
	 */
	Role getById(long id);

	/**
	 * Find all roles
	 *
	 * @return List of all roles
	 */
	List<Role> getAll();

	/**
	 * Stores new role
	 *
	 * @param role to be created
	 */
	void create(Role role);

	/**
	 * Updates existing role according to the id
	 *
	 * @param role to be updated
	 */
	void update(Role role);

	/**
	 * Removes the hero
	 *
	 * @param role to be deleted
	 */
	void delete(Role role);

}
