package com.github.mangila.library.author.data;

import jakarta.persistence.*;
import java.sql.Types;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.type.SqlTypes;

@Entity(name = "Author")
@Table(name = "author")
@Audited
public class AuthorEntity {

  @Id
  @JdbcTypeCode(Types.VARCHAR)
  private UUID id;

  @Column(name = "name", nullable = false)
  @JdbcTypeCode(Types.VARCHAR)
  private String name;

  @Column(name = "books")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<String> books = new ArrayList<>();

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

  public AuthorEntity() {
    // do nothing for JPA
  }

  public List<String> getBooks() {
    return books;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public Long getVersion() {
    return version;
  }

  public void setBooks(List<String> books) {
    this.books = books;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
