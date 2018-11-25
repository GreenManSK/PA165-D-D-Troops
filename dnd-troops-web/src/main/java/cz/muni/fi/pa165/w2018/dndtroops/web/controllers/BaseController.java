package cz.muni.fi.pa165.w2018.dndtroops.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Abstract controller as base for all other controllers
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public abstract class BaseController {

    private final static Logger log = LoggerFactory.getLogger(BaseController.class);

    /**
     * Initialize String trimming binder.
     *
     * @param binder WebDataBinder.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        log.debug("String trimming binder initialized");
        StringTrimmerEditor stringTrimmer = new StringTrimmerEditor(false);
        binder.registerCustomEditor(String.class, stringTrimmer);
    }

    /**
     * Method which checks if user is authenticated. Can be used in JSP.
     *
     * @return True if user, who created request is authenticated, false otherwise.
     */
    @ModelAttribute("isAuthenticated")
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        if (authentication == null)
            return false;
        return authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken);
    }

    /**
     * Returns login of user, who generated HTTP request.
     *
     * @return Login of user or null if user is not authenticated.
     */
    @ModelAttribute("getLogin")
    public String getLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            return null;
        return authentication.getName();
    }
}
