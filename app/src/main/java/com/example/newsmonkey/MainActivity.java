package com.example.newsmonkey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CustomAdapter.OnNewsClick, CategoriesAdapter.OnClickCategory {

    RecyclerView recyclerView, recyclerView_horizontal;
    CustomAdapter mcustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView_horizontal = findViewById(R.id.recyclerView_horizontal);
        recyclerView_horizontal.setLayoutManager(new LinearLayoutManager(this , recyclerView.HORIZONTAL , false));

        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(this);
        recyclerView_horizontal.setAdapter(categoriesAdapter);

        fetchData("general");

        mcustomAdapter = new CustomAdapter(this);

        recyclerView.setAdapter(mcustomAdapter);
    }

    // Fetches all the news from the api according to category
    void fetchData(String category)
    {
        String url ="https://newsapi.org/v2/top-headlines?country=in&category="+category+"&apiKey=ca1185d41e0e47bdaa2d940bd8f95c8f";

        MySingleton.getInstance(this).addToRequestQueue(new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            // Fetching the JSON Array response
                            JSONArray jsonArray = response.getJSONArray("articles");
                            ArrayList<News> newsArrayList = new ArrayList<>();

                            // Iterating over the entire JSON Array
                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                // Extracting the news from JSON Array
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                News news = new News(
                                        jsonObject.getString("title"),
                                        jsonObject.getString("urlToImage"),
                                        jsonObject.getString("url")
                                );

                                newsArrayList.add(news);
                            }
                            mcustomAdapter.updateNews(newsArrayList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        // Toast for any error
                        if(error.getMessage()!=null && error!=null) Toast.makeText(recyclerView.getContext() , error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            // Resolved the error but no idea how?
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0");
                return headers;
            }
        });
    }

    // Click Listener for opening the news in chrome tab
    // Used CustomTabsIntent
    @Override
    public void OnClick(News news) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(news.url));
    }

    // Click Listener for fetching the data according to category
    @Override
    public void clickedCategory(String category) {
        fetchData(category);
    }
}
