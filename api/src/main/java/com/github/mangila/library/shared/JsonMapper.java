package com.github.mangila.library.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Map;

@ApplicationScoped
public class JsonMapper {

  private final ObjectMapper objectMapper;

  public JsonMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public <T> T toObject(Map<String, Object> json, Class<T> clazz) {
    return objectMapper.convertValue(json, clazz);
  }
}
