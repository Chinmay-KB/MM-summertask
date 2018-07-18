package mm.kb.com.mondaymorning;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CustomFragment extends Fragment {
    private String mText = "";
    List<recyclerData> listItems;
    public static CustomFragment createInstance(List list)
    {
        CustomFragment fragment = new CustomFragment();
        fragment.listItems = list;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sample,container,false);
        //((TextView) v.findViewById(R.id.textView)).setText(mText);
        RVAdapter adapter = new RVAdapter(MyContext.getContext(), listItems);
        RecyclerView rv=(RecyclerView)v.findViewById(R.id.show_all_rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(new LinearLayoutManager(MyContext.getContext()));

        rv.setAdapter(adapter);
        return v;
    }
}