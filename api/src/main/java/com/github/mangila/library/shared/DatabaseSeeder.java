package com.github.mangila.library.shared;

import com.github.mangila.library.author.domain.Author;
import com.github.mangila.library.author.domain.AuthorService;
import com.github.mangila.library.author.shared.AuthorMapper;
import com.github.mangila.library.book.domain.Book;
import com.github.mangila.library.book.domain.BookService;
import com.github.mangila.library.book.shared.BookMapper;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

@IfBuildProfile("dev")
@Dependent
public class DatabaseSeeder {

  private final AuthorService authorService;
  private final BookService bookService;
  private final AuthorMapper authorMapper;
  private final BookMapper bookMapper;

  public DatabaseSeeder(
      AuthorService authorService,
      BookService bookService,
      AuthorMapper authorMapper,
      BookMapper bookMapper) {
    this.authorService = authorService;
    this.bookService = bookService;
    this.authorMapper = authorMapper;
    this.bookMapper = bookMapper;
  }

  @Transactional
  public void seed(@Observes StartupEvent event) throws URISyntaxException, IOException {
    final List<Author> authors =
        readCsv("data/authors.csv").stream().map(authorMapper::toDomain).toList();
    final List<Book> books =
        readCsv("data/books.csv").stream()
            .map(
                csvRecord -> {
                  final int index = ThreadLocalRandom.current().nextInt(authors.size());
                  final Author author = authors.get(index);
                  final Book book = bookMapper.toDomain(csvRecord, author.id());
                  author.addBook(book.id());
                  return book;
                })
            .toList();
    authorService.saveAll(authors);
    bookService.saveAll(books);
  }

  private List<CSVRecord> readCsv(String resourceName) throws IOException, URISyntaxException {
    final CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader().get();
    final URL resource = Thread.currentThread().getContextClassLoader().getResource(resourceName);
    final Path path = Paths.get(resource.toURI());
    try (final var reader = Files.newBufferedReader(path);
        final var csvParser = csvFormat.parse(reader)) {
      return csvParser.getRecords();
    }
  }
}
