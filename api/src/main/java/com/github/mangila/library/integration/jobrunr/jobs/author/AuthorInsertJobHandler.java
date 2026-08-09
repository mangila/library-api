package com.github.mangila.library.integration.jobrunr.jobs.author;

import com.github.mangila.library.author.domain.Author;
import com.github.mangila.library.author.domain.AuthorService;
import com.github.mangila.library.integration.jobrunr.jobs.shared.StagingQueue;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AuthorInsertJobHandler {

  private static final int BATCH_SIZE = 256;

  private final StagingQueue<Author> authorStagingQueue;
  private final AuthorService authorService;

  public AuthorInsertJobHandler(
      StagingQueue<Author> authorStagingQueue, AuthorService authorService) {
    this.authorStagingQueue = authorStagingQueue;
    this.authorService = authorService;
  }

  @Scheduled(every = "5s")
  public void run() {
    if (authorStagingQueue.isEmpty()) {
      return;
    }
    final List<Author> authors = new ArrayList<>(BATCH_SIZE);
    while (!authorStagingQueue.isEmpty()) {
      int _ = authorStagingQueue.drainTo(authors, BATCH_SIZE);
      authorService.saveAll(authors);
      authors.clear();
    }
  }
}
