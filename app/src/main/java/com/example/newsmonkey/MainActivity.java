package com.example.newsmonkey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CustomAdapter.OnNewsClick, CategoriesAdapter.OnClickCategory {

    RecyclerView recyclerView, recyclerView_horizontal;
    //private String data[];
    CustomAdapter mad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Check" , "Inside oncreate");
        //ArrayList<News> datalist = fetchData();
        //Log.d("Oncreate" , "data:" + datalist);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView_horizontal = findViewById(R.id.recyclerView_horizontal);
        recyclerView_horizontal.setLayoutManager(new LinearLayoutManager(this , recyclerView.HORIZONTAL , false));

        CategoriesAdapter cad = new CategoriesAdapter(this);
        recyclerView_horizontal.setAdapter(cad);

        fetchData("general");
        //CustomAdapter ad = new CustomAdapter(this);
        mad = new CustomAdapter(this);

        recyclerView.setAdapter(mad);
        //ad.updateNews(datalist);


        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    // was ArrayList<News>
    void fetchData(String category)
    {
        String url ="https://newsapi.org/v2/top-headlines?country=in&category="+category+"&apiKey=ca1185d41e0e47bdaa2d940bd8f95c8f";

        //ArrayList<News> newsArrayList = new ArrayList<>();
        MySingleton.getInstance(this).addToRequestQueue(new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("articles");
                            ArrayList<News> newsArrayList = new ArrayList<>();

                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                News news = new News(
                                        jsonObject.getString("title"),
                                        jsonObject.getString("urlToImage"),
                                        jsonObject.getString("url")
                                );

                                Log.d("Check" , "Inside fetchdata");
                                newsArrayList.add(news);
                            }
                            mad.updateNews(newsArrayList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        if(error.getMessage()!=null && error!=null) Toast.makeText(recyclerView.getContext() , error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0");
                return headers;
            }
        });


        //return newsArrayList;
    }

    @Override
    public void OnClick(News news) {

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(news.url));
    }

    @Override
    public void clickedCategory(String category) {
        fetchData(category);
    }
}



//        import android.net.Uri;
//        import android.opengl.Visibility;
//        import android.os.Bundle;
//        import android.widget.Toast;
//        import androidx.appcompat.app.AppCompatActivity;
//        import androidx.browser.customtabs.CustomTabsIntent;
//        import androidx.recyclerview.widget.LinearLayoutManager;
//        import androidx.recyclerview.widget.RecyclerView;
//        import com.android.volley.AuthFailureError;
//        import com.android.volley.Request;
//        import com.android.volley.Response;
//        import com.android.volley.VolleyError;
//        import com.android.volley.toolbox.JsonObjectRequest;
//        import java.util.ArrayList;
//        import java.util.HashMap;
//        import java.util.Map;
//        import org.json.JSONArray;
//        import org.json.JSONException;
//        import org.json.JSONObject;
//
//public class MainActivity extends AppCompatActivity implements CustomAdapter.OnNewsClick {
//    CustomAdapter adapter;
//    RecyclerView recyclerView;
//
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView((int) R.layout.activity_main);
//
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.peekAvailableContext()));
//        this.adapter = new CustomAdapter(this);
//        fetch();
//        this.recyclerView.setAdapter(this.adapter);
//    }
//
//    private void fetch() {
//        MySingleton.getInstance(this.peekAvailableContext()).addToRequestQueue(new JsonObjectRequest(Request.Method.GET,
//                "https://newsapi.org/v2/top-headlines?country=in&apiKey=13f0e594241746f2b5209873b8b5d39d", null, new Response.Listener<JSONObject>() {
//            public void onResponse(JSONObject response) {
//                JSONArray newsJsonArray = null;
//                try {
//                    newsJsonArray = response.getJSONArray("articles");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                ArrayList<News> newsInfoArray = new ArrayList<>();
//                for (int i = 0; i < newsJsonArray.length(); i++) {
//                    JSONObject newsJsonObject = null;
//                    News ob = null;
//                    try {
//                        newsJsonObject = newsJsonArray.getJSONObject(i);
//                    } catch (JSONException e2) {
//                        e2.printStackTrace();
//                    }
//                    try {
//                        ob = new News(newsJsonObject.getString("title"), newsJsonObject.getString("author"),  newsJsonObject.getString("urlToImage"), newsJsonObject.getString("url"));
//                    } catch (JSONException e3) {
//                        e3.printStackTrace();
//                    }
//                    newsInfoArray.add(ob);
//                }
//                MainActivity.this.adapter.updateNews(newsInfoArray);
//            }
//        }, new Response.ErrorListener() {
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this.recyclerView.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }){
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<>();
//                headers.put("User-Agent", "Mozilla/5.0");
//                return headers;
//            }
//        } );
//    }
//
//
//    @Override
//    public void OnClick(News news) {
//        new CustomTabsIntent.Builder().build().launchUrl(this.peekAvailableContext(), Uri.parse(news.url));
//    }
//}