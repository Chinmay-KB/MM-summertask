package mm.kb.com.mondaymorning;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
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

    RecyclerView departmentRV;
    RecyclerView campusRV;
    RecyclerView viewsRV;
    RecyclerView careerRV;
    RecyclerView alumniRV;
    RecyclerView ddcwcRV;

    ProgressBar a1,a2,a3,a4,a5,a6;
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
        departmentRV=(RecyclerView)rootView.findViewById(R.id.departments_rv);
         campusRV=(RecyclerView)rootView.findViewById(R.id.campus_rv);
         viewsRV=(RecyclerView)rootView.findViewById(R.id.views_rv);
         careerRV=(RecyclerView)rootView.findViewById(R.id.career_rv);
         alumniRV=(RecyclerView)rootView.findViewById(R.id.alumni_rv);
         ddcwcRV=(RecyclerView)rootView.findViewById(R.id.ddcwc_rv);

        departmentRV.setHasFixedSize(true);
        campusRV.setHasFixedSize(true);
        viewsRV.setHasFixedSize(true);
        careerRV.setHasFixedSize(true);
        alumniRV.setHasFixedSize(true);
        ddcwcRV.setHasFixedSize(true);

        a1=rootView.findViewById(R.id.loading_department);
        a2=rootView.findViewById(R.id.loading_campus);
        a3=rootView.findViewById(R.id.loading_views);
        a4=rootView.findViewById(R.id.loading_career);
        a5=rootView.findViewById(R.id.loading_alumni);
        a6=rootView.findViewById(R.id.loading_ddcwc);



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



        departmentFetch();
        campusFetch();
        viewsFetch();
        careerFetch();
        alumniFetch();
        ddcwcFetch();

        final View departmentsCard=rootView.findViewById(R.id.departments_card);
        final View campusCard=rootView.findViewById(R.id.campus_card);
        final View viewsCard=rootView.findViewById(R.id.views_card);
        final View careerCard=rootView.findViewById(R.id.career_card);
        final View alumniCard=rootView.findViewById(R.id.alumni_card);
        final View ddcwcCard=rootView.findViewById(R.id.ddcwc_card);
        departmentsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                TabbedDialogCategories dialogFragment = new TabbedDialogCategories();
                dialogFragment.departmentList=departmentList;
                dialogFragment.campusList=campusList;
                dialogFragment.viewsList=viewsList;
                dialogFragment.careerList=careerList;
                dialogFragment.alumniList=alumniList;
                dialogFragment.ddcwcList=ddcwcList;
                dialogFragment.tabIndex=0;
                dialogFragment.show(ft,"dialog");

            }

        });
        campusCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                TabbedDialogCategories dialogFragment = new TabbedDialogCategories();
                dialogFragment.departmentList=departmentList;
                dialogFragment.campusList=campusList;
                dialogFragment.viewsList=viewsList;
                dialogFragment.careerList=careerList;
                dialogFragment.alumniList=alumniList;
                dialogFragment.ddcwcList=ddcwcList;
                dialogFragment.tabIndex=1;
                dialogFragment.show(ft,"dialog");

            }

        });
        viewsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                TabbedDialogCategories dialogFragment = new TabbedDialogCategories();
                dialogFragment.departmentList=departmentList;
                dialogFragment.campusList=campusList;
                dialogFragment.viewsList=viewsList;
                dialogFragment.careerList=careerList;
                dialogFragment.alumniList=alumniList;
                dialogFragment.ddcwcList=ddcwcList;
                dialogFragment.tabIndex=2;
                dialogFragment.show(ft,"dialog");

            }

        });
        careerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                TabbedDialogCategories dialogFragment = new TabbedDialogCategories();
                dialogFragment.departmentList=departmentList;
                dialogFragment.campusList=campusList;
                dialogFragment.viewsList=viewsList;
                dialogFragment.careerList=careerList;
                dialogFragment.alumniList=alumniList;
                dialogFragment.ddcwcList=ddcwcList;
                dialogFragment.tabIndex=3;
                dialogFragment.show(ft,"dialog");

            }

        });
        alumniCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                TabbedDialogCategories dialogFragment = new TabbedDialogCategories();
                dialogFragment.departmentList=departmentList;
                dialogFragment.campusList=campusList;
                dialogFragment.viewsList=viewsList;
                dialogFragment.careerList=careerList;
                dialogFragment.alumniList=alumniList;
                dialogFragment.ddcwcList=ddcwcList;
                dialogFragment.tabIndex=4;
                dialogFragment.show(ft,"dialog");

            }

        });
        ddcwcCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                TabbedDialogCategories dialogFragment = new TabbedDialogCategories();
                dialogFragment.departmentList=departmentList;
                dialogFragment.campusList=campusList;
                dialogFragment.viewsList=viewsList;
                dialogFragment.careerList=careerList;
                dialogFragment.alumniList=alumniList;
                dialogFragment.ddcwcList=ddcwcList;
                dialogFragment.tabIndex=5;
                dialogFragment.show(ft,"dialog");

            }

        });


a1.setVisibility(View.VISIBLE);
        a2.setVisibility(View.VISIBLE);
        a3.setVisibility(View.VISIBLE);
        a4.setVisibility(View.VISIBLE);
        a5.setVisibility(View.VISIBLE);
        a6.setVisibility(View.VISIBLE);










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
                    RVAdapter departmentAdapter=new RVAdapter(getContext(),departmentList);
                    departmentRV.setAdapter(departmentAdapter);
                    a1.setVisibility(View.GONE);
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
                    RVAdapter campusAdapter=new RVAdapter(getContext(),campusList);
                    campusRV.setAdapter(campusAdapter);
                    a2.setVisibility(View.GONE);
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
                    RVAdapter viewsAdapter=new RVAdapter(getContext(),viewsList);
                    viewsRV.setAdapter(viewsAdapter);
                    a3.setVisibility(View.GONE);

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

                    RVAdapter careerAdapter=new RVAdapter(getContext(),careerList);
                    careerRV.setAdapter(careerAdapter);
                    a4.setVisibility(View.GONE);

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

                    RVAdapter alumniAdapter=new RVAdapter(getContext(),alumniList);
                    alumniRV.setAdapter(alumniAdapter);
                    a5.setVisibility(View.GONE);
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
                    RVAdapter ddcwcAdapter=new RVAdapter(getContext(),ddcwcList);
                    ddcwcRV.setAdapter(ddcwcAdapter);
                    a6.setVisibility(View.GONE);
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