package com.clov3rlabs.restclient.app.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by jhon on 5/14/14.
 */

public interface Articles {

    @GET("/sections/{sections}")
    List<Article> gelSectionNews(@Path("sections") String user);


    @GET("/articles")
    List<Article> gelAllNews();


    @GET("/articles/internacionles")
    List<Article> gelAlInternetzcaiones();

    @DatabaseTable(tableName = "articleTable")
    public class Article {

        @DatabaseField(id = true, generatedId = false)
        private String id;
        @DatabaseField
        private String kicker;
        @DatabaseField
        private String title;
        @DatabaseField
        private String summary;
        @DatabaseField
        private String section;



        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKicker() {
            return kicker;
        }

        public void setKicker(String kicker) {
            this.kicker = kicker;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }
    }
}
