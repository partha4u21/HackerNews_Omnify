package com.piper.hackernews;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.piper.hackernews.models.TopStories;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity implements ServiceCallback {

    private OkHttpClient client = new OkHttpClient();
    private Realm myRealm;
    private ListView lvPersonNameList;
    private static ArrayList<String> topStoriesIdArrayList = new ArrayList<>();
    private static ArrayList<TopStories> topStoriesArrayList = new ArrayList<>();
    private StoriesAdapter storiesAdapter;
    private static MainActivity instance;
    private AlertDialog.Builder subDialog;
    private TopStoriesFetchService fetchService;
    boolean bound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myRealm = Realm.getInstance(Realm.getDefaultConfiguration());
        instance = this;

        //addFragment();
        lvPersonNameList = (ListView) findViewById(R.id.stories_list);

        storiesAdapter = new StoriesAdapter(MainActivity.this, topStoriesArrayList);
        lvPersonNameList.setAdapter(storiesAdapter);
        updateListView();

    }

    private void updateListView() {
        if (topStoriesArrayList.size() == 0) {
            Toast.makeText(this, "Please wait ... updating stories", Snackbar.LENGTH_SHORT).show();
        }
        Realm myRealm = Realm.getInstance(Realm.getDefaultConfiguration());
        RealmResults<TopStories> results = myRealm.where(TopStories.class).findAll();
        myRealm.beginTransaction();
        topStoriesArrayList.clear();
        for (int i = 0; i < results.size(); i++) {
            topStoriesArrayList.add(results.get(i));
        }
        myRealm.commitTransaction();
        storiesAdapter.notifyDataSetChanged();
    }

    public void startService() {
        startService(new Intent(getBaseContext(), TopStoriesFetchService.class));
        Intent intent = new Intent(this, TopStoriesFetchService.class);
        Messenger messenger = new Messenger(myHandler);
        intent.putExtra("MESSENGER", messenger);
        bindService(intent, mServerConn, Context.BIND_AUTO_CREATE);
        bound = true;
    }

    public Handler myHandler = new Handler() {
        public void handleMessage(Message message) {
            Bundle data = message.getData();
        }
    };

    // Method to stop the service
    public void stopService() {
        stopService(new Intent(getBaseContext(), TopStoriesFetchService.class));
        if (bound) {
            unbindService(mServerConn);
        }
        storiesAdapter.notifyDataSetChanged();
        bound = false;
    }

    protected ServiceConnection mServerConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            TopStoriesFetchService.LocalBinder localBinder = (TopStoriesFetchService.LocalBinder) binder;
            fetchService = localBinder.getService();
            bound = true;
            fetchService.setCallbacks(MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
            updateAdapter();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        startService();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        topStoriesIdArrayList.clear();
        topStoriesArrayList.clear();
        myRealm.close();
        if (bound) {
            bound = false;
            unbindService(mServerConn);
        }
        stopService();
    }

    @Override
    public void updateAdapter() {
        runOnUiThread(new Runnable() {
            public void run() {
                updateListView();
            }
        });

    }
}
