package backend.utilities;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Helper to create consistent {@link ResponseEntity} instances for error responses.
 */
public final class ResponseUtils {

    private ResponseUtils() {
    }

    public static ResponseEntity<ApiError> badRequest(String message) {
        return error(HttpStatus.BAD_REQUEST, message);
    }

    public static ResponseEntity<ApiError> unauthorized(String message) {
        return error(HttpStatus.UNAUTHORIZED, message);
    }

    public static ResponseEntity<ApiError> forbidden(String message) {
        return error(HttpStatus.FORBIDDEN, message);
    }

    public static ResponseEntity<ApiError> notFound(String message) {
        return error(HttpStatus.NOT_FOUND, message);
    }

    public static ResponseEntity<ApiError> conflict(String message) {
        return error(HttpStatus.CONFLICT, message);
    }

    public static ResponseEntity<ApiError> internalServerError(String message) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static ResponseEntity<ApiError> error(HttpStatus status, String message) {
        return error(status, message, null, null);
    }

    public static ResponseEntity<ApiError> error(HttpStatus status, String message, String path) {
        return error(status, message, path, null);
    }

    public static ResponseEntity<ApiError> error(HttpStatus status, String message, String path, Map<String, Object> details) {
        return ResponseEntity.status(status).body(new ApiError(status, message, path, details));
    }
}
