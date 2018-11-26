package cz.muni.fi.pa165.w2018.dndtroops.web.controllers;

import cz.muni.fi.pa165.w2018.dndtroops.web.WebUris;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Class ErrorController
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Controller
public class ErrorController extends BaseController {
    private final static Logger log = LoggerFactory.getLogger(ErrorController.class);

    /**
     * Displays page not found poge.
     *
     * @return JSP which to display.
     */
    @RequestMapping(value = WebUris.NOT_FOUND, method = RequestMethod.GET)
    public String resourceNotFoundException() {
        log.info("resourceNotFoundException()");
        return "error/404";
    }
}
