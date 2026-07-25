package com.github.mangila.library.shared;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

public class ProgressInputStream extends FilterInputStream {

  private final Consumer<Long> progressCallback;
  private final long ratio;
  private long transferred;
  private long nextProgressUpdate = FileUtils.ONE_MB;

  public ProgressInputStream(InputStream in, long contentLength, Consumer<Long> progressCallback) {
    super(in);
    this.ratio = Math.max(1L, (long) (contentLength * 0.01));
    this.progressCallback = progressCallback;
  }

  @Override
  public int read(byte @NotNull [] b, int off, int len) throws IOException {
    int bytesRead = super.read(b, off, len);
    if (bytesRead > 0) {
      transferred += bytesRead;
      if (transferred >= nextProgressUpdate) {
        progressCallback.accept(transferred);
        nextProgressUpdate = transferred + ratio;
      }
    }
    return bytesRead;
  }
}
