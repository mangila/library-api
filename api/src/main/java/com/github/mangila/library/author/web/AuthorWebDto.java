package com.github.mangila.library.author.web;

import java.util.List;

public record AuthorWebDto(String id, String name, List<String> books) {}
