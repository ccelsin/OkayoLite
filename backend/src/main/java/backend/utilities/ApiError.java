package backend.utilities;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

/**
 * Standard API error
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private final Instant timestamp = Instant.now();
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final Map<String, Object> details;

    public ApiError(HttpStatus status, String message) {
        this(status, message, null, null);
    }

    public ApiError(HttpStatus status, String message, String path) {
        this(status, message, path, null);
    }

    public ApiError(HttpStatus status, String message, String path, Map<String, Object> details) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
        this.details = details;
    }
}