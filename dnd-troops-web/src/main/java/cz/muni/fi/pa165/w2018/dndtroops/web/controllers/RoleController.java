package cz.muni.fi.pa165.w2018.dndtroops.web.controllers;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.RoleChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.RoleDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.SearchNameDTO;
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
 * Class RoleController
 *
 * @author Marek Valko
 */
@Controller
@RequestMapping(value = {WebUris.URL_ROLE})
public class RoleController extends BaseController {
    private final static Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleFacade roleFacade;


    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public String searchName(
            @ModelAttribute("search") @Valid SearchNameDTO search,
            BindingResult bindingResult,
            Model model) {
        log.debug("searchName({})",search);
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "flashMessage",
                    new FlashMessage("role.search.problem",FlashMessageType.ERROR));
        }
        else {
            RoleDTO roleDTO = roleFacade.getByName(search.getName());
            if (roleDTO == null) {
                model.addAttribute(
                        "flashMessage",
                        new FlashMessage("role.search.not.found",FlashMessageType.ERROR));
            }
            else {
                return "redirect:" + WebUris.URL_ROLE + "/" + roleDTO.getId();
            }
        }
        List<RoleDTO> roles = roleFacade.getAll();
        model.addAttribute("roles",roles);
        model.addAttribute("search", new SearchNameDTO());
        return "/" + WebUris.URL_ROLE + "/all";
    }

    @RequestMapping(value = {"","/","/all"},method = RequestMethod.GET)
    public String all(Model model) {
        log.debug("all()");
        List<RoleDTO> roles = roleFacade.getAll();
        model.addAttribute("roles",roles);
        model.addAttribute("search", new SearchNameDTO());
        return "role/all";
    }


    @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
    public String create(Model model) {
        log.debug("create()");
        RoleChangeDTO role = new RoleChangeDTO();
        model.addAttribute("roleObj", role);
        return "role/form";
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public String createPost(
            @ModelAttribute("roleObj") @Valid RoleChangeDTO role,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        log.debug("createPost{()}",role);
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "flashMessage",
                    new FlashMessage("role.data.problem", FlashMessageType.ERROR));

            return "role/form";
        }
        roleFacade.create(role);
        redirectAttributes.addFlashAttribute(
                "flashMessage",
                new FlashMessage("role.create.success",FlashMessageType.SUCCESS));
        return "redirect:" + WebUris.URL_ROLE + "/all";
    }

    @RequestMapping(value = {"/{id}/update"}, method = RequestMethod.GET)
    public String update(@PathVariable Long id, Model model) {
        log.debug("update({})",id);
        RoleDTO role = roleFacade.getById(id);
        if (role == null) {
            return "redirect:" + WebUris.NOT_FOUND;
        }
        RoleChangeDTO roleChangeDTO = new RoleChangeDTO();
        roleChangeDTO.setId(role.getId());
        roleChangeDTO.setName(role.getName());
        roleChangeDTO.setDescription(role.getDescription());
        model.addAttribute("update",true);
        model.addAttribute("roleObj", roleChangeDTO);
        return "/role/form";
    }

    @RequestMapping(value = {"/{id}/update"}, method = RequestMethod.POST)
    public String updatePost(
            @PathVariable Long id,
            @ModelAttribute("roleObj") @Valid RoleChangeDTO role,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        log.debug("updatePost({} , {}", id , role);
        if (bindingResult.hasErrors()) {
            model.addAttribute("flashMessage",
                    new FlashMessage("role.data.problem",FlashMessageType.ERROR));
            model.addAttribute("update",true);
            return "/role/form";
        }
        roleFacade.update(role);
        redirectAttributes.addFlashAttribute(
                "flashMessage",
                new FlashMessage("role.update.success", FlashMessageType.SUCCESS));
        return "redirect:" + WebUris.URL_ROLE + "/all";
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model) {
        log.debug("detail({})", id);
        RoleDTO role = roleFacade.getById(id);
        if (role == null) {
            return "redirect:" + WebUris.NOT_FOUND;
        }
        model.addAttribute("role", role);
        return "/role/detail";
    }

    @RequestMapping(value = {"{id}/delete"}, method = RequestMethod.GET)
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {
        log.debug("delete({})", id);
        try {
            roleFacade.delete(id);
            redirectAttributes.addFlashAttribute(
                    "flashMessage",
                    new FlashMessage("role.delete.success", FlashMessageType.SUCCESS));
        } catch (DataAccessException e) {
            log.error("Error while deleting role", e);
            redirectAttributes.addFlashAttribute(
                    "flashMessage",
                    new FlashMessage("role.delete.problem", FlashMessageType.ERROR));
        }
        return "redirect:" + WebUris.URL_ROLE + "/all";
    }

}
