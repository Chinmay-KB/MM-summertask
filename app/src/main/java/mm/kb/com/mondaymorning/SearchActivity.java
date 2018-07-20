package mm.kb.com.mondaymorning;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    public String query;
    private List<recyclerData> listItems;
    private ProgressBar animationView;

    private String SEARCH_PREFIX="http://mondaymorning.nitrkl.ac.in/api/search/";
    String URL;
    RecyclerView searchResults;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        Intent i=getIntent();
        animationView=(ProgressBar) findViewById(R.id.loading_search);
        listItems=new ArrayList<>();
        query=i.getStringExtra("query");
        Toast.makeText(MyContext.getContext(),query,Toast.LENGTH_SHORT).show();
        Toolbar searchToolbar=findViewById(R.id.search_toolbar);
        searchToolbar.setTitle(query.toUpperCase());
        searchToolbar.setTitleTextColor(getResources().getColor(R.color.cardview_light_background));
        URL=SEARCH_PREFIX+query;
        searchResults=(RecyclerView)findViewById(R.id.search_rv);
        searchResults.setHasFixedSize(true);
        animationView.setVisibility(View.VISIBLE);
        loadSearches();


    }
    private void loadSearches() {

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONArray("posts");
                    String authors[] = new String[arr.length()];
                    String categories[] = new String[arr.length()];
                    Toast.makeText(MyContext.getContext(), "working", Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < arr.length(); ++i) {
                        JSONObject o = arr.getJSONObject(i);

                        recyclerData data = new recyclerData(
                                o.getString("post_title"),
                                getAuthors(o),
                                o.getString("post_publish_date"),
                                o.getString("featured_image"),
                                o.getString("post_id"));
                        listItems.add(data);
                    }
                    RVAdapter allNewsAdapter=new RVAdapter(MyContext.getContext(),listItems);
                    searchResults.setLayoutManager(new LinearLayoutManager(MyContext.getContext()));
                    searchResults.setAdapter(allNewsAdapter);
                    animationView.setVisibility(View.GONE);
                    runLayoutAnimation(searchResults);
                } catch (JSONException e) {
                    Toast.makeText(MyContext.getContext(), "Not working", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                Toast.makeText(MyContext.getContext(), "Not khelaw", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue rq = Volley.newRequestQueue(MyContext.getContext());
        rq.add(request);

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
    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}


