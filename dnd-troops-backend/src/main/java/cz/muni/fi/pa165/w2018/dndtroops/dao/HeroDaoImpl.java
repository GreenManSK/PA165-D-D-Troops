package cz.muni.fi.pa165.w2018.dndtroops.dao;

import cz.muni.fi.pa165.w2018.dndtroops.entity.Hero;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of HeroDao
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Repository
public class HeroDaoImpl implements HeroDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Find hero by its id
     *
     * @param id of the hero
     * @return hero or {@code null}, if there is no hero with requested id
     */
    @Override
    public Hero getById(long id) {
        return entityManager.find(Hero.class, id);
    }

    /**
     * Find all heroes
     *
     * @return List of all heroes
     */
    @Override
    public List<Hero> getAll() {
        return entityManager.createQuery("select h from Hero h", Hero.class).getResultList();
    }

    /**
     * Stores new hero
     *
     * @param hero to be created
     * @throws NullPointerException         if the {@code hero} is {@code null}
     * @throws ConstraintViolationException if the {@code hero} has required field set as {@code null}
     */
    @Override
    public void create(Hero hero) {
        checkNotNull(hero);
        entityManager.persist(hero);
    }

    /**
     * Updates existing hero according to the id
     *
     * @param hero to be updated
     * @throws NullPointerException         if the {@code hero} is {@code null}
     * @throws ConstraintViolationException if the {@code hero} has required field set as {@code null}
     */
    @Override
    public void update(Hero hero) {
        checkNotNull(hero);
        entityManager.merge(hero);
    }

    /**
     * Removes the hero
     *
     * @param hero to be deleted
     * @throws NullPointerException if the {@code hero} is {@code null}
     */
    @Override
    public void remove(Hero hero) {
        checkNotNull(hero);
        entityManager.remove(hero);
    }
}
