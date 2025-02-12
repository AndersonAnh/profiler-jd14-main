package by.javaguru.profiler.api.controllers;

import by.javaguru.profiler.MysqlSQLTestContainerExtension;
import by.javaguru.profiler.persistence.model.CurriculumVitae;
import by.javaguru.profiler.persistence.repository.CurriculumVitaeRepository;
import by.javaguru.profiler.usecasses.dto.CurriculumVitaeRequestDto;
import by.javaguru.profiler.usecasses.dto.CurriculumVitaeResponseDto;
import by.javaguru.profiler.util.AuthenticationTestData;
import by.javaguru.profiler.util.CurriculumVitaeTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ExtendWith(MysqlSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CurriculumVitaeApiControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CurriculumVitaeRepository curriculumVitaeRepository;

    private static final int EXPECTED_CVS_COUNT = 1;
    private static final String NEW_NAME = "Jack";

    @Test
    void shouldReturn201AndJsonContentTypeWhenSaveCurriculumVitaeSuccessfully() {
        CurriculumVitaeRequestDto request = CurriculumVitaeTestData.getValidCvRequestDto().withImageUuid(null).build();
        HttpEntity<CurriculumVitaeRequestDto> requestEntity = new HttpEntity<>(request, getAuthHeader());

        ResponseEntity<CurriculumVitaeResponseDto> responseEntity = restTemplate.exchange(
                CurriculumVitaeTestData.CVS_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                CurriculumVitaeResponseDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

        CurriculumVitaeResponseDto actualResponse = responseEntity.getBody();
        CurriculumVitae created = curriculumVitaeRepository.getReferenceByUuid(actualResponse.uuid());
        curriculumVitaeRepository.delete(created);
    }

    @Test
    void shouldReturnExpectedResponseJsonWhenSaveCurriculumVitaeSuccessfully() {
        CurriculumVitaeRequestDto request = CurriculumVitaeTestData.getValidCvRequestDto().withImageUuid(null).build();
        HttpEntity<CurriculumVitaeRequestDto> requestEntity = new HttpEntity<>(request, getAuthHeader());

        ResponseEntity<CurriculumVitaeResponseDto> responseEntity = restTemplate.exchange(
                CurriculumVitaeTestData.CVS_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                CurriculumVitaeResponseDto.class);

        CurriculumVitaeResponseDto actualResponse = responseEntity.getBody();
        CurriculumVitaeResponseDto expected = CurriculumVitaeTestData.getCvResponseDtoByCvRequestDto(request)
                .withUuid(actualResponse.uuid()).build();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expected, actualResponse);

        CurriculumVitae created = curriculumVitaeRepository.getReferenceByUuid(actualResponse.uuid());
        curriculumVitaeRepository.delete(created);
    }

    @Test
    void shouldReturn200AndJsonContentTypeWhenGetAllCvOfUserSuccessfully() {
        HttpEntity<String> requestEntity = new HttpEntity<>(getAuthHeader());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                CurriculumVitaeTestData.CVS_URL_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    void shouldReturnExpectedListOfCvsOfUserWhenGetAllCvOfUserSuccessfully() {
        HttpEntity<String> requestEntity = new HttpEntity<>(getAuthHeader());

        ResponseEntity<List<CurriculumVitaeResponseDto>> responseEntity = restTemplate.exchange(
                CurriculumVitaeTestData.CVS_URL_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        List<CurriculumVitaeResponseDto> actualResponse = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(actualResponse).hasSize(EXPECTED_CVS_COUNT);
    }

    @Test
    void shouldReturn200AndJsonContentTypeWhenGetCvOfUserSuccessfully() {
        HttpEntity<String> requestEntity = new HttpEntity<>(getAuthHeader());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                CurriculumVitaeTestData.CVS_UUID_URL_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                String.class,
                CurriculumVitaeTestData.CV_UUID_FROM_DB);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    void shouldReturnExpectedCvWhenGetCvOfUserSuccessfully() {
        HttpEntity<String> requestEntity = new HttpEntity<>(getAuthHeader());

        ResponseEntity<CurriculumVitaeResponseDto> responseEntity = restTemplate.exchange(
                CurriculumVitaeTestData.CVS_UUID_URL_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                CurriculumVitaeResponseDto.class,
                CurriculumVitaeTestData.CV_UUID_FROM_DB);

        CurriculumVitaeResponseDto actualResponse = responseEntity.getBody();
        CurriculumVitaeResponseDto expected = CurriculumVitaeTestData.getCvResponseDtoForExistingUser();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expected, actualResponse);
    }

    @Sql(scripts = "classpath:testdata/restore_cv.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldReturn200AndJsonContentTypeWhenUpdateCurriculumVitaeSuccessfully() {
        CurriculumVitaeRequestDto request = CurriculumVitaeTestData.getValidCvRequestDto().withName(NEW_NAME).withImageUuid(null).build();
        HttpEntity<CurriculumVitaeRequestDto> requestEntity = new HttpEntity<>(request, getAuthHeader());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                CurriculumVitaeTestData.CVS_UUID_URL_TEMPLATE,
                HttpMethod.PUT,
                requestEntity,
                String.class,
                CurriculumVitaeTestData.CV_UUID_FROM_DB);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Sql(scripts = "classpath:testdata/restore_cv.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldReturnCvWithExpectedUpdatesWhenUpdateCurriculumVitaeSuccessfully() {
        CurriculumVitaeRequestDto request = CurriculumVitaeTestData.getValidCvRequestDto().withName(NEW_NAME).withImageUuid(null).build();
        HttpEntity<CurriculumVitaeRequestDto> requestEntity = new HttpEntity<>(request, getAuthHeader());

        ResponseEntity<CurriculumVitaeResponseDto> responseEntity = restTemplate.exchange(
                CurriculumVitaeTestData.CVS_UUID_URL_TEMPLATE,
                HttpMethod.PUT,
                requestEntity,
                CurriculumVitaeResponseDto.class,
                CurriculumVitaeTestData.CV_UUID_FROM_DB);

        CurriculumVitaeResponseDto actualResponse = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(CurriculumVitaeTestData.CV_UUID_FROM_DB, actualResponse.uuid());
        assertEquals(NEW_NAME, actualResponse.name());
    }

    private HttpHeaders getAuthHeader() {
        HttpEntity<Map<String, String>> requestHttpAuthEntity = AuthenticationTestData.createLoginRequestHttpEntity();

        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
                AuthenticationTestData.AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestHttpAuthEntity,
                new ParameterizedTypeReference<>() {
                });

        Map<String, String> responseMap = responseEntity.getBody();
        String token = "Bearer " + responseMap.get("token");

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, token);
        return headers;
    }
}
