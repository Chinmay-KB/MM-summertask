package mm.kb.com.mondaymorning;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class HallInfo extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_halls);
        Toolbar tb=findViewById(R.id.toolbarHalls);
        tb.setTitle("Halls of Residence");
        tb.setTitleTextColor(getResources().getColor(R.color.cardview_light_background));
    }
}
