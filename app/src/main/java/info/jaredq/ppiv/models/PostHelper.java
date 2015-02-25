package info.jaredq.ppiv.models;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1218 on 25/02/2015.
 */
public class PostHelper {

    public static List<Post> readList(JsonReader reader) throws IOException {
        List ones = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            ones.add(readOne(reader));
        }
        reader.endArray();
        return ones;
    }

    public static Post readOne(JsonReader reader) throws IOException {
        Post one = new Post();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("pid")) {
                one.setPid(reader.nextInt());
            } else
            if (name.equals("tid")) {
                one.setTid(reader.nextInt());
            } else
            if (name.equals("fid")) {
                one.setFid(reader.nextInt());
            } else if (name.equals("subject")) {
                one.setSubject(reader.nextString());
            } else if (name.equals("message")) {
                one.setMessage(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return one;
    }

    /**
     * Get Post List under one forum
     * @param parentId the forum id
     * @return
     */
    public static List<Post> getPostList(int parentId) {
        List<Post> list = null;
        InputStream is = null;
        JsonReader rdr = null;
        try {
            URL url = new URL("http://prepareinterview.890m.com/apis/v1/?r=api/post-list&id=" + parentId);
            is = url.openStream();
            rdr = new JsonReader(new InputStreamReader(is, "UTF-8"));
            list = readList(rdr);
        } catch (MalformedURLException e) {
            Log.d("TH.getPostList", "MalformedURLException", e);
        } catch (UnsupportedEncodingException e) {
            Log.d("TH.getPostList","UnsupportedEncodingException",e);
        } catch (IOException e) {
            Log.d("TH.getPostList","IOException",e);
        } finally {
            if (rdr != null) {
                try {
                    rdr.close();
                } catch (IOException e) {
                    Log.d("TH.getPostList","IOException",e);
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.d("TH.getPostList","IOException",e);
                }
            }
        }
        return list;
    }
}
