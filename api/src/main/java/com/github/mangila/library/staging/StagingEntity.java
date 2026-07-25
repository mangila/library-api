package com.github.mangila.library.staging;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity(name = "Staging")
@Table(name = "staging")
public class StagingEntity {

  @Id
  @Column(name = "key", nullable = false)
  private String key;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "json", nullable = false)
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> json = new HashMap<>();

  @Column(name = "revision", nullable = false)
  private int revision;

  @Column(name = "last_modified", nullable = false)
  private String lastModified;

  private boolean processed;

  public StagingEntity() {
    // do nothing for JPA
  }

  public StagingEntity(
      String type, String key, Map<String, Object> json, int revision, String lastModified) {
    this.type = type;
    this.key = key;
    this.json = json;
    this.revision = revision;
    this.lastModified = lastModified;
  }

  public Map<String, Object> getJson() {
    return json;
  }

  public String getKey() {
    return key;
  }

  public String getLastModified() {
    return lastModified;
  }

  public int getRevision() {
    return revision;
  }

  public String getType() {
    return type;
  }

  public boolean isProcessed() {
    return processed;
  }

  public void setJson(Map<String, Object> json) {
    this.json = json;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setLastModified(String lastModified) {
    this.lastModified = lastModified;
  }

  public void setProcessed(boolean processed) {
    this.processed = processed;
  }

  public void setRevision(int revision) {
    this.revision = revision;
  }

  public void setType(String type) {
    this.type = type;
  }
}
