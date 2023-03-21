package ru.kubsu.geocoder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Nominatim place class.
 * @param latitude param1
 * @param longitude param2
 * @param displayName param3
 * @param type param4
 */
@SuppressWarnings("Indentation")
public record NominatimPlace(
  @JsonProperty("lat") Double latitude,
  @JsonProperty("lon") Double longitude,
  @JsonProperty("display_name") String displayName,
  @JsonProperty("type") String type
) {
  public NominatimPlace() {
    this(0.0, 0.0, "", "");
  }
}
