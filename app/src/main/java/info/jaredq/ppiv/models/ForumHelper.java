package info.jaredq.ppiv.models;

import android.os.AsyncTask;
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
 * Created by 1218 on 24/02/2015.
 */
public class ForumHelper {

    public static List<Forum> readList(JsonReader reader) throws IOException {
        List ones = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            ones.add(readOne(reader));
        }
        reader.endArray();
        return ones;
    }

    public static Forum readOne(JsonReader reader) throws IOException {
        Forum one = new Forum();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("fid")) {
                one.setFid(reader.nextInt());
            } else if (name.equals("name")) {
                one.setName(reader.nextString());
            } else if (name.equals("description")) {
                one.setDescription(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return one;
    }

    /**
     * Get Forum List under one category
     * @param parentId the category id
     * @return
     */
    public static List<Forum> getForumList(int parentId) {
        List<Forum> list = null;
        InputStream is = null;
        JsonReader rdr = null;
        try {
            URL url = new URL("http://prepareinterview.890m.com/apis/v1/?r=api/forum-list&id=" + parentId);
            is = url.openStream();
            rdr = new JsonReader(new InputStreamReader(is, "UTF-8"));
            list = ForumHelper.readList(rdr);
        } catch (MalformedURLException e) {
            Log.d("FH.getForumList","MalformedURLException",e);
        } catch (UnsupportedEncodingException e) {
            Log.d("FH.getForumList","UnsupportedEncodingException",e);
        } catch (IOException e) {
            Log.d("FH.getForumList","IOException",e);
        } finally {
            if (rdr != null) {
                try {
                    rdr.close();
                } catch (IOException e) {
                    Log.d("FH.getForumList","IOException",e);
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.d("FH.getForumList","IOException",e);
                }
            }
        }
        return list;
    }
}
