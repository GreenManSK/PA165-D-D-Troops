package cz.muni.fi.pa165.w2018.dndtroops.rest;

/**
 * Class RestUris
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public final class RestUris {
    public static final String ROOT_URL_HERO = "/hero";
    public static final String ROOT_URL_GROUP = "/group";
    public static final String ROOT_URL_TROOP = "/troop";
    public static final String ROOT_URL_ROLE = "/role";

    private RestUris() {
        throw new AssertionError("This is not the class you are looking for");
    }
}
