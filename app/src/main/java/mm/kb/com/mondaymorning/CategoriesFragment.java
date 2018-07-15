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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

;

public class CategoriesFragment extends Fragment {

    private ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> titleList;
    private static final String DATA_URL_DEPARTMENT = "http://mondaymorning.nitrkl.ac.in/api/post/get/tab/departments";
    private static final String DATA_URL_CAMPUS = "http://mondaymorning.nitrkl.ac.in/api/post/get/tab/campus";
    private static final String DATA_URL_VIEWS = "http://mondaymorning.nitrkl.ac.in/api/post/get/tab/views";
    private static final String DATA_URL_CAREER = "http://mondaymorning.nitrkl.ac.in/api/post/get/tab/career";
    private static final String DATA_URL_ALUMNI = "http://mondaymorning.nitrkl.ac.in/api/post/get/tab/alumni";

    private static final String DATA_URL_DDCWWC = "http://mondaymorning.nitrkl.ac.in/api/post/get/tab/dd-cwc";

    private List<recyclerData> departmentList;
    private List<recyclerData> campusList;
    private List<recyclerData> viewsList;
    private List<recyclerData> careerList;
    private List<recyclerData> alumniList;
    private List<recyclerData> ddcwcList;

    public CategoriesFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.categories_layout, container, false);
        RecyclerView departmentRV=(RecyclerView)rootView.findViewById(R.id.departments_rv);
        RecyclerView campusRV=(RecyclerView)rootView.findViewById(R.id.campus_rv);
        RecyclerView viewsRV=(RecyclerView)rootView.findViewById(R.id.views_rv);
        RecyclerView careerRV=(RecyclerView)rootView.findViewById(R.id.career_rv);
        RecyclerView alumniRV=(RecyclerView)rootView.findViewById(R.id.alumni_rv);
        RecyclerView ddcwcRV=(RecyclerView)rootView.findViewById(R.id.ddcwc_rv);

        departmentRV.setHasFixedSize(true);
        campusRV.setHasFixedSize(true);
        viewsRV.setHasFixedSize(true);
        careerRV.setHasFixedSize(true);
        alumniRV.setHasFixedSize(true);
        ddcwcRV.setHasFixedSize(true);



        departmentRV.setLayoutManager(new LinearLayoutManager(getContext()));
        campusRV.setLayoutManager(new LinearLayoutManager(getContext()));
        viewsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        careerRV.setLayoutManager(new LinearLayoutManager(getContext()));
        alumniRV.setLayoutManager(new LinearLayoutManager(getContext()));
        ddcwcRV.setLayoutManager(new LinearLayoutManager(getContext()));



        departmentList=new ArrayList<>();
        campusList=new ArrayList<>();
        viewsList=new ArrayList<>();
        careerList=new ArrayList<>();
        alumniList=new ArrayList<>();
        ddcwcList=new ArrayList<>();

        RVAdapter departmentAdapter=new RVAdapter(getContext(),departmentList);
        RVAdapter campusAdapter=new RVAdapter(getContext(),campusList);
        RVAdapter viewsAdapter=new RVAdapter(getContext(),viewsList);
        RVAdapter careerAdapter=new RVAdapter(getContext(),careerList);
        RVAdapter alumniAdapter=new RVAdapter(getContext(),alumniList);
        RVAdapter ddcwcAdapter=new RVAdapter(getContext(),ddcwcList);

        departmentFetch();
        campusFetch();
        viewsFetch();
        careerFetch();
        alumniFetch();
        ddcwcFetch();

        departmentRV.setAdapter(departmentAdapter);
        campusRV.setAdapter(campusAdapter);
        viewsRV.setAdapter(viewsAdapter);
        careerRV.setAdapter(careerAdapter);
        alumniRV.setAdapter(alumniAdapter);
        ddcwcRV.setAdapter(ddcwcAdapter);

        return rootView;

    }
    private void departmentFetch()
    {
        StringRequest request = new StringRequest(Request.Method.GET, DATA_URL_DEPARTMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONArray("posts");
                    String authors[] = new String[arr.length()];
                    String categories[] = new String[arr.length()];
                    //Toast.makeText(MyContext.getContext(), "working", //Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < arr.length(); ++i) {
                        JSONObject o = arr.getJSONObject(i);

                        recyclerData data = new recyclerData(
                                o.getString("post_title"),
                                //o.getJSONArray("authors").toString(),
                                getAuthors(o),
                                o.getString("post_publish_date"),
                                o.getString("featured_image"),
                                o.getString("post_id"));
                        departmentList.add(data);
                    }
                } catch (JSONException e) {
                    //Toast.makeText(MyContext.getContext(), "Not working", //Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                //Toast.makeText(MyContext.getContext(), "Not khelaw", //Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(request);
    }
    private void campusFetch()
    {
        StringRequest request = new StringRequest(Request.Method.GET, DATA_URL_CAMPUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONArray("posts");
                    String authors[] = new String[arr.length()];
                    String categories[] = new String[arr.length()];
                    //Toast.makeText(MyContext.getContext(), "working", //Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < arr.length(); ++i) {
                        JSONObject o = arr.getJSONObject(i);

                        recyclerData data = new recyclerData(
                                o.getString("post_title"),
                                //o.getJSONArray("authors").toString(),
                                getAuthors(o),
                                o.getString("post_publish_date"),
                                o.getString("featured_image"),
                                o.getString("post_id"));
                        campusList.add(data);
                    }
                } catch (JSONException e) {
                    //Toast.makeText(MyContext.getContext(), "Not working", //Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                //Toast.makeText(MyContext.getContext(), "Not khelaw", //Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(request);
    }
    private void viewsFetch()
    {
        StringRequest request = new StringRequest(Request.Method.GET, DATA_URL_VIEWS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONArray("posts");
                    String authors[] = new String[arr.length()];
                    String categories[] = new String[arr.length()];
                    //Toast.makeText(MyContext.getContext(), "working", //Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < arr.length(); ++i) {
                        JSONObject o = arr.getJSONObject(i);

                        recyclerData data = new recyclerData(
                                o.getString("post_title"),
                                //o.getJSONArray("authors").toString(),
                                getAuthors(o),
                                o.getString("post_publish_date"),
                                o.getString("featured_image"),
                                o.getString("post_id"));
                        viewsList.add(data);
                    }
                } catch (JSONException e) {
                    //Toast.makeText(MyContext.getContext(), "Not working", //Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                //Toast.makeText(MyContext.getContext(), "Not khelaw", //Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(request);
    }
    private void careerFetch()
    {
        StringRequest request = new StringRequest(Request.Method.GET, DATA_URL_CAREER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONArray("posts");
                    String authors[] = new String[arr.length()];
                    String categories[] = new String[arr.length()];
                    //Toast.makeText(MyContext.getContext(), "working", //Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < arr.length(); ++i) {
                        JSONObject o = arr.getJSONObject(i);

                        recyclerData data = new recyclerData(
                                o.getString("post_title"),
                                //o.getJSONArray("authors").toString(),
                                getAuthors(o),
                                o.getString("post_publish_date"),
                                o.getString("featured_image"),
                                o.getString("post_id"));
                        careerList.add(data);
                    }
                } catch (JSONException e) {
                    //Toast.makeText(MyContext.getContext(), "Not working", //Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                //Toast.makeText(MyContext.getContext(), "Not khelaw", //Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(request);
    }
    private void alumniFetch()
    {
        StringRequest request = new StringRequest(Request.Method.GET, DATA_URL_ALUMNI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONArray("posts");
                    String authors[] = new String[arr.length()];
                    String categories[] = new String[arr.length()];
                    //Toast.makeText(MyContext.getContext(), "working", //Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < arr.length(); ++i) {
                        JSONObject o = arr.getJSONObject(i);

                        recyclerData data = new recyclerData(
                                o.getString("post_title"),
                                //o.getJSONArray("authors").toString(),
                                getAuthors(o),
                                o.getString("post_publish_date"),
                                o.getString("featured_image"),
                                o.getString("post_id"));
                        alumniList.add(data);
                    }
                } catch (JSONException e) {
                    //Toast.makeText(MyContext.getContext(), "Not working", //Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                //Toast.makeText(MyContext.getContext(), "Not khelaw", //Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(request);
    }
    private void ddcwcFetch()
    {
        StringRequest request = new StringRequest(Request.Method.GET, DATA_URL_ALUMNI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONArray("posts");
                    String authors[] = new String[arr.length()];
                    String categories[] = new String[arr.length()];
                    //Toast.makeText(MyContext.getContext(), "working", //Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < arr.length(); ++i) {
                        JSONObject o = arr.getJSONObject(i);

                        recyclerData data = new recyclerData(
                                o.getString("post_title"),
                                //o.getJSONArray("authors").toString(),
                                getAuthors(o),
                                o.getString("post_publish_date"),
                                o.getString("featured_image"),
                                o.getString("post_id"));
                        ddcwcList.add(data);
                    }
                } catch (JSONException e) {
                    //Toast.makeText(MyContext.getContext(), "Not working", //Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                //Toast.makeText(MyContext.getContext(), "Not khelaw", //Toast.LENGTH_SHORT).show();

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