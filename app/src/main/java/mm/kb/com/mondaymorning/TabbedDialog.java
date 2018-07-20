package mm.kb.com.mondaymorning;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class TabbedDialog extends DialogFragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    int tabIndex;
     List<recyclerData> listItems;
     List<recyclerData> recyclerDataList;

    public TabbedDialog() {
tabIndex=0;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.dialog_sample,container,false);
        tabLayout = (TabLayout) rootview.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) rootview.findViewById(R.id.masterViewPager);
        CustomAdapter adapter = new CustomAdapter(getChildFragmentManager());
        adapter.addFragment("Featured",CustomFragment.createInstance(listItems));
        adapter.addFragment("All News",CustomFragment.createInstance(recyclerDataList));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.categoriesBack),getResources().getColor(R.color.categoriesBack));
        TabLayout.Tab tab=tabLayout.getTabAt(tabIndex);
        tab.select();
        DisplayMetrics displayMetrics=new DisplayMetrics();
        return rootview;
    }
}