package com.github.mangila.library.author.data;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity(name = "Author")
@Table(name = "author")
public class AuthorEntity {

  @Id private UUID id;

  @Column(name = "open_library_key", nullable = false)
  private String openLibraryKey;

  @Column(name = "name")
  private String name;

  @Column(name = "personalName")
  private String personalName;

  @Column(name = "alternateNames")
  @JdbcTypeCode(SqlTypes.JSON_ARRAY)
  private List<String> alternateNames = new ArrayList<>();

  @Column(name = "uris")
  @JdbcTypeCode(SqlTypes.JSON_ARRAY)
  private List<String> uris = new ArrayList<>();

  @Column(name = "bio")
  private String bio;

  @Column(name = "location")
  private String location;

  @Column(name = "birthDate")
  private String birthDate;

  @Column(name = "deathDate")
  private String deathDate;

  @Column(name = "wikipedia")
  private String wikipedia;

  @Column(name = "links")
  @JdbcTypeCode(SqlTypes.JSON_ARRAY)
  private List<String> links = new ArrayList<>();

  @Column(name = "books")
  @JdbcTypeCode(SqlTypes.JSON_ARRAY)
  private List<String> books = new ArrayList<>();

  @Column(name = "works")
  @JdbcTypeCode(SqlTypes.JSON_ARRAY)
  private List<String> works = new ArrayList<>();

  @Column(name = "open_library_json")
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> openLibraryJson = new HashMap<>();

  @Column(name = "created_at")
  @CreationTimestamp
  private Instant createdAt;

  public AuthorEntity() {
    // do nothing for JPA
  }

  public List<String> getAlternateNames() {
    return alternateNames;
  }

  public String getBio() {
    return bio;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public List<String> getBooks() {
    return books;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public String getDeathDate() {
    return deathDate;
  }

  public UUID getId() {
    return id;
  }

  public List<String> getLinks() {
    return links;
  }

  public String getLocation() {
    return location;
  }

  public String getName() {
    return name;
  }

  public Map<String, Object> getOpenLibraryJson() {
    return openLibraryJson;
  }

  public String getOpenLibraryKey() {
    return openLibraryKey;
  }

  public String getPersonalName() {
    return personalName;
  }

  public List<String> getUris() {
    return uris;
  }

  public String getWikipedia() {
    return wikipedia;
  }

  public List<String> getWorks() {
    return works;
  }

  public void setAlternateNames(List<String> alternateNames) {
    this.alternateNames = alternateNames;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public void setBooks(List<String> books) {
    this.books = books;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public void setDeathDate(String deathDate) {
    this.deathDate = deathDate;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setLinks(List<String> links) {
    this.links = links;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setOpenLibraryJson(Map<String, Object> originalJson) {
    this.openLibraryJson = originalJson;
  }

  public void setOpenLibraryKey(String openLibraryKey) {
    this.openLibraryKey = openLibraryKey;
  }

  public void setPersonalName(String personalName) {
    this.personalName = personalName;
  }

  public void setUris(List<String> uris) {
    this.uris = uris;
  }

  public void setWikipedia(String wikipedia) {
    this.wikipedia = wikipedia;
  }

  public void setWorks(List<String> works) {
    this.works = works;
  }
}
