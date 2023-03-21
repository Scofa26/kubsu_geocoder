/**
 * Copyright 2023 Sophia Chida
 */

package ru.kubsu.geocoder.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kubsu.geocoder.dto.NominatimPlace;

import java.util.List;
import java.util.Optional;

/**
 *
 */
@FeignClient(value = "nominatim", url = "https://nominatim.openstreetmap.org")
public interface NominatimClient {
    String JSON_FORMAT = "json";
    @RequestMapping(method = RequestMethod.GET, value = "/search", produces = "application/json")
    List<NominatimPlace> search(@RequestParam(value = "q") String query,
                                @RequestParam(value = "format") String format);

    @RequestMapping(method = RequestMethod.GET, value = "/reverse", produces = "application/json")
    NominatimPlace reverse(@RequestParam(value = "lat") String query,
                           @RequestParam(value = "lon") String query1,
                           @RequestParam(value = "format") String format);
    @SuppressWarnings({"JavadocStyle", "OverloadMethodsDeclarationOrder", "InvalidJavadocPosition", "CommentsIndentation", "LineLength"})

  /**
   * Поиск обьекта на карте по адресной строке в свободном формате
   * @param query Строка поиска
   * @return Обьект адреса
   * В случае нескольких подходящих обьектов будем возвращен самый релевантный 3
   */
    default Optional<NominatimPlace> search(String query) {
        try {
            return Optional.of(search(query, JSON_FORMAT).get(0));
        } catch (Exception ex) {
            return Optional.empty();

        }

    }

}
