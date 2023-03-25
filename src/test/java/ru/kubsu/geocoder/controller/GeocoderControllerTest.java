package ru.kubsu.geocoder.controller;

import org.h2.util.AbbaDetector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.kubsu.geocoder.client.NominatimClient;
import ru.kubsu.geocoder.dto.NominatimPlace;
import ru.kubsu.geocoder.model.Address;
import ru.kubsu.geocoder.repository.AddressRepository;
import ru.kubsu.geocoder.repository.TestRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GeocoderControllerTest {

  @MockBean
  private  NominatimClient nominatimClient;

  @Autowired
  private AddressRepository addressRepository;
  @LocalServerPort
  Integer port;
  private final TestRestTemplate testRestTemplate = new TestRestTemplate();
  @Autowired
  TestRepository testRepository ;

  @BeforeEach
  void setUp() {
    addressRepository.deleteAll();
  }

  @Test
  void searchWhenNominatimNotResponse() {
 when (nominatimClient.search(anyString())).thenReturn(Optional.empty());

    ResponseEntity<Address> response = testRestTemplate.
      getForEntity("http://localhost:" + port + "/geocoder/status?address=кубгу",
        Address.class);


    assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    assertNull(response.getBody());

    //assertEquals("{\"id\":1,\"name\":\"test\",\"done\":null,\"mark\":null}", body);
    //System.out.println(response.getBody());
  }
  @Test
  void search() {
    final  Address testAddress = buildTestAddress();
    when (nominatimClient.search(anyString()))
      .thenReturn(Optional.of(buildTestPlace()));

    ResponseEntity<Address> response = testRestTemplate.
      getForEntity(
        "http://localhost:" + port + "/geocoder/status?address=кубгу",
        Address.class);

    assertEquals(HttpStatus.OK,response.getStatusCode());

    final Address body = response.getBody();

    assertEquals(testAddress, body);


    //assertEquals("{\"id\":1,\"name\":\"test\",\"done\":null,\"mark\":null}", body);
    //System.out.println(response.getBody());
  }

  private static NominatimPlace buildTestPlace()
  {
    // TODO : указать корректные данные? которые реально должны вернуться
    return new NominatimPlace(45.02036085,39.9292392390293029302,"кубгу","universty");
  }

  private static Address buildTestAddress(){
    return Address.of(buildTestPlace());
  }

}
