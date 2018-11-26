package cz.muni.fi.pa165.w2018.dndtroops.web;

/**
 * Class WebUris
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class WebUris {

    public static final String URL_HOME = "/";
    public static final String URL_LOGIN = "/login";
    public static final String URL_LOGOUT = "/logout";

    public static final String NOT_FOUND = "/404";

    public static final String URL_HERO = "/hero";
    public static final String URL_GROUP = "/group";
    public static final String URL_TROOP = "/troop";
    public static final String URL_ROLE = "/role";

    private WebUris() {
        throw new AssertionError("This is not the class you are looking for");
    }
}
