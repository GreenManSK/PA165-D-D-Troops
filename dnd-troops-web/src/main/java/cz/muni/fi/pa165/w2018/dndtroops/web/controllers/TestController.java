package cz.muni.fi.pa165.w2018.dndtroops.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Class TestController
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Controller
public class TestController extends BaseController {

//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String loginGet(Model model) {
//        return "login";
//    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(Model model) {
        return "test";
    }
}
