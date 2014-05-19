package com.clov3rlabs.restclient.app.Utils;

import com.clov3rlabs.restclient.app.entities.Articles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticlesParser extends Parser<List<Articles.Article>> {

	@Override
	public List<Articles.Article> parse(String jsonString) {
		try {

            JSONArray json=new JSONArray(jsonString);
             List<Articles.Article> article = new ArrayList<Articles.Article>();
			for(int i=0;i<json.length();i++){
				JSONObject object=json.getJSONObject(i);
                Articles.Article item = new Articles.Article();
                item.setId(object.getString("id"));
                item.setTitle(object.getString("title"));
                article.add(item);
			}
			return article;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
