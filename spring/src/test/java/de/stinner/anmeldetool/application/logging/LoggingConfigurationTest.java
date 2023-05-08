package de.stinner.anmeldetool.application.logging;

import com.sun.security.auth.UserPrincipal;
import de.stinner.anmeldetool.application.rest.ApiEndpoints;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(OutputCaptureExtension.class)
class LoggingConfigurationTest {

    @Test
    @SneakyThrows
    void shouldNotLogRequestToActuatorLiveness(CapturedOutput output) {
        LoggingConfiguration loggingConfiguration = new LoggingConfiguration();
        CommonsRequestLoggingFilter filter = loggingConfiguration.requestLoggingFilter();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        request.setRequestURI(ApiEndpoints.Actuator.LIVENESS);

        filter.doFilter(request, response, chain);

        assertThat(output.getOut()).isEmpty();
    }

    @Test
    @SneakyThrows
    void shouldNotLogRequestToActuatorReadiness(CapturedOutput output) {
        LoggingConfiguration loggingConfiguration = new LoggingConfiguration();
        CommonsRequestLoggingFilter filter = loggingConfiguration.requestLoggingFilter();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        request.setRequestURI(ApiEndpoints.Actuator.READINESS);

        filter.doFilter(request, response, chain);

        assertThat(output.getOut()).isEmpty();
    }

    @Test
    @SneakyThrows
    void shouldLogQueryRequestFromAnonymousUser(CapturedOutput output) {
        LoggingConfiguration loggingConfiguration = new LoggingConfiguration();
        CommonsRequestLoggingFilter filter = loggingConfiguration.requestLoggingFilter();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        request.setMethod("GET");
        request.setRequestURI("/some/resource");
        request.setQueryString("id=1234");

        response.setStatus(200);

        try (MockedStatic<RequestContextHolder> ignored = mockStatic(RequestContextHolder.class)) {
            ServletRequestAttributes requestAttributesMock = mock(ServletRequestAttributes.class);
            when(RequestContextHolder.getRequestAttributes()).thenReturn(requestAttributesMock);
            when(requestAttributesMock.getResponse()).thenReturn(response);

            filter.doFilter(request, response, chain);
        }


        assertThat(output.getOut()).contains(
                "Request from user: Anonymous User, Method: GET, Path: http://localhost/some/resource, Query: id=1234"
        );

        assertThat(output.getOut())
                .containsPattern(
                        "Finished request from user: Anonymous User, Method: GET, Path: http://localhost/some/resource, Query: id=1234, HttpCode: 200, Duration: \\d+ms"
                );
    }

    @Test
    @SneakyThrows
    void shouldLogRequestFromAnonymousUser(CapturedOutput output) {
        LoggingConfiguration loggingConfiguration = new LoggingConfiguration();
        CommonsRequestLoggingFilter filter = loggingConfiguration.requestLoggingFilter();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        request.setMethod("GET");
        request.setRequestURI("/some/resource");

        response.setStatus(200);

        try (MockedStatic<RequestContextHolder> ignored = mockStatic(RequestContextHolder.class)) {
            ServletRequestAttributes requestAttributesMock = mock(ServletRequestAttributes.class);
            when(RequestContextHolder.getRequestAttributes()).thenReturn(requestAttributesMock);
            when(requestAttributesMock.getResponse()).thenReturn(response);

            filter.doFilter(request, response, chain);
        }

        assertThat(output.getOut()).contains(
                "Request from user: Anonymous User, Method: GET, Path: http://localhost/some/resource"
        );

        assertThat(output.getOut())
                .containsPattern(
                        "Finished request from user: Anonymous User, Method: GET, Path: http://localhost/some/resource, HttpCode: 200, Duration: \\d+ms"
                );
    }

    @Test
    @SneakyThrows
    void shouldLogQueryRequestFromTestUser(CapturedOutput output) {
        LoggingConfiguration loggingConfiguration = new LoggingConfiguration();
        CommonsRequestLoggingFilter filter = loggingConfiguration.requestLoggingFilter();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        request.setMethod("GET");
        request.setRequestURI("/some/resource");
        request.setQueryString("id=1234");
        request.setUserPrincipal(new UserPrincipal("TestUser"));

        response.setStatus(200);

        try (MockedStatic<RequestContextHolder> ignored = mockStatic(RequestContextHolder.class)) {
            ServletRequestAttributes requestAttributesMock = mock(ServletRequestAttributes.class);
            when(RequestContextHolder.getRequestAttributes()).thenReturn(requestAttributesMock);
            when(requestAttributesMock.getResponse()).thenReturn(response);

            filter.doFilter(request, response, chain);
        }

        assertThat(output.getOut()).contains(
                "Request from user: TestUser, Method: GET, Path: http://localhost/some/resource, Query: id=1234"
        );

        assertThat(output.getOut())
                .containsPattern(
                        "Finished request from user: TestUser, Method: GET, Path: http://localhost/some/resource, Query: id=1234, HttpCode: \\d+, Duration: \\d+ms"
                );
    }

    @Test
    @SneakyThrows
    void shouldLogRequestFromTestUser(CapturedOutput output) {
        LoggingConfiguration loggingConfiguration = new LoggingConfiguration();
        CommonsRequestLoggingFilter filter = loggingConfiguration.requestLoggingFilter();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        request.setMethod("GET");
        request.setRequestURI("/some/resource");
        request.setUserPrincipal(new UserPrincipal("TestUser"));

        response.setStatus(200);

        try (MockedStatic<RequestContextHolder> ignored = mockStatic(RequestContextHolder.class)) {
            ServletRequestAttributes requestAttributesMock = mock(ServletRequestAttributes.class);
            when(RequestContextHolder.getRequestAttributes()).thenReturn(requestAttributesMock);
            when(requestAttributesMock.getResponse()).thenReturn(response);

            filter.doFilter(request, response, chain);
        }

        assertThat(output.getOut()).contains(
                "Request from user: TestUser, Method: GET, Path: http://localhost/some/resource"
        );

        assertThat(output.getOut())
                .containsPattern(
                        "Finished request from user: TestUser, Method: GET, Path: http://localhost/some/resource, HttpCode: 200, Duration: \\d+ms"
                );
    }

    @Test
    @SneakyThrows
    void shouldLogStatusCodeZeroWhenNoResponse(CapturedOutput output) {
        LoggingConfiguration loggingConfiguration = new LoggingConfiguration();
        CommonsRequestLoggingFilter filter = loggingConfiguration.requestLoggingFilter();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        request.setMethod("GET");
        request.setRequestURI("/some/resource");

        try (MockedStatic<RequestContextHolder> ignored = mockStatic(RequestContextHolder.class)) {
            ServletRequestAttributes requestAttributesMock = mock(ServletRequestAttributes.class);
            when(RequestContextHolder.getRequestAttributes()).thenReturn(requestAttributesMock);
            when(requestAttributesMock.getResponse()).thenReturn(null);

            filter.doFilter(request, response, chain);
        }

        assertThat(output.getOut())
                .containsPattern(
                        "Finished request from user: Anonymous User, Method: GET, Path: http://localhost/some/resource, HttpCode: 0, Duration: \\d+ms"
                );
    }

    @Test
    void testScheduledAnnotationBeanPostProcessor() {
        LoggingConfiguration loggingConfiguration = new LoggingConfiguration();
        ScheduledAnnotationBeanPostProcessor processor = loggingConfiguration.scheduledAnnotationBeanPostProcessor();
        assertThat(processor).isInstanceOf(ScheduledAnnotationBeanPostProcessor.class);
    }
}
