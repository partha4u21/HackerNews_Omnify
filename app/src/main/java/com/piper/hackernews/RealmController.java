package com.piper.hackernews;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.piper.hackernews.models.TopStories;
import com.piper.hackernews.models.TopStoriesId;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by parthamurmu on 06/09/17.
 */

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    public void refresh() {

        realm.refresh();
    }

    public void clearAll() {

        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public RealmResults<TopStories> getTopStories() {

        return realm.where(TopStories.class).findAll();
    }

    public TopStories getTopStories(String id) {

        return realm.where(TopStories.class).equalTo("id", id).findFirst();
    }

    public boolean hasTopStories() {

        return !realm.where(TopStories.class).findAll().isEmpty();
    }

    public RealmResults<TopStories> queryTopStories() {

        return realm.where(TopStories.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }

    public RealmResults<TopStoriesId> getTopStoriesList() {

        return realm.where(TopStoriesId.class).findAll();
    }

    public TopStoriesId getTopStoriesListItem(String id) {

        return realm.where(TopStoriesId.class).equalTo("id", id).findFirst();
    }

    public boolean hasTopStoriesListItem() {
        return !realm.where(TopStoriesId.class).findAll().isEmpty();
    }

    //query example
    public RealmResults<TopStoriesId> queryTopStoriesList() {

        return realm.where(TopStoriesId.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
}
