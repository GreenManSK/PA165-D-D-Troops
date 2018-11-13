package cz.muni.fi.pa165.w2018.dndtroops.service;

import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Troop;

import java.util.List;

/**
 * Troop Service interface
 *
 * @author Dano Fecko 445539
 */
public interface TroopService {

	/**
	 * Retrieve troop by the id
	 * 
	 * @param id
	 * @return Troop
	 */
	Troop getById(long id);

	/**
	 * Return all troops
	 * 
	 * @return List<Troop>
	 */
	List<Troop> getAll();

	/**
	 * Return all troops with specified name
	 * 
	 * @param name
	 * @return List<Troop>
	 */
	List<Troop> getAllByName(String name);

	/**
	 * Return all troops with specified mission
	 *
	 * @param mission
	 * @return List<Troop>
	 */
	List<Troop> getAllByMission(String mission);

	/**
	 * Stores new troop
	 * 
	 * @param troop
	 * @return long
	 */
	long create(Troop troop);

	/**
	 * Updates the troop already in the database
	 * 
	 * @param troop
	 */
	void update(Troop troop);

	/**
	 * Deletes troop by its ID
	 * 
	 * @param id
	 */
 	void delete(long id);
}
