package com.github.mangila.library.author.rest;

import java.io.Serializable;
import java.util.List;

public record AuthorRestDto(
    String id,
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
    List<String> works)
    implements Serializable {}
