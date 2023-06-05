package de.stinner.anmeldetool.application.rest.error;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorMessagesTest {

    @Test
    void errorMessageShouldIncludeRejectedMediaTypeAndAcceptableMediaTypes() {
        String providedMediaType = "someRejectedMediaType";
        List<MediaType> acceptableMediaTypes = List.of(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML);
        assertThat(ErrorMessages.getMediaTypeNotAcceptableMessage(providedMediaType, acceptableMediaTypes))
                .contains(providedMediaType)
                .contains(acceptableMediaTypes.toString());
    }

    @Test
    void errorMessageShouldIncludeRejectedMediaTypeAndSupportedContentTypes() {
        String providedMediaType = "someRejectedMediaType";
        List<MediaType> supportedMediaTypes = List.of(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML);
        assertThat(ErrorMessages.getMediaTypeNotSupportedMessage(providedMediaType, supportedMediaTypes))
                .contains(providedMediaType)
                .contains(supportedMediaTypes.toString());
    }
}
