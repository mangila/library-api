package com.github.mangila.library.author.domain;

import java.util.List;
import java.util.UUID;

public record Author(
    UUID id,
    String openLibraryKey,
    String name,
    String personalName,
    List<String> alternateNames,
    List<String> uris,
    String bio,
    String location,
    String birthDate,
    String deathDate,
    String wikipedia,
    List<String> links,
    List<String> books,
    List<String> works) {}
