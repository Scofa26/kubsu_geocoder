package ru.kubsu.geocoder.model;

import ru.kubsu.geocoder.dto.NominatimPlace;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 *
 */
@Entity
@SuppressWarnings({"PMD.AvoidFieldNameMatchingTypeName", "Indentation"})
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String address;
  private Double latitude;
  private Double longitude;
  private String query;


  public Integer getId() {
    return id;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(final String address) {
    this.address = address;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(final Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(final Double longitude) {
    this.longitude = longitude;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(final String query) {
    this.query = query;
  }

  @Override
  @SuppressWarnings("PMD.ControlStatementBraces")
  public boolean equals(final Object o) {
    if (this == o) {
       return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
   final Address address1 = (Address) o;
    return /*id.equals(address1.id)  && */
      address.equals(address1.address)
      && latitude.equals(address1.latitude)
      && longitude.equals(address1.longitude)
        && query.equals(address1.query);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, address, latitude, longitude, query);
  }

  @Override
  @SuppressWarnings("OperatorWrap")
  public String toString() {
    return "Address{" +
      "id=" + id +
      ", address='" + address + '\'' +
      ", latitude=" + latitude +
      ", longitude=" + longitude +
      ", query=" + query +

      '}';
  }

  public static Address of(final NominatimPlace place, final String query) {
    final Address result = new Address();
    result.setAddress(place.displayName());
    result.setLatitude(place.latitude());
    result.setLongitude(place.longitude());
    result.setQuery(query);
    return result;
  }

}
