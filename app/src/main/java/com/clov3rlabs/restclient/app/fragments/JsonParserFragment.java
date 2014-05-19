package com.clov3rlabs.restclient.app.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.clov3rlabs.restclient.app.R;
import com.clov3rlabs.restclient.app.Utils.ArticlesParser;
import com.clov3rlabs.restclient.app.Utils.JSONRequest;
import com.clov3rlabs.restclient.app.adapters.NewsAdapter;
import com.clov3rlabs.restclient.app.database.DatabaseClassic;
import com.clov3rlabs.restclient.app.entities.Articles;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jhon on 4/24/14.
 */
public class JsonParserFragment extends Fragment {

    private ListView list;
    private List<Articles.Article> articles;
    private DatabaseClassic db;
    private Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_retrofit, container, false);

        db  = new DatabaseClassic(getActivity());
        db.open();
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

            cursor= db.CheckDatabase();
            if(cursor.getCount() != 0){

                articles = new ArrayList<Articles.Article>();
                if (cursor.moveToFirst()) {
                do {

                    Articles.Article p =  new Articles.Article();
                    if(cursor.getString(1)!=null)
                        p.setTitle(cursor.getString(1));

                    if(cursor.getString(2)!=null)
                        p.setSection(cursor.getString(2));

                    articles.add(p);
                } while(cursor.moveToNext());
            }

            } else {
                JSONRequest request = new JSONRequest();
                String cadena =  request.requestContent(getString(R.string.server));
                articles = new ArticlesParser().parse(cadena);

                for(Articles.Article p : articles) {

                    for(Imges img:  p.getImages) {

                    }
                    db.saveArticle(p.getId(), p.getTitle(),p.getSection());
                }
            }

            db.close();
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            NewsAdapter adapter = new NewsAdapter(getActivity(),
                    R.layout.news_list_item, articles);
            list.setAdapter(adapter);


            getActivity().setProgressBarIndeterminateVisibility(false);
        }
    }
}
