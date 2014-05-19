package com.clov3rlabs.restclient.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.clov3rlabs.restclient.app.database.DatabaseOrm;
import com.clov3rlabs.restclient.app.entities.Articles;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import retrofit.RestAdapter;

/**
 * This class is created automatically when the app launches.
 * It is used to provide an application-level context for the SQLiteOpenHelper
 */
public class Aplication extends Application
{
    private static Aplication instance;

    private SharedPreferences preferences;
    private DatabaseOrm databaseHelper = null;
    private Dao<Articles.Article, Integer> article = null;
    private  RestAdapter restAdapter;

    public Aplication()
    {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        databaseHelper = new DatabaseOrm(this);
        instance=this;

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(getString(R.string.server))
                .build();
    }

    public RestAdapter getRestAdapter() {
         return this.restAdapter;
    }
    public Dao<Articles.Article, Integer> getArticleDao() throws SQLException {
        if (article == null) {
            article = databaseHelper.getDao(Articles.Article.class);
        }
        return article;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    public  void CleanArticles() throws SQLException {
        databaseHelper.CleanArticles();
    }

    public static Aplication getApplication() {
        return instance;
    }

}