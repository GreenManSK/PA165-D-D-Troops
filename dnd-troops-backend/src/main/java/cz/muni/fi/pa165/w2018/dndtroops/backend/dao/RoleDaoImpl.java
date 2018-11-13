package cz.muni.fi.pa165.w2018.dndtroops.backend.dao;

import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of RoleDao
 *
 * @author Daniel Fecko 445539
 */
@Repository
public class RoleDaoImpl implements RoleDao {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Find role by its id
	 *
	 * @param id of role
	 * @return role or null if not found
	 */
	@Override
	public Role getById(long id) {
		return entityManager.find(Role.class, id);
	}

	/**
	 * Find all roles
	 *
	 * @return List of all roles
	 */
	@Override
	public List<Role> getAll() {
		return entityManager.createQuery("select r from Role r", Role.class).getResultList();
	}

	/**
	 * Stores new role
	 *
	 * @param role to be created
	 */
	@Override
	public void create(Role role) {
		checkNotNull(role);
		entityManager.persist(role);
	}

	/**
	 * Updates existing role according to the id
	 *
	 * @param role to be updated
	 */
	@Override
	public void update(Role role) {
		checkNotNull(role);
		entityManager.merge(role);
	}

	/**
	 * Removes the hero
	 *
	 * @param role to be deleted\
	 */
	@Override
	public void delete(Role role) {
		checkNotNull(role);
		entityManager.remove(role);
	}

	@Override
	public Role getByName(String name) {
		checkNotNull(name);
		try {
			return entityManager.createQuery("select r from Role r where r.name = :name", Role.class).setParameter("name", name).getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}
}
