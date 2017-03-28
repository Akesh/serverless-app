package com.payu.paga.base.io;

import java.io.OutputStream;

/**
 * Utilities for working with output streams
 * Created by BduPreez on 2016-06-29.
 */
public class OutputStreams {


    /**
     * Attempts to close the given output stream. NB: MUST be invoked from within a finally clause!
     *
     * @param outputStream the output stream to close
     */
    public static void close(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();

            } catch (Exception e) {
                // log.warn("Failed to close output stream: " + e.getMessage(), e);
            }
        }
    }

}
