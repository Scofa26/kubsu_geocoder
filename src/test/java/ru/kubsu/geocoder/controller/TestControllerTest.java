package ru.kubsu.geocoder.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.kubsu.geocoder.dto.RestApiError;
import ru.kubsu.geocoder.repository.TestRepository;
import ru.kubsu.geocoder.util.TestUtil;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestControllerTest {

    @LocalServerPort
    Integer port;
    TestRestTemplate testRestTemplate = new TestRestTemplate();
    @Autowired
    TestRepository testRepository ;



    @BeforeAll
    static void beforeAll() {
        System.out.println("BEFORE ALL");
    }

    @BeforeEach
    void setUp() {
        System.out.println("SET UP");
    }

    @Test
    void integrationTest() {
        System.out.println("TEST 1");

        ResponseEntity<ru.kubsu.geocoder.model.Test> response = testRestTemplate.
                getForEntity("http://localhost:" + port + "/tests/1?name=test",
                        ru.kubsu.geocoder.model.Test.class);

        final ru.kubsu.geocoder.model.Test body = response.getBody();
        assertEquals(1, body.getId());
        assertEquals("test", body.getName());
        assertEquals(null, body.getDone());
        assertEquals(null, body.getMark());

        //assertEquals("{\"id\":1,\"name\":\"test\",\"done\":null,\"mark\":null}", body);
        //System.out.println(response.getBody());
    }

    @Test
    void integrationTestWhenNameIsNull() {
        ResponseEntity<Map<String, String>> response = testRestTemplate
                .exchange("http://localhost:" + port + "/tests/1",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, String>>() {});

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        final Map<String, String> body = response.getBody();
        assertEquals("400", body.get("status"));
        assertEquals("Bad Request", body.get("error"));
        assertEquals("/tests/1", body.get("path"));

        //final String body = response.getBody();

        //System.out.println(body);
    }

    @Test
    void integrationTestWhenIdIsString() {
      ResponseEntity<RestApiError> response = testRestTemplate
              .exchange("http://localhost:" + port + "/tests/abc?name=test",
                      HttpMethod.GET,
                      null,
                      RestApiError.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        final RestApiError body = response.getBody();
        assertEquals(400, body.status());
        assertEquals("Bad Request", body.error());
        assertEquals("/tests/abc", body.path());

        //assertEquals("{\"id\":1,\"name\":\"test\",\"done\":null,\"mark\":null}", body);
        //System.out.println(response.getBody());
    }

  @Test
  void integrationTestPositiveForSave() {

    ResponseEntity<Void> response = testRestTemplate.
      getForEntity("http://localhost:" + port + "/tests/save?name=test",
        Void.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());

  }
  @Test
  void integrationTestNegativeForSave() {

    ResponseEntity<Void> response = testRestTemplate.
      getForEntity("http://localhost:" + port + "/tests/save?",
        Void.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

  }
  @Test
  void integrationTestPositiveForLoad() {
    ru.kubsu.geocoder.model.Test test = new ru.kubsu.geocoder.model.Test();
    test.setName("OneOneOne");
    testRepository.save(test);

    ResponseEntity<ru.kubsu.geocoder.model.Test> response = testRestTemplate.
      getForEntity("http://localhost:" + port + "/tests/load/OneOneOne",
        ru.kubsu.geocoder.model.Test.class);

    final ru.kubsu.geocoder.model.Test body = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1, body.getId());
    assertEquals("OneOneOne", body.getName());
    assertEquals(null, body.getDone());
    assertEquals(null, body.getMark());

  }
  @Test
  void integrationTestNegativeForLoad() {
    ResponseEntity<RestApiError> response = testRestTemplate
      .exchange("http://localhost:" + port + "/tests/load",
        HttpMethod.GET,
        null,
        RestApiError.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    final RestApiError body = response.getBody();
    assertEquals(400, body.status());
    assertEquals("Bad Request", body.error());
    assertEquals("/tests/load", body.path());

    //assertEquals("{\"id\":1,\"name\":\"test\",\"done\":null,\"mark\":null}", body);
    //System.out.println(response.getBody());
  }

  @AfterEach
    void tearDown() {
        System.out.println("TEAR DOWN");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("AFTER ALL");

    }
}
