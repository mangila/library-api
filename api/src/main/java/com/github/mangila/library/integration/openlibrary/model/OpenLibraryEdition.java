package com.github.mangila.library.integration.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenLibraryEdition(
    String title,
    @JsonProperty("title_prefix") String titlePrefix,
    String subtitle,
    @JsonProperty("other_titles") List<String> otherTitles,
    List<OpenLibraryReference> authors,
    @JsonProperty("by_statement") String byStatement,
    @JsonProperty("publish_date") String publishDate,
    @JsonProperty("copyright_date") String copyrightDate,
    @JsonProperty("edition_name") String editionName,
    List<OpenLibraryReference> languages,
    JsonNode description,
    JsonNode notes,
    List<String> genres,
    @JsonProperty("table_of_contents") List<OpenLibraryTocItem> tableOfContents,
    @JsonProperty("work_titles") List<String> workTitles,
    List<String> series,
    @JsonProperty("physical_dimensions") String physicalDimensions,
    @JsonProperty("physical_format") String physicalFormat,
    @JsonProperty("number_of_pages") int numberOfPages,
    List<String> subjects,
    String pagination,
    List<String> lccn,
    String ocaid,
    @JsonProperty("oclc_numbers") List<String> oclcNumbers,
    @JsonProperty("isbn_10") List<String> isbn10,
    @JsonProperty("isbn_13") List<String> isbn13,
    @JsonProperty("dewey_decimal_class") List<String> deweyDecimalClass,
    @JsonProperty("lc_classifications") List<String> lcClassifications,
    List<String> contributions,
    @JsonProperty("publish_places") List<String> publishPlaces,
    @JsonProperty("publish_country") String publishCountry,
    List<String> publishers,
    List<String> distributors,
    @JsonProperty("first_sentence") JsonNode firstSentence,
    String weight,
    List<String> location,
    @JsonProperty("scan_on_demand") boolean scanOnDemand,
    List<OpenLibraryReference> collections,
    List<String> uris,
    @JsonProperty("uri_descriptions") List<String> uriDescriptions,
    @JsonProperty("translation_of") String translationOf,
    List<OpenLibraryReference> works,
    @JsonProperty("source_records") List<String> sourceRecords,
    @JsonProperty("translated_from") List<OpenLibraryReference> translatedFrom,
    @JsonProperty("scan_records") List<OpenLibraryReference> scanRecords,
    List<OpenLibraryReference> volumes,
    @JsonProperty("accompanying_material") String accompanyingMaterial) {

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record OpenLibraryReference(String key) {}

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record OpenLibraryTocItem(
      @JsonProperty("class") String clazz,
      String label,
      String title,
      @JsonProperty("pagenum") String pageNum) {}
}
