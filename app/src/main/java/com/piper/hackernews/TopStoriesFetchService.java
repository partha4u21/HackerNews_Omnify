package com.piper.hackernews;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.piper.hackernews.models.Comments;
import com.piper.hackernews.models.TopStories;
import com.piper.hackernews.models.TopStoriesId;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by parthamurmu on 07/09/17.
 */

public class TopStoriesFetchService extends Service {

    Realm realm = null;
    private OkHttpClient client = new OkHttpClient();
    ServiceCallback callback;
    private final LocalBinder mLocalBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public TopStoriesFetchService getService() {
            return TopStoriesFetchService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mLocalBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        realm = Realm.getInstance(Realm.getDefaultConfiguration());
        insertAndUpdateDb();
        return START_STICKY;
    }

    private void addStories() {
        realm = Realm.getInstance(Realm.getDefaultConfiguration());
        realm.beginTransaction();
        RealmResults<TopStoriesId> results = realm.where(TopStoriesId.class).findAll();
        realm.commitTransaction();

        for (int i = 0; i < (results.size() > 50 ? 50 : results.size()); i++) {
            TopStories wisdom = realm.where(TopStories.class).equalTo("id", results.get(i).getStoriesId()).findFirst();
            if (wisdom == null) {
                insertTopStories(results.get(i).getStoriesId());
            }
        }
    }

    private void insertTopStories(String id) {
        CreateTopStoryRequest createTopStoryRequest = new CreateTopStoryRequest();
        createTopStoryRequest.execute("https://hacker-news.firebaseio.com/v0/item/" + id + ".json?print=pretty");
    }

    private class CreateTopStoryRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            Request request = new Request.Builder()
                    .url(urls[0])
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                if (response != null) {
                    JSONObject result = new JSONObject(response.body().string());
                    Realm realm = Realm.getInstance(Realm.getDefaultConfiguration());
                    String id = String.valueOf(result.getInt("id"));
                    realm.beginTransaction();
                    TopStories topStoriesId = realm.createObject(TopStories.class);
                    topStoriesId.setId(String.valueOf(result.getInt("id")));
                    topStoriesId.setTitle(result.getString("title"));
                    topStoriesId.setParent(result.optString("parent", ""));
                    topStoriesId.setKids(result.optJSONArray("kids") != null ? result.optJSONArray("kids").toString() : new JSONArray().toString());
                    topStoriesId.setScore(result.optString("score", ""));
                    topStoriesId.setUrl(result.optString("url", ""));
                    topStoriesId.setTime(result.getString("time"));
                    topStoriesId.setType(result.getString("type"));
                    topStoriesId.setUsername(result.getString("by"));
                    realm.commitTransaction();
                    System.out.println(urls[0] + "added");
                    realm.close();
                    System.out.println("Insert complete");
                    return response.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                System.out.println("Exception in inserting " + urls[0]);
                e.printStackTrace();
            }
            return "Download failed";
        }

        @Override
        protected void onPostExecute(String result) {
            if (callback != null) {
                callback.updateAdapter();
            }
        }
    }

    private class CreateTopStoryIdRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            Request request = new Request.Builder()
                    .url(urls[0])
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                String result = response.body().string();
                System.out.println(result);
                try {
                    JSONArray resultArray = new JSONArray(result);
                    for (int i = 0; i < resultArray.length(); i++) {
                        addDataToRealmTopStoriesIdList(resultArray.getString(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return response.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Download failed";
        }

        @Override
        protected void onPostExecute(String result) {
            addStories();
        }
    }

    private void insertAndUpdateDb() {
        CreateTopStoryIdRequest createTopStoryIdRequest = new CreateTopStoryIdRequest();
        createTopStoryIdRequest.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
    }

    private void addDataToRealmTopStoriesIdList(String id) {
        Realm realm = Realm.getInstance(Realm.getDefaultConfiguration());
        TopStoriesId topStoriesId = realm.where(TopStoriesId.class).equalTo("storiesId", id).findFirst();
        if (topStoriesId == null) {
            realm.beginTransaction();
            TopStoriesId topStoriesID = realm.createObject(TopStoriesId.class);
            topStoriesID.setStoriesId(id);
            realm.commitTransaction();
        }
        realm.close();
    }

    public void setCallbacks(ServiceCallback callbacks) {
        callback = callbacks;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
