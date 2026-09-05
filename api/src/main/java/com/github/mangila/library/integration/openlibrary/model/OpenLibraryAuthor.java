package com.github.mangila.library.integration.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenLibraryAuthor(
    String name,
    @JsonProperty("eastern_order") boolean easternOrder,
    @JsonProperty("personal_name") String personalName,
    String enumeration,
    String title,
    @JsonProperty("alternate_names") List<String> alternateNames,
    List<String> uris,
    JsonNode bio,
    String location,
    @JsonProperty("birth_date") String birthDate,
    @JsonProperty("death_date") String deathDate,
    String date,
    String wikipedia,
    List<OpenLibraryLink> links) {

  public String getBioText() {
    if (bio == null || bio.isNull()) return null;
    if (bio.isTextual()) return bio.asText();
    if (bio.has("value")) return bio.get("value").asText();
    return null;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record OpenLibraryLink(String title, String url) {}
}
