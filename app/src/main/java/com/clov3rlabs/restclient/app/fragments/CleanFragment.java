package com.clov3rlabs.restclient.app.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clov3rlabs.restclient.app.Aplication;
import com.clov3rlabs.restclient.app.R;
import com.clov3rlabs.restclient.app.database.DatabaseClassic;

import java.sql.SQLException;


/**
 * Created by jhon on 4/24/14.
 */
public class CleanFragment extends Fragment {


    private TextView text;
    private DatabaseClassic db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);

        db  = new DatabaseClassic(getActivity());
        db.open();
        new DownloadNewsTask().execute();
        text = (TextView) view.findViewById(R.id.text);

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

            try {
                Aplication.getApplication().CleanArticles();
                db.deleteAllArticles();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            text.setText("Done!");
            getActivity().setProgressBarIndeterminateVisibility(false);
        }
    }
}
