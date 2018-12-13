package cz.muni.fi.pa165.w2018.dndtroops.web.controllers;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.*;
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

/**
 * Group controller
 *
 * @author Dusan Hetlerovic
 */
@Controller
@RequestMapping(value = (WebUris.URL_GROUP))
public class GroupController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupFacade groupFacade;

    @Autowired
    private HeroFacade heroFacade;

    @Autowired
    private RoleFacade roleFacade;

    /**
     * Lists all groups
     */
    @RequestMapping(value = {"", "/", "all"}, method = RequestMethod.GET)
    public String all(Model model) {
        logger.debug("all()");
        model.addAttribute("groups", groupFacade.getAll());
        model.addAttribute("search", new SearchNameDTO());
        addGroupFormVariables(model);
        return "/group/all";
    }

    /**
     * Shows the detail of a group
     *
     * @param id of the group
     */
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model) {
        logger.debug("detail({})", id);
        GroupDTO g = groupFacade.getById(id);
        if (g == null) {
            return "redirect:" + WebUris.NOT_FOUND;
        }
        model.addAttribute("group", g);
        addGroupFormVariables(model);
        model.addAttribute("groupHeroes", groupFacade.getHeroesFromGroup(id));
        return "/group/detail";
    }

    /**
     * Shows group creation page
     */
    @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
    public String create(Model model) {
        logger.debug("create()");
        GroupChangeDTO g = new GroupChangeDTO();
        model.addAttribute("groupObj", g);
        return "/group/form";
    }

    /**
     * Processes group creation request
     *
     * @param group to be created
     */
    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public String createPost(@ModelAttribute("groupObj") @Valid GroupChangeDTO group,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        logger.debug("createPost({})", group);
        if (bindingResult.hasErrors()) {
            model.addAttribute("flashMessage",
                    new FlashMessage("group.data.problem",
                            FlashMessageType.ERROR));
            return "/group/form";
        }
        groupFacade.create(group);
        redirectAttributes.addFlashAttribute("flashMessage",
                new FlashMessage("group.create.success",
                        FlashMessageType.SUCCESS));
        return "redirect:" + WebUris.URL_GROUP + "/all";
    }

    /**
     * Shows group update page
     *
     * @param id of the group
     */
    @RequestMapping(value = {"/{id}/update"}, method = RequestMethod.GET)
    public String update(@PathVariable Long id, Model model) {
        logger.debug("update({})", id);
        GroupDTO group = groupFacade.getById(id);
        if (group == null) {
            return "redirect:" + WebUris.NOT_FOUND;
        }
        GroupChangeDTO groupChangeDTO = new GroupChangeDTO();
        groupChangeDTO.setId(group.getId());
        groupChangeDTO.setName(group.getName());
        model.addAttribute("update", true);
        model.addAttribute("groupObj", groupChangeDTO);
        addGroupFormVariables(model);
        return "/group/form";
    }

    /**
     * Processes group update request
     *
     * @param id    of the group
     * @param group to be updated
     */
    @RequestMapping(value = {"/{id}/update"}, method = RequestMethod.POST)
    public String updatePost(@PathVariable Long id,
                             @ModelAttribute("groupObj") @Valid GroupChangeDTO group,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        logger.debug("updatePost({}, {})", id, group);
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "flashMessage",
                    new FlashMessage("group.data.problem", FlashMessageType.ERROR));
            addGroupFormVariables(model);
            model.addAttribute("update", true);
            return "/group/form";
        }
        groupFacade.update(group);
        redirectAttributes.addFlashAttribute(
                "flashMessage",
                new FlashMessage("group.update.success", FlashMessageType.SUCCESS));

        return "redirect:" + WebUris.URL_GROUP + "/all";
    }

    /**
     * Deletes a group
     *
     * @param id of the group
     */
    @RequestMapping(value = {"{id}/delete"}, method = RequestMethod.GET)
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        logger.debug("delete({})", id);
        try {
            groupFacade.delete(id);
            redirectAttributes.addFlashAttribute(
                    "flashMessage",
                    new FlashMessage("group.delete.success", FlashMessageType.SUCCESS));
        } catch (DataAccessException exception) {
            logger.error("Error while deleting group", exception);
            redirectAttributes.addFlashAttribute(
                    "flashMessage",
                    new FlashMessage("group.delete.problem", FlashMessageType.ERROR));
        }
        return "redirect:" + WebUris.URL_GROUP + "/all";
    }

    /**
     * Finds group by name
     *
     * @param s SearchNameDTO containing the name of the group
     */
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public String findByName(
            @ModelAttribute("search") @Valid SearchNameDTO s,
            BindingResult bindingResult,
            Model model) {
        logger.debug("searchName({})", s);
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "flashMessage",
                    new FlashMessage("group.search.problem", FlashMessageType.ERROR));
        } else {
            GroupDTO g = groupFacade.getByName(s.getName());
            if (g == null) {
                model.addAttribute(
                        "flashMessage",
                        new FlashMessage("group.search.notFound", FlashMessageType.ERROR));
            } else {
                return "redirect:" + WebUris.URL_GROUP + "/" + g.getId();
            }
        }
        model.addAttribute("groups", groupFacade.getAll());
        addGroupFormVariables(model);
        model.addAttribute("search", new SearchNameDTO());
        return WebUris.URL_GROUP + "/all";
    }

    /**
     * Shows findWithoutRoles form
     */
    @RequestMapping(value = {"/findWithoutRoles"}, method = RequestMethod.GET)
    public String findWithoutRoles(Model model) {
        logger.debug("findWithoutRoles()");
        model.addAttribute("roles", roleFacade.getAll());
        model.addAttribute("rolesObj", new GroupRoleSearchDTO());
        addGroupFormVariables(model);
        return "/group/findWithoutRoles";
    }

    /**
     * Processes findWithoutRoles request
     *
     * @param roles GroupRoleSearchDTO with role ids
     */
    @RequestMapping(value = {"/findWithoutRoles"}, method = RequestMethod.POST)
    public String findWithoutRolesPost(@ModelAttribute("rolesObj") @Valid GroupRoleSearchDTO roles,
                                       BindingResult bindingResult,
                                       Model model,
                                       RedirectAttributes redirectAttributes) {
        logger.debug("findWithoutRolesPost()");
        List<GroupDTO> groups;
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "flashMessage",
                    new FlashMessage("group.findWithoutRoles.problem", FlashMessageType.ERROR));
            model.addAttribute("groups", groupFacade.getAll());
            addGroupFormVariables(model);
            return "/group/all";
        }

        groups = groupFacade.getGroupsWithoutRoles(roles.getRoleIds());
        redirectAttributes.addFlashAttribute("groups", groups);

        List<GroupDTO> omitted = groupFacade.getAll();
        for (GroupDTO g : groups) {
            omitted.remove(g);
        }
        redirectAttributes.addFlashAttribute("omitted", omitted);
        return "redirect:" + WebUris.URL_GROUP + "/groupsWithoutRoles";
    }

    /**
     * Shows groups without the selected roles
     *
     * @param groups groups without the roles
     * @param omitted groups with the roles
     */
    @RequestMapping(value = {"/groupsWithoutRoles"}, method = RequestMethod.GET)
    public String groupsWithoutRoles(@ModelAttribute("groups") @Valid List<GroupDTO> groups,
                                     @ModelAttribute("omitted") @Valid List<GroupDTO> omitted,
                                     Model model) {

        model.addAttribute("groups", groups);
        model.addAttribute("omitted", omitted);
        addGroupFormVariables(model);
        return "/group/groupsWithoutRoles";
    }

    /**
     * Adds hero to group
     *
     * @param gid id of the group
     * @param hid id of the hero
     */
    @RequestMapping(value = {"{gid}/addHero/{hid}"}, method = RequestMethod.GET)
    public String addHero(@PathVariable("gid") Long gid, @PathVariable("hid") Long hid, Model model) {
        logger.debug("addHero()");
        if (isUser() && !getUserDTO().getHero().getId().equals(hid)) {
            return "redirect:" + WebUris.NOT_FOUND;
        }
        HeroDTO hero = heroFacade.getById(hid);
        if (hero == null) {
            return "redirect:" + WebUris.NOT_FOUND;
        }
        groupFacade.addHero(gid, hid);
        model.addAttribute("added", true);
        return "redirect:" + WebUris.URL_GROUP + "/" + gid;
    }

    /**
     * Removes hero from group
     *
     * @param gid id of the group
     * @param hid id of the hero
     */
    @RequestMapping(value = {"{gid}/removeHero/{hid}"}, method = RequestMethod.GET)
    public String removeHero(@PathVariable("gid") Long gid, @PathVariable("hid") Long hid, Model model) {
        logger.debug("removeHero()");
        if (isUser() && !getUserDTO().getHero().getId().equals(hid)) {
            return "redirect:" + WebUris.NOT_FOUND;
        }
        HeroDTO hero = heroFacade.getById(hid);
        if (hero == null) {
            return "redirect:" + WebUris.NOT_FOUND;
        }
        groupFacade.removeHero(gid, hid);
        model.addAttribute("added", true);
        return "redirect:" + WebUris.URL_GROUP + "/" + gid;
    }

    /**
     * Helper method that adds heroes to model
     */
    private void addGroupFormVariables(Model model) {
        model.addAttribute("heroes", heroFacade.getAll());
        model.addAttribute("roles", roleFacade.getAll());
    }

}
