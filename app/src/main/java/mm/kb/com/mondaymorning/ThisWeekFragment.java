package mm.kb.com.mondaymorning;

import android.animation.ValueAnimator;
import android.app.Dialog;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;;import com.airbnb.lottie.LottieAnimationView;
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
    RecyclerView rv;
    RecyclerView allNews;
    FragmentManager fm;
    private LottieAnimationView anim1, anim2;

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
        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);
        allNews = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view_all_news);
        allNews.setHasFixedSize(true);
        anim2=rootView.findViewById(R.id.loading_allNews);
        anim1=rootView.findViewById(R.id.loading_featured);
        anim1.setVisibility(View.VISIBLE);
        anim2.setVisibility(View.VISIBLE);

        final View featuredCard=rootView.findViewById(R.id.featured_card);
        View featuredCardL=featuredCard.findViewById(R.id.featured_cardL);
        TextView showAllFeatured=(TextView)featuredCardL.findViewById(R.id.showAllTextView2);
        featuredCard.findViewById(R.id.showAllTextView2);
        featuredCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                TabbedDialog dialogFragment = new TabbedDialog();
                dialogFragment.listItems=listItems;
                dialogFragment.recyclerDataList=recyclerDataList;
                dialogFragment.tabIndex=0;
                dialogFragment.show(ft,"dialog");

            }

        });
        final View allNewsCard=rootView.findViewById(R.id.all_news_card);
        allNewsCard.findViewById(R.id.textView_allNews);
        allNewsCard.setOnClickListener((new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            // Create and show the dialog.
            TabbedDialog dialogFragment = new TabbedDialog();
            dialogFragment.listItems=listItems;
            dialogFragment.recyclerDataList=recyclerDataList;
            dialogFragment.tabIndex=1;
            dialogFragment.show(ft,"dialog");

        }

        }));
        String list[] = new String[25];
        for (int i = 0; i < 19; i++)
            namesList.add("Article Title Here");
        listItems = new ArrayList<>();
        recyclerDataList = new ArrayList<>();

        startCheckAnimation(anim1);
        startCheckAnimation(anim2);
        loadRecyclerViewData();
        loadAllNews();

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
                    RVAdapter adapter = new RVAdapter(MyContext.getContext(), listItems);


                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    rv.setLayoutManager(new LinearLayoutManager(MyContext.getContext()));

                    rv.setAdapter(adapter);
                    runLayoutAnimation(rv);
                    anim1.setVisibility(View.GONE);
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


        RequestQueue rq = Volley.newRequestQueue(MyContext.getContext());
        rq.add(request);
    }
    public void FeaturedShowAll(View view)
    {
        final Dialog fbDialogue = new Dialog(MyContext.getContext(), android.R.style.Theme_Black_NoTitleBar);
        fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        fbDialogue.setContentView(R.layout.this_week_show_all);
        fbDialogue.setCancelable(true);
        fbDialogue.show();
    }
        public void onClick(View view)
        {
            switch (view.getId())
            {
                //handle multiple view click events
                case R.id.showAllTextView2:
            }
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
                    allNews.setHasFixedSize(true);
                    RVAdapter allNewsAdapter=new RVAdapter(MyContext.getContext(), recyclerDataList);
                    allNews.setLayoutManager(new LinearLayoutManager(MyContext.getContext()));
                    allNews.setAdapter(allNewsAdapter);
                    anim2.setVisibility(View.GONE);
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
    private void startCheckAnimation(final LottieAnimationView lottieAnimationView) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lottieAnimationView.setProgress((Float) valueAnimator.getAnimatedValue());
            }
        });

        animator.start();
    }

}