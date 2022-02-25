package br.com.gels.seletivo.itau.api.exception;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class APIException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Logger logger;
    private HttpStatus statusCode;

    public APIException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public APIException(String message, HttpStatus httpStatus) {
        this(message, httpStatus, null);
    }

    public APIException(String message, HttpStatus httpStatus, Logger logger) {
        this(message, httpStatus, logger, null);
    }

    public APIException(String message, HttpStatus httpStatus, Logger logger, Throwable cause) {
        super(message, cause);

        this.logger = logger;
        this.statusCode = httpStatus;

        if (this.logger != null) {
            this.logger.error("{} - {} - {}", this.statusCode, message, cause);
        }
    }

    public APIException(String message, String identificador, HttpStatus httpStatus, Logger logger) {
        this(message, httpStatus, null);

        this.logger = logger;
        this.statusCode = httpStatus;

        if (this.logger != null) {
            this.logger.error("{} - {} - {}", this.statusCode, message, identificador);
        }
    }

    public HttpStatus getStatusCode() {
        return this.statusCode;
    }
}