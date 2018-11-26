package cz.muni.fi.pa165.w2018.dndtroops.web.helpers;

/**
 * Type of flash message
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public enum FlashMessageType {
    PRIMARY("alert-primary"), SUCCESS("alert-success"), WARNING("alert-warning"),
    ERROR("alert-danger"), WARING("alert-waring");

    private String cssClass;

    FlashMessageType(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }
}
