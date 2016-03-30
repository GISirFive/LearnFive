package com.telchina.init.http;

import com.loopj.android.http.AsyncHttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.telchina.pub.http.IRequestParams;

/**
 * A collection of string request parameters or files to send along with requests made from an
 * {@link AsyncHttpClient} instance. <p>&nbsp;</p> For example: <p>&nbsp;</p>
 * <pre>
 * RequestParams params = new RequestParams();
 * params.put("username", "james");
 * params.put("password", "123456");
 * params.put("email", "my&#064;email.com");
 * params.put("profile_picture", new File("pic.jpg")); // Upload a File
 * params.put("profile_picture2", someInputStream); // Upload an InputStream
 * params.put("profile_picture3", new ByteArrayInputStream(someBytes)); // Upload some bytes
 *
 * Map&lt;String, String&gt; map = new HashMap&lt;String, String&gt;();
 * map.put("first_name", "James");
 * map.put("last_name", "Smith");
 * params.put("user", map); // url params: "user[first_name]=James&amp;user[last_name]=Smith"
 *
 * Set&lt;String&gt; set = new HashSet&lt;String&gt;(); // unordered collection
 * set.add("music");
 * set.add("art");
 * params.put("like", set); // url params: "like=music&amp;like=art"
 *
 * List&lt;String&gt; list = new ArrayList&lt;String&gt;(); // Ordered collection
 * list.add("Java");
 * list.add("C");
 * params.put("languages", list); // url params: "languages[0]=Java&amp;languages[1]=C"
 *
 * String[] colors = { "blue", "yellow" }; // Ordered collection
 * params.put("colors", colors); // url params: "colors[0]=blue&amp;colors[1]=yellow"
 *
 * File[] files = { new File("pic.jpg"), new File("pic1.jpg") }; // Ordered collection
 * params.put("files", files); // url params: "files[]=pic.jpg&amp;files[]=pic1.jpg"
 *
 * List&lt;Map&lt;String, String&gt;&gt; listOfMaps = new ArrayList&lt;Map&lt;String,
 * String&gt;&gt;();
 * Map&lt;String, String&gt; user1 = new HashMap&lt;String, String&gt;();
 * user1.put("age", "30");
 * user1.put("gender", "male");
 * Map&lt;String, String&gt; user2 = new HashMap&lt;String, String&gt;();
 * user2.put("age", "25");
 * user2.put("gender", "female");
 * listOfMaps.add(user1);
 * listOfMaps.add(user2);
 * params.put("users", listOfMaps); // url params: "users[][age]=30&amp;users[][gender]=male&amp;users[][age]=25&amp;users[][gender]=female"
 *
 * AsyncHttpClient client = new AsyncHttpClient();
 * client.post("https://myendpoint.com", params, responseHandler);
 * </pre>
 *
 * @author GISirFive
 * @since 2015-12-13 上午12:24:40
 */
public class RequestParams implements IRequestParams {

    private com.loopj.android.http.RequestParams mParams;

    public RequestParams() {
        mParams = new com.loopj.android.http.RequestParams();
    }

    public RequestParams(String key, String value) {
        super();
        put(key, value);
    }

    @Override
    public Object getParams() {
        return mParams;
    }

    @Override
    public void put(String key, String value) {
        mParams.put(key, value);
    }

    @Override
    public void put(String key, File file) throws FileNotFoundException {
        mParams.put(key, file);
    }

    @Override
    public void put(String key, InputStream stream) {
        mParams.put(key, stream);
    }

    @Override
    public void put(String key, InputStream stream, String name) {
        mParams.put(key, stream, name);
    }

    @Override
    public void put(String key, InputStream stream, String name,
                    String contentType) {
        mParams.put(key, stream, name, contentType);
    }

    @Override
    public void put(String key, InputStream stream, String name,
                    String contentType, boolean autoClose) {
        mParams.put(key, stream, name, contentType, autoClose);
    }

    @Override
    public void put(String key, Object value) {
        mParams.put(key, value);
    }

    @Override
    public void put(String key, int value) {
        mParams.put(key, value);
    }

    @Override
    public void put(String key, long value) {
        mParams.put(key, value);
    }

    @Override
    public void add(String key, String value) {
        mParams.add(key, value);
    }

    @Override
    public void remove(String key) {
        mParams.remove(key);
    }


}
