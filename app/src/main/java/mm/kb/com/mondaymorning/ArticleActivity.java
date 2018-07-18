package mm.kb.com.mondaymorning;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ArticleActivity extends AppCompatActivity {

    private String articleURLPrefix = "http://mondaymorning.nitrkl.ac.in/api/post/get/";
    private String articleHeaderPrefix="http://mondaymorning.nitrkl.ac.in/uploads/post/big/";
    String  article_name;
    private ArrayList<ArticleStore> articleList = new ArrayList<>();
    RecyclerView rv;
    ViewGroup toolbar;
    TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_layout);
        toolbar=findViewById(R.id.toolbar);
        tv=(TextView)toolbar.findViewById(R.id.textView);
        Intent intent=getIntent();
        String s=intent.getStringExtra("post_id");
        articleURLPrefix += s;
        loadContent();
        rv = findViewById(R.id.article_recycler);

    }
    private void loadContent() {
        StringRequest request = new StringRequest(Request.Method.GET, articleURLPrefix, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject jsonObject = object.getJSONObject("post");
                    ArticleStore headerImageModel=new ArticleStore(ArticleStore.IMAGE,articleHeaderPrefix+jsonObject.getString("featured_image"));
                    articleList.add(headerImageModel);
                    ArticleStore TitleModel=new ArticleStore(ArticleStore.TITLE,jsonObject.getString("post_title" ));
                    articleList.add(TitleModel);
                    ArticleStore categoryModel=new ArticleStore(ArticleStore.CATEGORY,getAuthors(jsonObject));//jsonObject.getJSO("categories").getString("post_category_name"));
                    articleList.add(categoryModel);
                    JSONArray array = jsonObject.getJSONArray("post_content");
                    for(int i=0; i<array.length(); ++i){
                        JSONObject o = array.getJSONObject(i);
                        if (o.getInt("type") != 1) {
                            ArticleStore model = new ArticleStore(ArticleStore.TEXT,o.getString("content"));
                            articleList.add(model);
                        }
                        else{
                            ArticleStore model = new ArticleStore(ArticleStore.IMAGE, o.getString("content"));
                            articleList.add(model);
                        }
                    }
                    tv.setText(jsonObject.getString("post_title" ));
                    ArticleAdapter adapter = new ArticleAdapter(articleList,ArticleActivity.this,2);
                    rv.setLayoutManager(new LinearLayoutManager(MyContext.getContext(), LinearLayoutManager.VERTICAL,false));
                    rv.setItemAnimator(new DefaultItemAnimator());
                    rv.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                error.printStackTrace();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    public void onBackClicked(View view)
    {
        startActivity(new Intent(this,MainActivity.class));
    }
    public String getAuthors(JSONObject jo) {

        String str = "";
        try {
            JSONArray arr_auth = jo.getJSONArray("authors");
            for (int i = 0; i < arr_auth.length(); ++i) {
                if (i == arr_auth.length() - 1)
                    str += arr_auth.get(i);
                else
                    str += arr_auth.get(i) + ",";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }


}
