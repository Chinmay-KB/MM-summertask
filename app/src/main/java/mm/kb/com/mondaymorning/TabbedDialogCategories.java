package mm.kb.com.mondaymorning;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class TabbedDialogCategories extends DialogFragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    int tabIndex;
     List<recyclerData> departmentList;
     List<recyclerData> campusList;
     List<recyclerData> viewsList;
     List<recyclerData> careerList;
     List<recyclerData> alumniList;
     List<recyclerData> ddcwcList;

    public TabbedDialogCategories() {
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
        adapter.addFragment("Departments",CustomFragment.createInstance(departmentList));
        adapter.addFragment("Campus",CustomFragment.createInstance(campusList));
        adapter.addFragment("Views",CustomFragment.createInstance(viewsList));
        adapter.addFragment("Career",CustomFragment.createInstance(careerList));
        adapter.addFragment("Alumni",CustomFragment.createInstance(alumniList));
        adapter.addFragment("DD&CWC",CustomFragment.createInstance(ddcwcList));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.categoriesBack),getResources().getColor(R.color.categoriesBack));
        TabLayout.Tab tab=tabLayout.getTabAt(tabIndex);
        tab.select();
        return rootview;
    }
}