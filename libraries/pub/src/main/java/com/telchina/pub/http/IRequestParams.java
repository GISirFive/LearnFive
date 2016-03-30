package com.telchina.pub.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * @author GISirFive
 * @since 2016-2-16 下午2:19:07
 */
public interface IRequestParams {

    /**
     * 获取参数集合对应的实体
     * @return
     * @author GISirFive
     */
    Object getParams();

    /**
     * Adds a key/value string pair to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value string for the new param.
     */
    void put(String key, String value);

    /**
     * Adds a file to the request.
     *
     * @param key  the key name for the new param.
     * @param file the file to add.
     * @throws FileNotFoundException throws if wrong File argument was passed
     */
    void put(String key, File file) throws FileNotFoundException;

    /**
     * Adds an input stream to the request.
     *
     * @param key    the key name for the new param.
     * @param stream the input stream to add.
     */
    void put(String key, InputStream stream);

    /**
     * Adds an input stream to the request.
     *
     * @param key    the key name for the new param.
     * @param stream the input stream to add.
     * @param name   the name of the stream.
     */
    void put(String key, InputStream stream, String name);

    /**
     * Adds an input stream to the request.
     *
     * @param key         the key name for the new param.
     * @param stream      the input stream to add.
     * @param name        the name of the stream.
     * @param contentType the content type of the file, eg. application/json
     */
    void put(String key, InputStream stream, String name, String contentType);

    /**
     * Adds an input stream to the request.
     *
     * @param key         the key name for the new param.
     * @param stream      the input stream to add.
     * @param name        the name of the stream.
     * @param contentType the content type of the file, eg. application/json
     * @param autoClose   close input stream automatically on successful upload
     */
    void put(String key, InputStream stream, String name, String contentType, boolean autoClose);

    /**
     * Adds param with non-string value (e.g. Map, List, Set).
     *
     * @param key   the key name for the new param.
     * @param value the non-string value object for the new param.
     */
    void put(String key, Object value);

    /**
     * Adds a int value to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value int for the new param.
     */
    void put(String key, int value);

    /**
     * Adds a long value to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value long for the new param.
     */
    void put(String key, long value);

    /**
     * Adds string value to param which can have more than one value.
     *
     * @param key   the key name for the param, either existing or new.
     * @param value the value string for the new param.
     */
    void add(String key, String value);

    /**
     * Removes a parameter from the request.
     * @param key the key name for the parameter to remove.
     */
    void remove(String key);


}
