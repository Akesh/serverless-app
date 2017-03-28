package com.payu.paga.base.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Utilities for working with input streams.
 * Created by BduPreez on 2016-06-29.
 */
public class InputStreams {


    /**
     * Attempts to close the given input stream. NB: MUST be invoked from within a finally clause!
     *
     * @param inputStream the input stream to close
     */
    public static void close(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();

            } catch (Exception e) {
                //log.warn("Failed to close input stream: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Reads and returns the contents of the given input stream as a string.
     * NB: Does NOT close the stream!
     */
    public static String read(InputStream inputStream) throws IOException {
        StringBuilder response = new StringBuilder();
        //try {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        //} catch (Exception e) {
        //    log.error("Failed to read input stream: " + e.getMessage(), e);
        //    throw e;
        //}
        return response.toString();
    }

    /**
     * Reads and returns the contents of the given input stream as a string.
     * NB: Does NOT close the stream!
     */
    public static String read(InputStream inputStream, String charsetName) throws IOException {
        StringBuilder response = new StringBuilder();
        //try {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, charsetName));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        //} catch (Exception e) {
        //    log.error("Failed to read input stream: " + e.getMessage(), e);
        //    throw e;
        //}
        return response.toString();
    }
}
