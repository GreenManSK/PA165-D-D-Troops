package cz.muni.fi.pa165.w2018.dndtroops.service;

import cz.muni.fi.pa165.w2018.dndtroops.backend.dao.HeroDao;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Hero;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Role;
import cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of Hero Service
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Service
public class HeroServiceImpl implements HeroService {

    @Inject
    private HeroDao heroDao;


    /**
     * Retrieve hero by the id
     *
     * @param id of the hero
     * @return hero or {@code null}, if there is no hero with requested id
     */
    @Override
    public Hero getById(long id) {
        return heroDao.getById(id);
    }

    /**
     * Return all heroes
     *
     * @return List of Heroes
     */
    @Override
    public List<Hero> getAll() {
        return heroDao.getAll();
    }

    /**
     * Stores new hero
     *
     * @param hero Hero
     */
    @Override
    public void create(Hero hero) {
        heroDao.create(hero);
    }

    /**
     * Updates the hero already in the database
     *
     * @param hero Hero
     */
    @Override
    public void update(Hero hero) {
        heroDao.update(hero);
    }

    /**
     * Deletes hero by its ID
     *
     * @param id ID of the hero
     */
    @Override
    public void delete(long id) {
        Hero hero = heroDao.getById(id);
        if (hero != null) {
            heroDao.remove(hero);
        }
    }

    /**
     * Retrieve all heroes that have all specified properties
     *
     * @param role           Role of the hero, if {@code null} role is not used in search
     * @param race           Race of the hero, if {@code null} race is not used in search
     * @param fromExperience Minimal amount of experience, if {@code null} value is not used in search
     * @param toExperience   Maximal amount of experience, if {@code null} value is not used in search
     * @return List of heroes that satisfy all conditions
     */
    @Override
    public List<Hero> search(Role role, Race race, Long fromExperience, Long toExperience) {
        return getAll()
                .stream()
                .filter(hero -> {
                    if (role != null && !hero.getRoles().contains(role)) {
                        return false;
                    }
                    if (race != null && !hero.getRace().equals(race)) {
                        return false;
                    }
                    if (fromExperience != null && hero.getExperience() < fromExperience) {
                        return false;
                    }
                    if (toExperience != null && hero.getExperience() > toExperience) {
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
