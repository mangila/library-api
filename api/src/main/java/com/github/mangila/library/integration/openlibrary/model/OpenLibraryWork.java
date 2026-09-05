package com.github.mangila.library.integration.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenLibraryWork(
    String title,
    String subtitle,
    List<OpenLibraryAuthorRole> authors,
    @JsonProperty("translated_titles") List<OpenLibraryTranslatedString> translatedTitles,
    List<String> subjects,
    @JsonProperty("subject_places") List<String> subjectPlaces,
    @JsonProperty("subject_times") List<String> subjectTimes,
    @JsonProperty("subject_people") List<String> subjectPeople,
    JsonNode description,
    @JsonProperty("dewey_number") List<String> deweyNumber,
    @JsonProperty("lc_classifications") List<String> lcClassifications,
    @JsonProperty("first_sentence") JsonNode firstSentence,
    @JsonProperty("original_languages") List<OpenLibraryLanguage> originalLanguages,
    @JsonProperty("other_titles") List<String> otherTitles,
    @JsonProperty("first_publish_date") String firstPublishDate,
    List<OpenLibraryLink> links,
    JsonNode notes,
    @JsonProperty("cover_edition") OpenLibraryEdition coverEdition,
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
