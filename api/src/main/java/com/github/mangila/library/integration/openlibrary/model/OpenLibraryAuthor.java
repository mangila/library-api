package com.github.mangila.library.integration.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenLibraryAuthor(
    String name,
    boolean eastern_order,
    String personal_name,
    String enumeration,
    String title,
    List<String> alternate_names,
    List<String> uris,
    JsonNode bio,
    String location,
    String birth_date,
    String death_date,
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
