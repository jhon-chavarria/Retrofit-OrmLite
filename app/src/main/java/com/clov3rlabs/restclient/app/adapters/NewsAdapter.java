package com.clov3rlabs.restclient.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.clov3rlabs.restclient.app.R;
import com.clov3rlabs.restclient.app.entities.Articles;

import java.util.List;

/**
 * Created by jhon on 5/9/14.
 */
public class NewsAdapter extends ArrayAdapter<String> {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Articles.Article> news;

    public NewsAdapter(Activity a, int textViewResourceId, List<Articles.Article> items) {
        super(a, textViewResourceId);
        this.activity =a;
        this.news = items;
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return news.size();
    }

    public long getItemId(int arg0) {
        return arg0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        NewsHolder holder;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            holder = new NewsHolder();
            convertView = inflater.inflate(R.layout.news_list_item, parent, false);
            holder.title = (TextView)convertView.findViewById(R.id.title);

            convertView.setTag(holder);

        } else holder = (NewsHolder)convertView.getTag();

        Articles.Article article = news.get(position);

        holder.title.setText(article.getTitle());
       // holder.date.setText("Hoje " + Utils.getDateAsFormat(article.getFirstPublishedDate()));

        //String plain = Html.fromHtml(article.getText()).toString();

        //holder.text.setText(plain);


        return convertView;
    }

    private static class NewsHolder {
        public TextView title;

    }
}