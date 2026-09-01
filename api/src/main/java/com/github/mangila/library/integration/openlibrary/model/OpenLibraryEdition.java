package com.github.mangila.library.integration.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenLibraryEdition(
    String title,
    String title_prefix,
    String subtitle,
    List<String> other_titles,
    List<OpenLibraryReference> authors,
    String by_statement,
    String publish_date,
    String copyright_date,
    String edition_name,
    List<OpenLibraryReference> languages,
    JsonNode description,
    JsonNode notes,
    List<String> genres,
    List<OpenLibraryTocItem> table_of_contents,
    List<String> work_titles,
    List<String> series,
    String physical_dimensions,
    String physical_format,
    int number_of_pages,
    List<String> subjects,
    String pagination,
    List<String> lccn,
    String ocaid,
    List<String> oclc_numbers,
    List<String> isbn_10,
    List<String> isbn_13,
    List<String> dewey_decimal_class,
    List<String> lc_classifications,
    List<String> contributions,
    List<String> publish_places,
    String publish_country,
    List<String> publishers,
    List<String> distributors,
    JsonNode first_sentence,
    String weight,
    List<String> location,
    boolean scan_on_demand,
    List<OpenLibraryReference> collections,
    List<String> uris,
    List<String> uri_descriptions,
    String translation_of,
    List<OpenLibraryReference> works,
    List<String> source_records,
    List<OpenLibraryReference> translated_from,
    List<OpenLibraryReference> scan_records,
    List<OpenLibraryReference> volumes,
    String accompanying_material) {

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record OpenLibraryReference(String key) {}

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record OpenLibraryTocItem(
      @JsonProperty("class") String clazz, String label, String title, String pagenum) {}
}
