package cz.muni.fi.pa165.w2018.dndtroops.api.facade;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.TroopChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.TroopDTO;

import java.util.List;

/**
 * TroopFacade interface
 *
 * @author Daniel Fecko 445539
 */
public interface TroopFacade {

	/**
	 * Retrieve troop by the id
	 *
	 * @param id of the troop
	 * @return troop or {@code null}, if there is no troop with requested id
	 */
	TroopDTO getById(long id);

	/**
	 * Return all troops
	 *
	 * @return List of TroopDTOs
	 */
	List<TroopDTO> getAll();

	/**
	 * Return all troops by name
	 *
	 * @return List of TroopsDTOs
	 */
	List<TroopDTO> getAllByName(String name);

	/**
	 * Return all troops by mission
	 *
	 * @return List of TroopsDTOs
	 */
	List<TroopDTO> getAllByMission(String mission);

	/**
	 * Creates new troop
	 *
	 * @param troopChangeDTO TroopChangeDTO
	 * @return ID of new troop
	 */
	long create(TroopChangeDTO troopChangeDTO);

	/**
	 * Updates the troop specified by the ID of the DAO object
	 *
	 * @param troopChangeDTO TroopChangeDTO
	 */
	void update(TroopChangeDTO troopChangeDTO);

	/**
	 * Deletes troop by its ID
	 *
	 * @param id ID of the troop
	 */
	void delete(long id);

}
