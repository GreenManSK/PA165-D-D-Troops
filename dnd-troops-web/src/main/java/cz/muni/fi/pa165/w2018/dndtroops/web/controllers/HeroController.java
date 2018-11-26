package cz.muni.fi.pa165.w2018.dndtroops.web.controllers;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroSearchDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.RoleDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.enums.Race;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.GroupFacade;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.HeroFacade;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.RoleFacade;
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
import java.util.stream.Collectors;

/**
 * Class HeroController
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Controller
@RequestMapping(value = {WebUris.URL_HERO})
public class HeroController extends BaseController {
    private final static Logger log = LoggerFactory.getLogger(HeroController.class);

    @Autowired
    private HeroFacade heroFacade;

    @Autowired
    private RoleFacade roleFacade;

    @Autowired
    private GroupFacade groupFacade;

    //TODO: User hero, user edit

    /**
     * Show list of all heroes
     */
    @RequestMapping(value = {"", "/", "/all"}, method = RequestMethod.GET)
    public String all(Model model) {
        log.debug("all()");
        List<HeroDTO> heroes = heroFacade.getAll();
        model.addAttribute("heroes", heroes);
        model.addAttribute("search", new HeroSearchDTO());
        addHeroFormVariables(model);

        return "/hero/all";
    }

    /**
     * Search for heroes
     *
     * @param search HeroSearchDTO
     */
    @RequestMapping(value = {"/all"}, method = RequestMethod.POST)
    public String search(
            @ModelAttribute("search") @Valid HeroSearchDTO search,
            BindingResult bindingResult,
            Model model) {
        log.debug("search({})", search);
        List<HeroDTO> heroes;

        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "flashMessage",
                    new FlashMessage("hero.search.problem", FlashMessageType.ERROR));
            heroes = heroFacade.getAll();
        } else {
            heroes = heroFacade.search(search);
        }

        model.addAttribute("heroes", heroes);
        addHeroFormVariables(model);

        return "/hero/all";
    }

    /**
     * Show hero creation page
     */
    @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
    public String create(Model model) {
        log.debug("create()");
        HeroChangeDTO hero = new HeroChangeDTO();
        hero.setExperience(0L);
        model.addAttribute("heroObj", hero);
        addHeroFormVariables(model);
        return "hero/form";
    }

    /**
     * Process hero creation request
     *
     * @param hero HeroChangeDTO
     */
    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public String createPost(
            @ModelAttribute("heroObj") @Valid HeroChangeDTO hero,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        log.debug("createPost({})", hero);
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "flashMessage",
                    new FlashMessage("hero.data.problem", FlashMessageType.ERROR));
            addHeroFormVariables(model);
            return "hero/form";
        }
        heroFacade.create(hero);
        redirectAttributes.addFlashAttribute(
                "flashMessage",
                new FlashMessage("hero.create.success", FlashMessageType.SUCCESS));

        return "redirect:" + WebUris.URL_HERO + "/all";
    }

    /**
     * Show hero update form
     */
    @RequestMapping(value = {"/{id}/update"}, method = RequestMethod.GET)
    public String update(@PathVariable Long id, Model model) {
        log.debug("update({})", id);
        HeroDTO hero = heroFacade.getById(id);
        if (hero == null) {
            return "redirect:" + WebUris.NOT_FOUND;
        }

        HeroChangeDTO heroChangeDTO = new HeroChangeDTO();
        heroChangeDTO.setId(hero.getId());
        heroChangeDTO.setName(hero.getName());
        heroChangeDTO.setExperience(hero.getExperience());
        heroChangeDTO.setRace(hero.getRace());
        heroChangeDTO.setGroupId(hero.getGroup().getId());
        heroChangeDTO.setRoleIds(hero.getRoles().stream().map(RoleDTO::getId).collect(Collectors.toList()));

        model.addAttribute("update", true);
        model.addAttribute("heroObj", heroChangeDTO);
        addHeroFormVariables(model);
        return "/hero/form";
    }

    /**
     * Process hero update request
     *
     * @param hero HeroChangeDTO
     */
    @RequestMapping(value = {"/{id}/update"}, method = RequestMethod.POST)
    public String updatePost(
            @PathVariable Long id,
            @ModelAttribute("heroObj") @Valid HeroChangeDTO hero,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        log.debug("updatePost({}, {})", id, hero);
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "flashMessage",
                    new FlashMessage("hero.data.problem", FlashMessageType.ERROR));
            addHeroFormVariables(model);
            model.addAttribute("update", true);
            return "/hero/form";
        }
        heroFacade.update(hero);
        redirectAttributes.addFlashAttribute(
                "flashMessage",
                new FlashMessage("hero.update.success", FlashMessageType.SUCCESS));

        return "redirect:" + WebUris.URL_HERO + "/all";
    }

    /**
     * Show hero detail
     *
     * @param id id of the hero
     */
    @RequestMapping(value = {"{id}"}, method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model) {
        log.debug("detail({})", id);
        HeroDTO hero = heroFacade.getById(id);
        if (hero == null) {
            return "redirect:" + WebUris.NOT_FOUND;
        }
        model.addAttribute("hero", hero);
        return "/hero/detail";
    }

    /**
     * Delete hero
     *
     * @param id id of the hero
     */
    @RequestMapping(value = {"{id}/delete"}, method = RequestMethod.GET)
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {
        log.debug("delete({})", id);

        try {
            heroFacade.delete(id);
            redirectAttributes.addFlashAttribute(
                    "flashMessage",
                    new FlashMessage("hero.delete.success", FlashMessageType.SUCCESS));
        } catch (DataAccessException exception) {
            log.error("Error while deleting hero", exception);
            redirectAttributes.addFlashAttribute(
                    "flashMessage",
                    new FlashMessage("hero.delete.problem", FlashMessageType.ERROR));
        }

        return "redirect:" + WebUris.URL_HERO + "/all";
    }

    /**
     * Adds important variables for hero form to model
     */
    private void addHeroFormVariables(Model model) {
        model.addAttribute("roles", roleFacade.getAll());
        model.addAttribute("groups", groupFacade.getAll());
        model.addAttribute("races", Race.values());
    }
}
