package com.github.mangila.library.author.rest;

import java.io.Serializable;
import java.util.List;

public record AuthorRestDto(String id, String name, List<String> books) implements Serializable {}
