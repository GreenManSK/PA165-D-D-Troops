package cz.muni.fi.pa165.w2018.dndtroops.rest.controllers;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.TroopChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.TroopDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.TroopFacade;
import cz.muni.fi.pa165.w2018.dndtroops.rest.RestUris;
import cz.muni.fi.pa165.w2018.dndtroops.rest.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.w2018.dndtroops.rest.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.w2018.dndtroops.rest.exceptions.ResourceNotModifiedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(RestUris.ROOT_URL_TROOP)
public class TroopController {

    final static Logger logger = LoggerFactory.getLogger(TroopController.class);

    @Inject
    private TroopFacade troopFacade;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TroopDTO getById(@PathVariable("id") Long id) {
        logger.debug("REST getById({})", id);
        if (id == null || id < 0) {
            throw new InvalidParameterException();
        }
        TroopDTO troop = troopFacade.getById(id);
        if (troop == null) {
            throw new ResourceNotFoundException();
        }
        return troop;
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TroopDTO> getByName(@PathVariable("name") String name) {
        logger.debug("REST getByName({})", name);
        if (name == null) {
            throw new InvalidParameterException();
        }
        return troopFacade.getAllByName(name);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TroopDTO> getAll() {
        logger.debug("REST getAll()");
        return troopFacade.getAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void create(@RequestBody TroopChangeDTO troop) {
        logger.debug("REST create()");
        try {
            troopFacade.create(troop);
        } catch (NullPointerException e) {
            throw new InvalidParameterException();
        } catch (DataIntegrityViolationException e) {
            throw new ResourceNotModifiedException();
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void update(@RequestBody TroopChangeDTO troop) {
        logger.debug("REST update()");
        try {
            troopFacade.update(troop);
        } catch (NullPointerException e) {
            throw new InvalidParameterException();
        } catch (DataIntegrityViolationException e) {
            throw new ResourceNotModifiedException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final void delete(@PathVariable("id") Long id) {
        logger.debug("REST delete({})", id);
        if (id == null || id < 0) {
            throw new InvalidParameterException();
        }
        troopFacade.delete(id);
    }

}
