package com.payu.paga.base.net;

import com.payu.paga.base.io.InputStreams;
import com.payu.paga.base.io.OutputStreams;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Created by akesh.patil on 16-03-2017.
 */
public class HttpUrlConnections {

    /**
     * Returns the input stream of the given HTTP URL connection if the connection is enabled for input (i.e. its
     * doInput flag is true) and has a response code < 400; otherwise returns the the error stream of the connection.
     *
     * @param con the HTTP URL connection
     * @return the input or error stream of the given connection wrapped in an optional
     * @throws IOException
     */
    public static InputStream getInputOrErrorStream(HttpURLConnection con) throws IOException {
        InputStream inputStream = null;
        if (con != null) {
            if (con.getDoInput()) {
                // doInput is true, so should have an input stream
                if (con.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                    inputStream = con.getInputStream();
                    // if the input stream is null then try the error stream
                    if (inputStream == null) {
                        inputStream = con.getErrorStream();
                    }

                } else { // HTTP response code >= 400
                    inputStream = con.getErrorStream();
                    // if the error stream is null then try the input stream
                    if (inputStream == null) {
                        inputStream = con.getInputStream();
                    }
                }
            } else {
                // doInput is false, but try the error stream just in case
                inputStream = con.getErrorStream();
            }
        }
        return inputStream;
    }

    /**
     * Reads the appropriate input or error stream from the given connection and returns its contents as a string.
     * Returns an empty string if no stream is available.
     */
    public static String getInputOrErrorStreamResponse(HttpsURLConnection con) throws IOException {
        return getInputOrErrorStreamResponse(con, "");
    }

    /**
     * Reads the appropriate input or error stream from the given connection and returns its contents as a string.
     * Returns the given default value if no stream is available.
     */
    public static String getInputOrErrorStreamResponse(HttpsURLConnection con, String defaultResponse) throws IOException {
        InputStream inputOrErrorStream = getInputOrErrorStream(con);
        return InputStreams.read(inputOrErrorStream);
    }

    /**
     * Synonym for release, since there is no real close on HTTPUrlConnection.
     */
    public static void close(HttpURLConnection con) {
        release(con);
    }

    /**
     * Attempts to release the given connection by closing its input stream / error and output streams.
     *
     * @param con the connection to release
     */
    public static void release(HttpURLConnection con) {
        if (con != null) {
            // Selectively closes the connection's input or error stream
            //            Optional<InputStream> inputStream = Optional.empty();
            //            try {
            //                inputStream = getInputOrErrorStream(con);
            //            } catch (Exception e) {
            //                log.error("Failed to release HTTP URL connection input: " + e.getMessage(), e);
            //            } finally {
            //                inputStream.ifPresent(InputStreams::close);
            //            }

            // Just attempts to close both the input stream AND the error stream just to be safe (regardless of the "fact"
            // that there should only be one of them)

            // Close the input stream (if any)
            InputStream inputStream = null;
            try {
                inputStream = con.getInputStream();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                InputStreams.close(inputStream);
            }

            // Close the error stream (if any)
            InputStream errorStream = null;
            try {
                errorStream = con.getErrorStream();
            } catch (Exception e) {
                //log.error("Failed to close the HTTP URL connection's error stream: " + e.getMessage(), e);
            } finally {
                InputStreams.close(errorStream);
            }

            // Selectively closes the connection's output stream
            //            OutputStream outputStream = null;
            //            try {
            //                if (con.getDoOutput()) {
            //                    outputStream = con.getOutputStream();
            //                }
            //            } catch (Exception e) {
            //                log.error("Failed to close the HTTP URL connection's output stream: " + e.getMessage(), e);
            //            } finally {
            //                OutputStreams.close(outputStream);
            //            }

            // Just attempts to close the connection's output stream
            OutputStream outputStream = null;
            try {
                outputStream = con.getOutputStream();
            } catch (Exception e) {
                if (con.getDoOutput()) {
                    //log.error("Failed to close the HTTP URL connection's output stream: " + e.getMessage(), e);
                }
            } finally {
                OutputStreams.close(outputStream);
            }
        }
    }

}
