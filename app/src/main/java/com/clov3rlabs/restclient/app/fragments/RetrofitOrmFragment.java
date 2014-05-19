package com.clov3rlabs.restclient.app.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.clov3rlabs.restclient.app.Aplication;
import com.clov3rlabs.restclient.app.R;
import com.clov3rlabs.restclient.app.adapters.NewsAdapter;
import com.clov3rlabs.restclient.app.entities.Articles;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;


/**
 * Created by jhon on 4/24/14.
 */
public class RetrofitOrmFragment extends Fragment {


    private Dao<Articles.Article, Integer> articleDao = null;
    //private static final String API_URL = "http://api.elnuevodiario.com.ni/";
    private List<Articles.Article> news;
    private ListView list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_retrofit, container, false);
        new DownloadNewsTask().execute();
        list = (ListView) view.findViewById(R.id.news);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    class DownloadNewsTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            getActivity().setProgressBarIndeterminateVisibility(true);
        }
        @Override
        protected Void doInBackground(Void... arg0) {

            Articles repre = Aplication.getApplication().getRestAdapter().create(Articles.class);
            try {
                articleDao = Aplication.getApplication().getArticleDao();
                if(articleDao.isTableExists() && articleDao.queryForAll().size()>0) {
                    news = articleDao.queryForAll();
                } else {
                    news = repre.gelAllNews();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            NewsAdapter adapter = new NewsAdapter(getActivity(),
                    R.layout.news_list_item, news);
            list.setAdapter(adapter);


            try {
                articleDao = Aplication.getApplication().getArticleDao();

                for(Articles.Article p :  news) {
                    articleDao.queryForAll();
                }
                //articleDao.c(news);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            getActivity().setProgressBarIndeterminateVisibility(false);
        }
    }
}
