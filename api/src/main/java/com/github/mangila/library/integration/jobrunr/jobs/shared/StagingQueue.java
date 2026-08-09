package com.github.mangila.library.integration.jobrunr.jobs.shared;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public final class StagingQueue<T> {

  private final BlockingQueue<T> queue;

  public StagingQueue(int capacity) {
    this.queue = new ArrayBlockingQueue<>(capacity);
  }

  public int drainTo(List<T> list, int maxElements) {
    return queue.drainTo(list, maxElements);
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }

  public boolean offer(T element, Duration timeout) throws InterruptedException {
    return queue.offer(element, timeout.toMillis(), TimeUnit.MILLISECONDS);
  }

  public int size() {
    return queue.size();
  }

  public T take() throws InterruptedException {
    return queue.take();
  }
}
