package org.fasttrackit.onlineshopapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNorFoundException extends ApplicationException {
    public ResourceNorFoundException(String message) {
        super(message, ErrorCodes.NOT_FOUND);
    }

}
