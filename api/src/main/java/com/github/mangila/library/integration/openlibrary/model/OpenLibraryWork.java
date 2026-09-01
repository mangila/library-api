package com.github.mangila.library.integration.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenLibraryWork(
    String title,
    String subtitle,
    List<OpenLibraryAuthorRole> authors,
    List<OpenLibraryTranslatedString> translated_titles,
    List<String> subjects,
    List<String> subject_places,
    List<String> subject_times,
    List<String> subject_people,
    JsonNode description,
    List<String> dewey_number,
    List<String> lc_classifications,
    JsonNode first_sentence,
    List<OpenLibraryLanguage> original_languages,
    List<String> other_titles,
    String first_publish_date,
    List<OpenLibraryLink> links,
    JsonNode notes,
    OpenLibraryEdition cover_edition,
    List<Integer> covers) {

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record OpenLibraryAuthorRole(String author, String role, String as) {}

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record OpenLibraryTranslatedString(OpenLibraryLanguage language, String text) {}

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record OpenLibraryLanguage(
      String name, String code, boolean deprecated, OpenLibraryLanguage current) {}

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record OpenLibraryLink(String title, String url) {}
}
