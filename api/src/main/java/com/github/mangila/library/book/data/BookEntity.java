package com.github.mangila.library.book.data;

import jakarta.persistence.*;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.type.SqlTypes;

@Entity(name = "Book")
@Table(name = "book")
@Audited
public class BookEntity {

  @Id
  @JdbcTypeCode(Types.VARCHAR)
  private UUID id;

  @JdbcTypeCode(Types.VARCHAR)
  private UUID authorId;

  @Column(name = "title", nullable = false)
  @JdbcTypeCode(Types.VARCHAR)
  private String title;

  @Enumerated(EnumType.STRING)
  private Category category;

  @Column(name = "publication_date", nullable = false)
  @JdbcTypeCode(Types.DATE)
  private LocalDate publicationDate;

  @JdbcTypeCode(Types.VARCHAR)
  private String description;

  @Column(name = "metadata")
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> metadata = new HashMap<>();

  @Column(name = "created_at")
  @NotAudited
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @Column(name = "rev_version")
  @Version
  private Long version;

  public BookEntity() {
    // do nothing for JPA
  }

  public UUID getAuthorId() {
    return authorId;
  }

  public Category getCategory() {
    return category;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public String getDescription() {
    return description;
  }

  public UUID getId() {
    return id;
  }

  public Map<String, Object> getMetadata() {
    return metadata;
  }

  public LocalDate getPublicationDate() {
    return publicationDate;
  }

  public String getTitle() {
    return title;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public Long getVersion() {
    return version;
  }

  public void setAuthorId(UUID authorId) {
    this.authorId = authorId;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setMetadata(Map<String, Object> metadata) {
    this.metadata = metadata;
  }

  public void setPublicationDate(LocalDate publicationDate) {
    this.publicationDate = publicationDate;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
