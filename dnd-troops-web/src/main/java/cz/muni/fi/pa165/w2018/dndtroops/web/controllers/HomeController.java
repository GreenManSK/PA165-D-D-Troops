package cz.muni.fi.pa165.w2018.dndtroops.web.controllers;

import cz.muni.fi.pa165.w2018.dndtroops.web.WebUris;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Class HomeController
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Controller
public class HomeController extends BaseController {
    private final static Logger log = LoggerFactory.getLogger(HomeController.class);

    /**
     * Home page
     */
    @RequestMapping(value = WebUris.URL_HOME, method = RequestMethod.GET)
    public String home() {
        log.info("HomeController.home()");
        return "home/home";
    }
}
