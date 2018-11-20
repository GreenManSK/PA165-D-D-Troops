package cz.muni.fi.pa165.w2018.dndtroops.service.facade;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.TroopChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.TroopDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.TroopFacade;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Troop;
import cz.muni.fi.pa165.w2018.dndtroops.service.BeanMappingService;
import cz.muni.fi.pa165.w2018.dndtroops.service.TroopService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 *Troop Facade implementation
 *
 * @author Daniel Fecko 445539
 */
@Service
@Transactional
public class TroopFacadeImpl implements TroopFacade {

	@Inject
	private TroopService troopService;

	@Inject
	private BeanMappingService mapper;

	@Override
	public TroopDTO getById(long id) {
		return mapper.mapTo(troopService.getById(id), TroopDTO.class);
	}

	@Override
	public List<TroopDTO> getAll() {
		return mapper.mapTo(troopService.getAll(), TroopDTO.class);
	}

	@Override
	public List<TroopDTO> getAllByName(String name) {
		return mapper.mapTo(troopService.getAllByName(name), TroopDTO.class);
	}

	@Override
	public List<TroopDTO> getAllByMission(String mission) {
		return mapper.mapTo(troopService.getAllByMission(mission), TroopDTO.class);
	}

	@Override
	public long create(TroopChangeDTO troopChangeDTO) {
		Troop troop = mapper.mapTo(troopChangeDTO, Troop.class);
		troopService.create(troop);
		return troop.getId();
	}

	@Override
	public void update(TroopChangeDTO troopChangeDTO) {
		Troop troop = mapper.mapTo(troopChangeDTO, Troop.class);
		troopService.update(troop);
	}

	@Override
	public void delete(long id) {
		troopService.delete(id);
	}

}
