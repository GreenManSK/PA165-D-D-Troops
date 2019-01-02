package cz.muni.fi.pa165.w2018.dndtroops.rest.controllers;

import com.google.common.collect.ImmutableList;
import cz.muni.fi.pa165.w2018.dndtroops.rest.RestError;
import cz.muni.fi.pa165.w2018.dndtroops.rest.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.w2018.dndtroops.rest.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.w2018.dndtroops.rest.exceptions.ResourceNotModifiedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Lukáš Kurčík
 */
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    RestError handleException(ResourceNotFoundException ex) {
        RestError error = new RestError();
        error.setErrors(ImmutableList.of("Unable to find the requested resource."));
        return error;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    RestError handleException(InvalidParameterException ex) {
        RestError error = new RestError();
        error.setErrors(ImmutableList.of("Invalid parameter."));
        return error;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    @ResponseBody
    RestError handleException(ResourceNotModifiedException ex) {
        RestError error = new RestError();
        error.setErrors(ImmutableList.of("The requested resource was not modified."));
        return error;
    }
}
