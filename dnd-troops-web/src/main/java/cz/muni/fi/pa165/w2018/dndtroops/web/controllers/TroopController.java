package cz.muni.fi.pa165.w2018.dndtroops.web.controllers;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.TroopChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.TroopDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.TroopFacade;
import cz.muni.fi.pa165.w2018.dndtroops.web.WebUris;
import cz.muni.fi.pa165.w2018.dndtroops.web.helpers.FlashMessage;
import cz.muni.fi.pa165.w2018.dndtroops.web.helpers.FlashMessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Troop controller
 *
 * @author Daniel Fecko
 */
@Controller
@RequestMapping(value = {WebUris.URL_TROOP})
public class TroopController extends BaseController {

	private final static Logger log = LoggerFactory.getLogger(TroopController.class);

	@Autowired
	private TroopFacade troopFacade;

	/**
	 * Show list of all troops
	 */
	@RequestMapping(value = {"", "/", "/all"}, method = RequestMethod.GET)
	public String all(Model model) {
		log.debug("all()");
		List<TroopDTO> troops = troopFacade.getAll();
		model.addAttribute("troops", troops);

		return "/troop/all";
	}

	/**
	 * Show list of troops specified by name
	 *
	 * @param name String
	 */
	@RequestMapping(value = {"/findByName/{name}"}, method = RequestMethod.GET)
	public String findByName(Model model, @PathVariable String name) {
		log.debug("findByName(" + name + ")");
		List<TroopDTO> troops = troopFacade.getAllByName(name);
		model.addAttribute("troops", troops);

		return "/troop/findByName";
	}

	/**
	 * Show list of troops specified by mission
	 *
	 * @param mission String
	 */
	@RequestMapping(value = {"/findByMission/{mission}"}, method = RequestMethod.GET)
	public String findByMission(Model model, @PathVariable String mission) {
		log.debug("findByMission(" + mission + ")");
		List<TroopDTO> troops = troopFacade.getAllByMission(mission);
		model.addAttribute("troops", troops);

		return "/troop/findByMission";
	}

	/**
	 * Show troop creation page
	 */
	@RequestMapping(value = {"/create"}, method = RequestMethod.GET)
	public String create(Model model) {
		log.debug("create()");
		TroopChangeDTO troop = new TroopChangeDTO();
		model.addAttribute("troopObj", troop);
		return "troop/form";
	}

	/**
	 * Process troop creation request
	 *
	 * @param troop TroopChangeDTO
	 */
	@RequestMapping(value = {"/create"}, method = RequestMethod.POST)
	public String createPost(
			@ModelAttribute("troopObj") @Valid TroopChangeDTO troop,
			BindingResult bindingResult,
			Model model,
			RedirectAttributes redirectAttributes
	) {
		log.debug("createPost({})", troop);
		if (bindingResult.hasErrors()) {
			model.addAttribute(
					"flashMessage",
					new FlashMessage("troop.data.problem", FlashMessageType.ERROR));
			return "troop/form";
		}
		troopFacade.create(troop);
		redirectAttributes.addFlashAttribute(
				"flashMessage",
				new FlashMessage("troop.create.success", FlashMessageType.SUCCESS));

		return "redirect:" + WebUris.URL_TROOP + "/all";
	}

	/**
	 * Show troop update form
	 */
	@RequestMapping(value = {"/{id}/update"}, method = RequestMethod.GET)
	public String update(@PathVariable Long id, Model model) {
		log.debug("update({})", id);

		TroopDTO troop = troopFacade.getById(id);
		if (troop == null) {
			return "redirect:" + WebUris.NOT_FOUND;
		}

		TroopChangeDTO troopChangeDTO = new TroopChangeDTO();
		troopChangeDTO.setId(troop.getId());
		troopChangeDTO.setName(troop.getName());
		troopChangeDTO.setMission(troop.getMission());
		troopChangeDTO.setGold(troop.getGold());

		model.addAttribute("update", true);
		model.addAttribute("troopObj", troopChangeDTO);
		return "/troop/form";
	}

	/**
	 * Process troop update request
	 *
	 * @param troop troopChangeDTO
	 */
	@RequestMapping(value = {"/{id}/update"}, method = RequestMethod.POST)
	public String updatePost(
			@PathVariable Long id,
			@ModelAttribute("troopObj") @Valid TroopChangeDTO troop,
			BindingResult bindingResult,
			Model model,
			RedirectAttributes redirectAttributes
	) {
		log.debug("updatePost({}, {})", id, troop);

		if (bindingResult.hasErrors()) {
			model.addAttribute(
					"flashMessage",
					new FlashMessage("troop.data.problem", FlashMessageType.ERROR));
			model.addAttribute("update", true);
			return "/troop/form";
		}
		troopFacade.update(troop);
		redirectAttributes.addFlashAttribute(
				"flashMessage",
				new FlashMessage("troop.update.success", FlashMessageType.SUCCESS));

		return "redirect:" + WebUris.URL_TROOP + "/all";
	}

	/**
	 * Delete troop
	 *
	 * @param id id of the troop
	 */
	@RequestMapping(value = {"{id}/delete"}, method = RequestMethod.GET)
	public String delete(
			@PathVariable Long id,
			RedirectAttributes redirectAttributes
	) {
		log.debug("delete({})", id);

		try {
			troopFacade.delete(id);
			redirectAttributes.addFlashAttribute(
					"flashMessage",
					new FlashMessage("troop.delete.success", FlashMessageType.SUCCESS));
		} catch (DataAccessException exception) {
			log.error("Error while deleting troop", exception);
			redirectAttributes.addFlashAttribute(
					"flashMessage",
					new FlashMessage("troop.delete.problem", FlashMessageType.ERROR));
		}

		return "redirect:" + WebUris.URL_TROOP + "/all";
	}

}
