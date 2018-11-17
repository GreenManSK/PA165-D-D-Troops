package cz.muni.fi.pa165.w2018.dndtroops.backend.dao;

import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Group;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of GroupDao
 *
 * @author Marek Valko <valko.marek@gmail.com>
 */
@Repository
public class GroupDaoImpl implements GroupDao{

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Finds Group by id
     * @param id of group
     * @return group or null, if there is no group with given id
     */
    @Override
    public Group getById(long id) {
        return entityManager.find(Group.class, id);
    }

    /**
     * Finds group by name
     *
     * @param name of the group
     * @return group or null, if there is no group with given id
     */
    @Override
    public Group getByName(String name) {
        checkNotNull(name);
        try {
            return entityManager.createQuery("select g from groups g where g.name = :name", Group.class).setParameter("name", name).getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Finds all Groups
     * @return List of all groups
     */
    @Override
    public List<Group> getAll() {
        return entityManager.createQuery("select g from groups g", Group.class).getResultList();
    }

    /**
     * Stores new Group
     * @param group to be created
     */
    @Override
    public void create(Group group) {
        checkNotNull(group);
        entityManager.persist(group);
    }

    /**
     * Updates existing Group
     * @param group to be updated
     */
    @Override
    public void update(Group group) {
        checkNotNull(group);
        entityManager.merge(group);
    }

    /**
     * Removes Group
     * @param group to be deleted
     */
    @Override
    public void remove(Group group) {
        checkNotNull(group);
        entityManager.remove(group);
    }
}
