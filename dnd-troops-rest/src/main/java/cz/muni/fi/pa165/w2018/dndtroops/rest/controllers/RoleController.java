package cz.muni.fi.pa165.w2018.dndtroops.rest.controllers;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.RoleChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.RoleDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.RoleFacade;
import cz.muni.fi.pa165.w2018.dndtroops.rest.RestUris;
import cz.muni.fi.pa165.w2018.dndtroops.rest.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.w2018.dndtroops.rest.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.w2018.dndtroops.rest.exceptions.ResourceNotModifiedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Daniel Fecko
 */
@RestController
@RequestMapping(RestUris.ROOT_URL_ROLE)
public class RoleController {

	private final static Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Inject
	private RoleFacade roleFacade;

	@RequestMapping(value = "/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public final RoleDTO getById(@PathVariable("id") Long id) {
		logger.debug("REST Role getById({})", id);
		RoleDTO role = roleFacade.getById(id);
		if (role != null) {
			return role;
		} else {
			throw new ResourceNotFoundException();
		}
	}

	@RequestMapping(value = "/getByName/{name}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public final RoleDTO getByName(@PathVariable("name") String name) {
		logger.debug("REST Role getByName({})", name);
		RoleDTO role = roleFacade.getByName(name);
		if (role != null) {
			return role;
		} else {
			throw new ResourceNotFoundException();
		}
	}

	@RequestMapping(method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public final List<RoleDTO> getAll() {
		logger.debug("REST Role getAll()");
		return roleFacade.getAll();
	}

	@RequestMapping(method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public final long create(@RequestBody RoleChangeDTO role) {
		logger.debug("REST Role create()");
		try {
			return roleFacade.create(role);
		} catch (NullPointerException e) {
			throw new InvalidParameterException();
		} catch (DataIntegrityViolationException e) {
			throw new ResourceNotModifiedException();
		}
	}
}
