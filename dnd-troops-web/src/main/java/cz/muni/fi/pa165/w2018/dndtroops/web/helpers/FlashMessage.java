package cz.muni.fi.pa165.w2018.dndtroops.web.helpers;

/**
 * Message used for showing important information to user
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class FlashMessage {
    private String text;
    private FlashMessageType type;

    public FlashMessage(String text, FlashMessageType type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public FlashMessageType getType() {
        return type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(FlashMessageType type) {
        this.type = type;
    }
}
