package cz.muni.fi.pa165.w2018.dndtroops.dao;

import cz.muni.fi.pa165.w2018.dndtroops.entity.Hero;

import java.util.List;
import javax.validation.ConstraintViolationException;

/**
 * DAO for Hero entities
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface HeroDao {

    /**
     * Find hero by its id
     *
     * @param id of the hero
     * @return hero or {@code null}, if there is no hero with requested id
     */
    Hero getById(long id);

    /**
     * Find all heroes
     *
     * @return List of all heroes
     */
    List<Hero> getAll();

    /**
     * Stores new hero
     *
     * @param hero to be created
     * @throws NullPointerException         if the {@code hero} is {@code null}
     * @throws ConstraintViolationException if the {@code hero} has required field set as {@code null}
     */
    void create(Hero hero);


    /**
     * Updates existing hero according to the id
     *
     * @param hero to be updated
     * @throws NullPointerException         if the {@code hero} is {@code null}
     * @throws ConstraintViolationException if the {@code hero} has required field set as {@code null}
     */
    void update(Hero hero);

    /**
     * Removes the hero
     *
     * @param hero to be deleted
     * @throws NullPointerException if the {@code hero} is {@code null}
     */
    void remove(Hero hero);
}
