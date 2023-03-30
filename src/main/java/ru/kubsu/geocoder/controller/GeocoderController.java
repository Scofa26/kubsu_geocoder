package ru.kubsu.geocoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kubsu.geocoder.client.NominatimClient;
import ru.kubsu.geocoder.model.Address;
import ru.kubsu.geocoder.service.AddressService;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author chidasophia
 */
@RestController
@RequestMapping("geocoder")
@SuppressWarnings({"Indentation", "PMD.SingularField"})
public class GeocoderController {
    private final AddressService addressService;
    private final NominatimClient nominatimClient;
    @Autowired
    public GeocoderController(final AddressService addressService,
                              final NominatimClient nominatimClient) {
      this.addressService = addressService;
      this.nominatimClient = nominatimClient;
    }


    @GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Address> search(@RequestParam final String query) {
        return addressService.search(query)
          .map(nominatimPlace -> ResponseEntity
        .status(HttpStatus.OK)
        .body(nominatimPlace)).orElseGet(() -> ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .build());

    }


  @GetMapping(value = "/reverse", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Address> reverse(@RequestParam final Double lat, @RequestParam final Double lon) {
    return addressService.reverse(lat, lon)
      .map(nominatimPlace -> ResponseEntity
        .status(HttpStatus.OK)
        .body(nominatimPlace)).orElseGet(() -> ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .build());

  }

}

