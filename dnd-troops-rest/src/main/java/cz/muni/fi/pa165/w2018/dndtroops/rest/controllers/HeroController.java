package cz.muni.fi.pa165.w2018.dndtroops.rest.controllers;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.HeroFacade;
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
@RequestMapping(RestUris.ROOT_URL_HERO)
public class HeroController {

	private final static Logger logger = LoggerFactory.getLogger(HeroController.class);

	@Inject
	private HeroFacade heroFacade;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final HeroDTO getById(@PathVariable("id") Long id) {
		logger.debug("REST Hero getById({})", id);
		HeroDTO hero = heroFacade.getById(id);
		if (hero != null) {
			return hero;
		} else {
			throw new ResourceNotFoundException();
		}
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final List<HeroDTO> getAll() {
		logger.debug("REST Hero getAll()");
		return heroFacade.getAll();
	}

	@RequestMapping(method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public final long create(@RequestBody HeroChangeDTO hero) {
		logger.debug("REST Hero create()");
		try {
			return heroFacade.create(hero);
		} catch (NullPointerException e) {
			throw new InvalidParameterException();
		} catch (DataIntegrityViolationException e) {
			throw new ResourceNotModifiedException();
		}
	}

	@RequestMapping(value = "/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public final void delete(@PathVariable("id") Long id) {
		logger.debug("REST Hero delete({})", id);
		try {
			heroFacade.delete(id);
		} catch (Exception e) {
			throw new InvalidParameterException();
		}
	}
}
