/*
 * Copyright (C) 2005-2017 UCWeb Inc. All rights reserved.
 *  Description :Source.java
 *
 *  Creation    : 2017-06-03
 *  Author      : zhonglian.wzl@alibaba-inc.com
 */
package vanda.wzl.vandadownloader.quark_okio;

import java.io.Closeable;
import java.io.IOException;

/**
 * Supplies a stream of bytes. Use this interface to read data from wherever
 * it's located: from the network, storage, or a buffer in memory. Sources may
 * be layered to transform supplied data, such as to decompress, decrypt, or
 * remove protocol framing.
 *
 * <p>Most applications shouldn't operate on a source directly, but rather on a
 * {@link QuarkBufferedSource} which is both more efficient and more convenient. Use
 * {@link okio.Okio#buffer(okio.Source)} to wrap any source with a buffer.
 *
 * <p>Sources are easy to test: just use a {@link QuarkBuffer} in your tests, and
 * fill it with the data your application is to read.
 *
 * <h3>Comparison with InputStream</h3>
 * This interface is functionally equivalent to {@link java.io.InputStream}.
 *
 * <p>{@code InputStream} requires multiple layers when consumed data is
 * heterogeneous: a {@code DataInputStream} for primitive values, a {@code
 * BufferedInputStream} for buffering, and {@code InputStreamReader} for
 * strings. This class uses {@code BufferedSource} for all of the above.
 *
 * <p>Source avoids the impossible-to-implement {@linkplain
 * java.io.InputStream#available available()} method. Instead callers specify
 * how many bytes they {@link QuarkBufferedSource#require require}.
 *
 * <p>Source omits the unsafe-to-compose {@linkplain java.io.InputStream#mark
 * mark and reset} state that's tracked by {@code InputStream}; instead, callers
 * just buffer what they need.
 *
 * <p>When implementing a source, you don't need to worry about the {@linkplain
 * java.io.InputStream#read single-byte read} method that is awkward to implement efficiently
 * and returns one of 257 possible values.
 *
 * <p>And source has a stronger {@code skip} method: {@link QuarkBufferedSource#skip}
 * won't return prematurely.
 *
 * <h3>Interop with InputStream</h3>
 * Use {@link QuarkOkio#source} to adapt an {@code InputStream} to a source. Use
 * {@link QuarkBufferedSource#inputStream} to adapt a source to an {@code
 * InputStream}.
 */
public interface QuarkSource extends Closeable {
  /**
   * Removes at least 1, and up to {@code byteCount} bytes from this and appends
   * them to {@code sink}. Returns the number of bytes read, or -1 if this
   * source is exhausted.
   */
  long read(QuarkBuffer sink, long byteCount) throws IOException;

  /** Returns the timeout for this source. */
  Timeout timeout();

  /**
   * Closes this source and releases the resources held by this source. It is an
   * error to read a closed source. It is safe to close a source more than once.
   */
  @Override void close() throws IOException;
}
