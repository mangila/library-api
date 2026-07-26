package com.github.mangila.library.book.rest;

import com.github.mangila.library.book.data.Category;
import java.time.LocalDate;
import java.util.Map;

public record BookRestDto(
    String id,
    String authorId,
    String title,
    Category category,
    LocalDate publicationDate,
    String description,
    Map<String, Object> metadata) {}
