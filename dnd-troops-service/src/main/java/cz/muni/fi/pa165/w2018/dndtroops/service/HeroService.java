package cz.muni.fi.pa165.w2018.dndtroops.service;


import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Group;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Hero;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Role;
import cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race;

import java.util.List;

/**
 * Interface for Hero Service
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface HeroService {
    /**
     * Retrieve hero by the id
     *
     * @param id of the hero
     * @return hero or {@code null}, if there is no hero with requested id
     */
    Hero getById(long id);

    /**
     * Return all heroes
     *
     * @return List of Heroes
     */
    List<Hero> getAll();

    /**
     * Creates new hero
     *
     * @param hero Hero
     * @return ID of new hero
     */
    long create(Hero hero);

    /**
     * Updates the hero already in the database
     *
     * @param hero Hero
     */
    void update(Hero hero);

    /**
     * Deletes hero by its ID
     *
     * @param id ID of the hero
     */
    void delete(long id);

    /**
     * Retrieve all heroes that have all specified properties
     *
     * @param role           Role of the hero, if {@code null} role is not used in search
     * @param race           Race of the hero, if {@code null} race is not used in search
     * @param fromExperience Minimal amount of experience, if {@code null} value is not used in search
     * @param toExperience   Maximal amount of experience, if {@code null} value is not used in search
     * @return List of heroes that satisfy all conditions
     */
    List<Hero> search(Role role, Race race, Group group, Long fromExperience, Long toExperience);
}
