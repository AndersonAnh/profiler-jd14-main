package by.javaguru.profiler.api.controllers;

import by.javaguru.profiler.MysqlSQLTestContainerExtension;
import by.javaguru.profiler.util.AuthenticationTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MysqlSQLTestContainerExtension.class)
class InstitutionApiControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /*@Test
    void shouldReturn200AndJsonContentTypeWhenGetListOfInstitutions() {
        ResponseEntity<String> response = restTemplate.exchange(
                InstitutionsTestData.INSTITUTION_URL_TEMPLATE,
                HttpMethod.GET,
                getAuthHttpEntity(),
                String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    }*/

   /* @Test
    void shouldReturnNotEmptyListWhenGetListOfInstitutions() {
        ResponseEntity<List<InstitutionResponseDto>> responseEntity = restTemplate.exchange(
                InstitutionsTestData.INSTITUTION_URL_TEMPLATE,
                HttpMethod.GET,
                getAuthHttpEntity(),
                new ParameterizedTypeReference<>() {}
        );

        List<InstitutionResponseDto> actualResponse = responseEntity.getBody();
        assertNotNull(actualResponse);
    }*/

    /*@Test
    void shouldReturnExpectedInstitutionResponseJson() throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.exchange(
                InstitutionsTestData.INSTITUTION_URL_TEMPLATE,
                HttpMethod.GET,
                getAuthHttpEntity(),
                String.class
        );

        List<InstitutionResponseDto> actualResponse = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(actualResponse).hasSize(92);
    }*/

   /* @Test
    void shouldReturn200AndJsonContentTypeWhenPostInstitution() {
        ResponseEntity<String> response = restTemplate.exchange(
                InstitutionsTestData.INSTITUTION_URL_TEMPLATE,
                HttpMethod.POST,
                getAuthHttpEntity(),
                String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    }*/

    @SneakyThrows
    private HttpEntity<String> getAuthHttpEntity() {
        HttpEntity<Map<String, String>> requestHttpAuthEntity = AuthenticationTestData.createLoginRequestHttpEntity();

        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
                AuthenticationTestData.AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestHttpAuthEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        Map<String, String> responseMap = responseEntity.getBody();
        String token = "Bearer " + responseMap.get("token");

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, token);
        return new HttpEntity<>(headers);
    }
}
