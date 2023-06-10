package de.stinner.anmeldetool.application.rest.error;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Validated
@RestController
public class ExceptionHandlerTestController {

    public static final String EXCEPTION_TEST_ENDPOINT = "/test/exception";
    public static final String GENERIC_EXCEPTION_TEST_ENDPOINT = "/test/exception/generic";
    public static final String RESPONSE_STATUS_EXCEPTION_TEST_ENDPOINT =
            "/test/exception/response-status/{responseStatus}";

    public static final String URL_CONSTRAINT_VIOLATION_EXCEPTION_TEST_ENDPOINT =
            "/test/exception/url-constraint-violation/{constraint}";

    public static final String BODY_CONSTRAINT_VIOLATION_EXCEPTION_TEST_ENDPOINT =
            "/test/exception/body-constraint-violation";

    public static final String EMPTY_RESULT_DATA_ACCESS_EXCEPTION_TEST_ENDPOINT =
            "/test/exception/empty-result";
    public static final String NO_SUCH_ELEMENT_EXCEPTION_TEST_ENDPOINT =
            "/test/exception/no-such-element";

    public static final String UNSUPPORTED_MEDIA_TYPE_EXCEPTION_TEST_ENDPOINT =
            "/test/exception/unsupported-media-type";

    public static final String ACCESS_DENIED_EXCEPTION_TEST_ENDPOINT =
            "/test/exception/access-denied";

    @GetMapping(value = EXCEPTION_TEST_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> exception() {

        return ResponseEntity.ok("test");
    }

    @GetMapping(value = GENERIC_EXCEPTION_TEST_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> genericException() {
        throw new RuntimeException("this is a generic exception");
    }

    @GetMapping(RESPONSE_STATUS_EXCEPTION_TEST_ENDPOINT)
    public ResponseEntity<Void> responseStatusException(
            @PathVariable() final int responseStatus,
            @RequestParam(required = false) final String reason
    ) {
        throw new ResponseStatusException(HttpStatus.resolve(responseStatus), reason);
    }

    @GetMapping(URL_CONSTRAINT_VIOLATION_EXCEPTION_TEST_ENDPOINT)
    public ResponseEntity<String> urlConstraintViolationException(
            @PathVariable @Size(max = 5) final String constraint
    ) {
        return ResponseEntity.ok(constraint);
    }

    @PostMapping(value = BODY_CONSTRAINT_VIOLATION_EXCEPTION_TEST_ENDPOINT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TestControllerDto> bodyConstraintViolationException(
            @RequestBody @Valid final TestControllerDto dto
    ) {
        return ResponseEntity.ok(dto);
    }


    @GetMapping(EMPTY_RESULT_DATA_ACCESS_EXCEPTION_TEST_ENDPOINT)
    public ResponseEntity<String> emptyResultDataAccessException() {
        throw new EmptyResultDataAccessException(2);
    }

    @GetMapping(NO_SUCH_ELEMENT_EXCEPTION_TEST_ENDPOINT)
    public ResponseEntity<String> noSuchElementException() {
        throw new NoSuchElementException();
    }

    @PostMapping(value = UNSUPPORTED_MEDIA_TYPE_EXCEPTION_TEST_ENDPOINT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> unsupportedMediaTypeException(@RequestBody String value) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping(ACCESS_DENIED_EXCEPTION_TEST_ENDPOINT)
    @RolesAllowed("NEEDED_ROLE")
    public ResponseEntity<Void> accessDeniedException() {
        return ResponseEntity.noContent().build();
    }


}