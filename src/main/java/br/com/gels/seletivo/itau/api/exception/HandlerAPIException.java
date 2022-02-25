package br.com.gels.seletivo.itau.api.exception;

import br.com.gels.seletivo.itau.api.adapter.dto.ErroResponseDTO;
import br.com.gels.seletivo.itau.api.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class HandlerAPIException {

    @ExceptionHandler(value = {APIException.class})
    public ResponseEntity<ErroResponseDTO> apiException(APIException ex) {
        return response(ex.getStatusCode(), ex.getMessage(), ex.getCause().getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErroResponseDTO> unknownException() {
        return response(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR_MESSAGE, null);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class,
            IllegalArgumentException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ErroResponseDTO> paramInvalidException() {
        return response(HttpStatus.BAD_REQUEST, Constants.PARAMETERS_INVALID_ERROR_MSG, null);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErroResponseDTO> convertNumberException(MethodArgumentTypeMismatchException ex) {
        return response(HttpStatus.BAD_REQUEST, String.format(ex.getMessage(), ex.getName(), ex.getValue()), null);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<ErroResponseDTO> parameterRequiredIsNotPresent(MissingServletRequestParameterException ex) {
        return response(HttpStatus.BAD_REQUEST, String.format(ex.getMessage(), ex.getParameterName()), ex.getCause().toString());
    }

    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<ErroResponseDTO> parameterRequiredIsNotPresent(HttpMediaTypeNotSupportedException ex) {
        return response(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getMessage(), ex.getCause().toString());
    }

    private ResponseEntity<ErroResponseDTO> response(HttpStatus status, String message, String cause) {
        String statusCode = String.format(Constants.STATUS_CODE_FORMAT, status.value(), status.name());
        return new ResponseEntity<>(new ErroResponseDTO(statusCode, message, cause), status);
    }
}