package cz.muni.fi.pa165.w2018.dndtroops.api.facade;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroSearchDTO;

import java.util.List;

/**
 * Interface for HeroFacade
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface HeroFacade {
    /**
     * Retrieve hero by the id
     *
     * @param id of the hero
     * @return hero or {@code null}, if there is no hero with requested id
     */
    HeroDTO getById(long id);

    /**
     * Return all heroes
     * @return List of HeroDTOs
     */
    List<HeroDTO> getAll();

    /**
     * Creates new hero
     *
     * @param heroChangeDTO HeroChangeDTO
     * @return ID of new hero
     */
    long create(HeroChangeDTO heroChangeDTO);

    /**
     * Updates the hero specified by the ID of the DAO object
     *
     * @param heroChangeDTO HeroChangeDTO
     */
    void update(HeroChangeDTO heroChangeDTO);

    /**
     * Deletes hero by its ID
     *
     * @param id ID of the hero
     */
    void delete(long id);

    /**
     * Find all heroes according to the query specified by DTO
     * @param searchDTO HeroSearchDTO
     * @return List of heroes satisfying the query
     */
    List<HeroDTO> search(HeroSearchDTO searchDTO);

}
