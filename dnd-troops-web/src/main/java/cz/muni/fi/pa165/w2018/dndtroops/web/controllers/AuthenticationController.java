package cz.muni.fi.pa165.w2018.dndtroops.web.controllers;

import cz.muni.fi.pa165.w2018.dndtroops.web.WebUris;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class AuthenticationController
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Controller
public class AuthenticationController extends BaseController {

    private final static Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @RequestMapping(value = WebUris.URL_LOGIN, method = RequestMethod.GET)
    public String loginGet(Model model) {
        log.info("AuthenticationController.loginGet()");
        return "authentication/login";
    }

    @RequestMapping(value = WebUris.URL_LOGIN, method = RequestMethod.POST)
    public String loginPost(Model model) {
        log.info("AuthenticationController.loginPost()");
        return WebUris.URL_LOGIN;
    }

    @RequestMapping(value = WebUris.URL_LOGOUT, method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("LogoutController.logout()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/authentication/login";
    }

}
