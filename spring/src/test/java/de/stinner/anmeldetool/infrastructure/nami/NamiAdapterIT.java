//package de.stinner.anmeldetool.domain.nami.service;
//
//import com.github.tomakehurst.wiremock.admin.model.ServeEventQuery;
//import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
//import de.stinner.anmeldetool.base.BaseIntegrationTest;
//import de.stinner.anmeldetool.base.IntegrationTestConfiguration;
//import de.stinner.anmeldetool.domain.usermanagement.namimembers.service.NamiMemberService;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
//import org.springframework.context.annotation.Import;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//import static com.github.tomakehurst.wiremock.client.WireMock.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.*;
//
//
//@AutoConfigureWireMock(port = 43500)
//@Slf4j
//@Import(IntegrationTestConfiguration.class)
//class NamiAdapterIT extends BaseIntegrationTest {
//    @MockBean
//    NamiMemberService namiMemberServiceMock;
//    @Autowired
//    private NamiService namiService;
//
//    @Autowired
//    private Environment environment;
//
//    @Test
//    void when_namiImport_withWorkingCredentials_then_noExceptionIsThrown() {
//
//        String username = "username";
//        String password = "password";
//        String groupId = "groupId";
//
//        setupWiremock(username, password, groupId);
//
//        try {
//            namiService.namiImport(username, password, groupId);
//        } catch (Exception e) {
//            log.warn("Ex", e);
//        }
//
//
//        List<ServeEvent> serveEvents = getAllServeEvents(ServeEventQuery.ALL);
//        List<ServeEvent> serveEventsUnmatched = getAllServeEvents(ServeEventQuery.ALL_UNMATCHED);
//        String s = new String(serveEvents.get(1).getRequest().getBody(), StandardCharsets.UTF_8);
//
//        verify(namiMemberServiceMock, times(1)).importNamiMembers(anyList());
//    }
//
//    @SneakyThrows
//    private void setupWiremock(String username, String password, String groupId) {
//        // sessionStartup
//        String activateLoginLocation = "/some/url/to/activate/login?redirect=pages%2Floggedin.jsp";
//        stubFor(post(urlEqualTo(NamiApiEndpoints.LOGIN))
//                        .withHeader(
//                                HttpHeaders.CONTENT_TYPE,
//                                containing(MediaType.APPLICATION_FORM_URLENCODED.toString())
//                        )
//                        .withRequestBody(binaryEqualTo(
//                                        ("username=" + username + "&password=" + password + "&redirectTo=.%2Fpages%2Floggedin.jsp&Login=API").getBytes()
//                                )
//                        )
//                        .willReturn(
//                                aResponse().withStatus(302).withHeader(HttpHeaders.LOCATION, "http://localhost:43500" + activateLoginLocation)
////                        temporaryRedirect("http://localhost:43500" + activateLoginLocation)
//                        )
//        );
//
//        // activateLogin
//        String jsessionId = "OR7VONJokSYlmhsQUipKNgNx.srv-nami06";
//        stubFor(get(urlEqualTo(activateLoginLocation))
//                .willReturn(
//                        ok().withHeader(HttpHeaders.SET_COOKIE, "JSESSIONID=" + jsessionId + "; Path=/ica;")
//                )
//        );
//
//        // getAllNamiMembers
////        NamiMembersWrapper namiMembersWrapper = new NamiMembersWrapper();
////        NamiMember memberA = new NamiMember();
////        memberA.setMemberId(12345);
////        memberA.setFirstname("Max");
////        memberA.setLastname("Musterman");
////        memberA.setDateOfBirth(LocalDateTime.now());
////        memberA.setRank(Rank.JUNGPFADFINDER);
////        memberA.setGender(Gender.MALE);
////        List<NamiMember> namiMembers = List.of(
////                memberA
////        );
////        namiMembersWrapper.setSuccess(true);
////        namiMembersWrapper.setData(namiMembers);
////        namiMembersWrapper.setTotalEntries(namiMembers.size());
////
////        ObjectWriter objectWriter = JsonMapper.builder()
////                .findAndAddModules().build()
////                .writer().withDefaultPrettyPrinter();
////        String json = objectWriter.writeValueAsString(namiMembersWrapper);
////
////        stubFor(get(urlPathEqualTo(NamiApiEndpoints.allMembers(groupId)))
////                .withCookie("JSESSIONID", matching(jsessionId))
////                .willReturn(
////                        ok()
////                                .withBody(json)
////                )
////        );
//    }
//
////    private NamiService setupMockForTestNamiImportWithWorkingCredentials(
////            RestTemplate restTemplateMock, NamiMemberService namiMemberServiceMock,
////            String username, String password, String groupId
////    ) {
////        NamiService service = new NamiService(restTemplateMock, namiMemberServiceMock);
////
////        // sessionStartup
////        String activateLoginLocation = "https://example.com/some/url/to/activate/login?redirect=pages/loggedin.jsp";
////
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
////
////        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
////        map.add("username", username);
////        map.add("password", password);
////        map.add("redirectTo", "./pages/loggedin.jsp");
////        map.add("Login", "API");
////
////        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
////
////        @SuppressWarnings("unchecked")
////        ResponseEntity<Void> responseEntityMockForSessionStartup = (ResponseEntity<Void>) mock(ResponseEntity.class);
////        HttpStatusCode statusCode3xxMock = mock(HttpStatusCode.class);
////        HttpHeaders locationHeader = new HttpHeaders();
////        locationHeader.add(HttpHeaders.LOCATION, activateLoginLocation);
////
////        when(statusCode3xxMock.is3xxRedirection()).thenReturn(true);
////        when(responseEntityMockForSessionStartup.getStatusCode()).thenReturn(statusCode3xxMock);
////        when(responseEntityMockForSessionStartup.getHeaders()).thenReturn(locationHeader);
////
////        when(restTemplateMock.exchange(NamiApiEndpoints.LOGIN, HttpMethod.POST, entity, Void.class)).thenReturn(
////                responseEntityMockForSessionStartup
////        );
////
////        // activateLogin
////        @SuppressWarnings("unchecked")
////        ResponseEntity<Void> responseEntityMockForActivateLogin = (ResponseEntity<Void>) mock(ResponseEntity.class);
////        HttpHeaders sessionCoockieHeader = new HttpHeaders();
////        String jsessionIdCookie = "JSESSIONID=OR7VONJokSYlmhsQUipKNgNx.srv-nami06";
////        sessionCoockieHeader.add(
////                HttpHeaders.SET_COOKIE, jsessionIdCookie + "; Path=/ica;"
////        );
////        when(responseEntityMockForActivateLogin.getHeaders()).thenReturn(sessionCoockieHeader);
////
////        when(restTemplateMock.exchange(activateLoginLocation, HttpMethod.GET, null, Void.class)).thenReturn(
////                responseEntityMockForActivateLogin
////        );
////
////        // getAllNamiMembers
////
////
////        return service;
////    }
//}
