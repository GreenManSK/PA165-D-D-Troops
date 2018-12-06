package cz.muni.fi.pa165.w2018.dndtroops.rest.controllers;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.GroupDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.GroupFacade;
import cz.muni.fi.pa165.w2018.dndtroops.rest.RestUris;
import cz.muni.fi.pa165.w2018.dndtroops.rest.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.w2018.dndtroops.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Daniel Fecko
 */
@RestController
@RequestMapping(RestUris.ROOT_URL_GROUP)
public class GroupController {

	private final static Logger logger = LoggerFactory.getLogger(GroupController.class);

	@Inject
	private GroupFacade groupFacade;

	@RequestMapping(value = "/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public final GroupDTO getById(@PathVariable("id") Long id) {
		logger.debug("REST Group getById({})", id);
		GroupDTO group = groupFacade.getById(id);
		if (group != null) {
			return group;
		} else {
			throw new ResourceNotFoundException();
		}
	}

	@RequestMapping(value = "/getByName/{name}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public final GroupDTO getByName(@PathVariable("name") String name) {
		logger.debug("REST Groreturn groupFacade.getByName(name);up getByName({})", name);
		GroupDTO group = groupFacade.getByName(name);
		if (group != null) {
			return group;
		} else {
			throw new ResourceNotFoundException();
		}
	}

	@RequestMapping(method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public final List<GroupDTO> getAll() {
		logger.debug("REST Group getAll()");
		return groupFacade.getAll();
	}

	@RequestMapping(value = "/getHeroesFromGroup/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public final List<HeroDTO> getHeroesFromGroup(@PathVariable("id") Long id) {
		logger.debug("REST Group getHeroesFromGroup({})", id);
		List<HeroDTO> heroes = groupFacade.getHeroesFromGroup(id);
		if (heroes.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		return heroes;
	}

	@RequestMapping(value = "/addHero/{groupId}/{heroId}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public final void addHero(@PathVariable("groupId") Long groupId, @PathVariable("heroId") Long heroId) {
		logger.debug("REST Group addHero");
		try {
			groupFacade.addHero(groupId, heroId);
		} catch (Exception e) {
			throw new InvalidParameterException();
		}
	}

	@RequestMapping(value = "/removeHero/{groupId}/{heroId}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public final void removeHero(@PathVariable("groupId") Long groupId, @PathVariable("heroId") Long heroId) {
		logger.debug("REST Group removeHero");
		try {
			groupFacade.removeHero(groupId, heroId);
		} catch (Exception e) {
			throw new InvalidParameterException();
		}
	}

}
