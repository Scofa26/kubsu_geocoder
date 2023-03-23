package ru.kubsu.geocoder.dto;


/**
 * TestService class.
 * @param status param1
 * @param error param2
 * @param path param3
 */

public record RestApiError(
    Integer status,
    String error,
    String path

) {
    public RestApiError() {
        this(0, "00", "0");
    }
}
