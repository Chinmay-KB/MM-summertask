package mm.kb.com.mondaymorning;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;;import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ThisWeekFragment extends Fragment {

    private ProgressDialog pDialog;
    private static String url = "http://mondaymorning.nitrkl.ac.in/api/post/get/featured";
    ArrayList<HashMap<String, String>> titleList;
    private static final String DATA_URL_FEATURED = "http://mondaymorning.nitrkl.ac.in/api/post/get/featured";

    private static final String DATA_URL_ALLNEWS = "http://mondaymorning.nitrkl.ac.in/api/post/get/thisweek";
    private List<recyclerData> listItems;
    private List<recyclerData> recyclerDataList;

    public ThisWeekFragment() {
        // Required empty public constructor
    }

    List<String> namesList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.this_week_layout, container, false);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        String list[] = new String[25];
        for (int i = 0; i < 19; i++)
            namesList.add("Article Title Here");
        listItems = new ArrayList<>();
        recyclerDataList = new ArrayList<>();
        loadRecyclerViewData();
        RecyclerView allNews = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view_all_news);
        allNews.setHasFixedSize(true);
        RVAdapter allNewsAdapter=new RVAdapter(getContext(), recyclerDataList);
        allNews.setLayoutManager(new LinearLayoutManager(getContext()));
        allNews.setAdapter(allNewsAdapter);
        loadAllNews();

        RVAdapter adapter = new RVAdapter(getContext(), listItems);


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        rv.setAdapter(adapter);
        runLayoutAnimation(rv);
        return rootView;

    }

    private void loadRecyclerViewData() {

        StringRequest request = new StringRequest(Request.Method.GET, DATA_URL_FEATURED, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONObject("top4").getJSONArray("posts");
                    String authors[] = new String[arr.length()];
                    String categories[] = new String[arr.length()];
                    for (int i = 0; i < arr.length(); ++i) {
                        JSONObject o = arr.getJSONObject(i);

                        recyclerData data = new recyclerData(
                                o.getString("post_title"),
                                //o.getJSONArray("authors").toString(),
                                getAuthors(o),
                                o.getString("post_publish_date"),
                                o.getString("featured_image"),
                                o.getString("post_id"));
                        listItems.add(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });


        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(request);
    }

    private void loadAllNews() {

        StringRequest request = new StringRequest(Request.Method.GET, DATA_URL_ALLNEWS, new Response.Listener<String>() {
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
                                //o.getJSONArray("authors").toString(),
                                getAuthors(o),
                                o.getString("post_publish_date"),
                                o.getString("featured_image"),
                                o.getString("post_id"));
                        recyclerDataList.add(data);
                    }
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

        RequestQueue rq = Volley.newRequestQueue(getContext());
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