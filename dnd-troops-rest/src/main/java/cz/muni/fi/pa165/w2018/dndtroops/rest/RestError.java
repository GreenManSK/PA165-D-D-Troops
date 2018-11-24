package cz.muni.fi.pa165.w2018.dndtroops.rest;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@XmlRootElement
public class RestError {

    private List<String> errors;

    public RestError() {
    }

    public RestError(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
