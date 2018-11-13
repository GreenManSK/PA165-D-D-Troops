package cz.muni.fi.pa165.w2018.dndtroops.backend.dao;


import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Troop;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of TroopDao
 *
 * @author Dusan Hetlerovic
 */
@Repository
public class TroopDaoImpl implements TroopDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Find troop by id
     *
     * @param id of the troop
     * @return troop or {@code null}, if there is no troop with requested id
     */
    @Override
    public Troop getById(long id) {
        return entityManager.find(Troop.class, id);
    }

    /**
     * Find all troops
     *
     * @return List of all troops
     */
    @Override
    public List<Troop> getAll() {
        return entityManager.createQuery("SELECT t FROM Troop t", Troop.class).getResultList();
    }

    /**
     * Gets Troops according to Name
     *
     * @param name String
     * @return List<Troop>
     */
    @Override
    public List<Troop> getAllByName(String name) {
        return entityManager.createQuery("SELECT t FROM Troop t WHERE t.name = :name", Troop.class).setParameter("name", name).getResultList();
    }

    /**
     * Gets Troops according to mission
     *
     * @param mission String
     * @return List<Troop>
     */
    @Override
    public List<Troop> getAllByMission(String mission) {
        return entityManager.createQuery("SELECT t FROM Troop t WHERE t.mission = :mission", Troop.class).setParameter("mission", mission).getResultList();
    }

    /**
     * Stores new troop
     *
     * @param troop to be created
     * @throws NullPointerException if the {@code troop} is {@code null}
     * @throws ConstraintViolationException if the {@code troop} has required field set as {@code null}
     */
    @Override
    public void create(Troop troop) {
        checkNotNull(troop);
        entityManager.persist(troop);
    }

    /**
     * Updates existing troop with the same id
     *
     * @param troop to be updated
     * @throws NullPointerException if the {@code troop} is {@code null}
     * @throws ConstraintViolationException if the {@code troop} has required field set as {@code null}
     */
    @Override
    public void update(Troop troop) {
        checkNotNull(troop);
        entityManager.merge(troop);
    }

    /**
     * Deletes existing troop
     *
     * @param troop to be deleted
     * @throws NullPointerException if the {@code troop} is {@code null}
     */
    @Override
    public void remove(Troop troop) {
        checkNotNull(troop);
        entityManager.remove(troop);
    }
}
