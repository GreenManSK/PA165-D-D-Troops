package cz.muni.fi.pa165.w2018.dndtroops.service;

import cz.muni.fi.pa165.w2018.dndtroops.backend.dao.TroopDao;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Troop;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Troop Service Implementation
 *
 * @author Daniel Fecko 445539
 */
@Service
public class TroopServiceImpl implements TroopService{

	@Inject
	private TroopDao troopDao;

	/**
	 * Gets troop by id
	 *
	 * @param id
	 * @return Troop
	 */
	@Override
	public Troop getById(long id) {
		return troopDao.getById(id);
	}

	/**
	 * Gets all Troops
	 *
	 * @return List<Troop>
	 */
	@Override
	public List<Troop> getAll() {
		return troopDao.getAll();
	}

	/**
	 * Gets Troops according to Name
	 *
	 * @param name String
	 * @return List<Troop>
	 */
	@Override
	public List<Troop> getAllByName(String name) {
		return troopDao.getAllByName(name);
	}

	/**
	 * Gets Troops according to mission
	 *
	 * @param mission String
	 * @return List<Troop>
	 */
	@Override
	public List<Troop> getAllByMission(String mission) {
		return troopDao.getAllByMission(mission);
	}

	/**
	 * Creates Troop
	 *
	 * @param troop Troop
	 * @return long id
	 */
	@Override
	public long create(Troop troop) {
		troopDao.create(troop);
		return troop.getId();
	}

	/**
	 * Updates troop
	 *
	 * @param troop Troop
	 */
	@Override
	public void update(Troop troop) {
		troopDao.update(troop);
	}

	/**
	 * Deletes troop
	 * @param id long
	 */
	@Override
	public void delete(long id) {
		Troop troop = troopDao.getById(id);
		if (troop != null) {
			troopDao.remove(troop);
		}
	}
}
