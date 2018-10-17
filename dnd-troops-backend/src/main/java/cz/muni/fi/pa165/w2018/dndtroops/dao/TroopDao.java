package cz.muni.fi.pa165.w2018.dndtroops.dao;


import cz.muni.fi.pa165.w2018.dndtroops.entity.Troop;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * DAO for Troop entities
 *
 * @author Dusan Hetlerovic
 */
public interface TroopDao {

    /**
     * Find troop by id
     * @param id of the troop
     * @return troop or {@code null}, if there is no troop with requested id
     */
     Troop getById(long id);

    /**
     * Find all troops
     * @return List of all troops
     */
    List<Troop> getAll();

    /**
     * Stores new troop
     * @param troop to be created
     * @throws NullPointerException if the {@code troop} is {@code null}
     * @throws ConstraintViolationException if the {@code troop} has required field set as {@code null}
     */
    void create(Troop troop);

    /**
     * Updates existing troop with the same id
     * @param troop to be updated
     * @throws NullPointerException if the {@code troop} is {@code null}
     * @throws ConstraintViolationException if the {@code troop} has required field set as {@code null}
     */
    void update(Troop troop);

    /**
     * Deletes existing troop
     * @param troop to be deleted
     * @throws NullPointerException if the {@code troop} is {@code null}
     */
    void remove(Troop troop);

}
