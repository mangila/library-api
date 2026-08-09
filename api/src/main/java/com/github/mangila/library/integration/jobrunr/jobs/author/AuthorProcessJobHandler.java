package com.github.mangila.library.integration.jobrunr.jobs.author;

import com.github.mangila.library.author.domain.Author;
import com.github.mangila.library.author.domain.AuthorFactory;
import com.github.mangila.library.integration.jobrunr.jobs.shared.StagingQueue;
import io.github.mangila.ensure4j.Ensure;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.jobrunr.server.runner.ThreadLocalJobContext;

@ApplicationScoped
public class AuthorProcessJobHandler implements JobRequestHandler<AuthorProcessJobRequest> {

  private final StagingQueue<Author> authorStagingQueue;
  private final AuthorFactory authorFactory;

  public AuthorProcessJobHandler(
      StagingQueue<Author> authorStagingQueue, AuthorFactory authorFactory) {
    this.authorStagingQueue = authorStagingQueue;
    this.authorFactory = authorFactory;
  }

  @Override
  public void run(AuthorProcessJobRequest jobRequest) throws Exception {
    final Map<String, String> csvRecord = jobRequest.csvRecord();
    final JobContext jobContext = ThreadLocalJobContext.getJobContext();
    if (csvRecord.isEmpty()) {
      throw new IllegalArgumentException("CSV record is empty");
    }
    final String type = csvRecord.get("type");
    Ensure.notBlank(type, "type was blank or null");
    final String key = csvRecord.get("key");
    Ensure.notBlank(key, "key was blank or null");
    final String revision = csvRecord.get("revision");
    Ensure.notBlank(revision, "revision was blank or null");
    final String lastModified = csvRecord.get("last_modified");
    Ensure.notBlank(lastModified, "lastModified was blank or null");
    final String json = csvRecord.get("JSON");
    Ensure.notBlank(json, "json was blank or null");
    jobContext.logger().info("type: " + type);
    jobContext.logger().info("key: " + key);
    jobContext.logger().info("revision: " + revision);
    jobContext.logger().info("lastModified: " + lastModified);
    jobContext.logger().info("json: " + json);
    Author author = authorFactory.create("john", Collections.emptyList());
    final boolean queued = authorStagingQueue.offer(author, Duration.ofMinutes(1));
    if (!queued) {
      throw new IllegalStateException("Author could not be queued");
    }
  }
}
