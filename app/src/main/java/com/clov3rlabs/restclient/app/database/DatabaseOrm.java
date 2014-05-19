package com.clov3rlabs.restclient.app.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.clov3rlabs.restclient.app.entities.Articles;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/** SQLite Adapter
 *
 *
 * Database Structure
 *
 *
 * */
public class DatabaseOrm extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "test1";
    private static final int DATABASE_VERSION =2;
    private  SQLiteDatabase  db;


    public DatabaseOrm(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            this.db = db;
            Log.i(DatabaseOrm.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Articles.Article.class);
        } catch (SQLException e) {
            Log.e(DatabaseOrm.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseOrm.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Articles.Article.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseOrm.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        super.close();
    }
    public void CleanArticles() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), Articles.Article.class);
    }

}