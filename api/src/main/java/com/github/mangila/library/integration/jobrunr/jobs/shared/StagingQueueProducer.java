package com.github.mangila.library.integration.jobrunr.jobs.shared;

import com.github.mangila.library.author.domain.Author;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Produces;

public class StagingQueueProducer {

  private static final int BACK_PRESSURE = 1024;

  @Produces
  @Singleton
  public StagingQueue<Author> authorStagingQueue() {
    return new StagingQueue<>(BACK_PRESSURE);
  }
}
