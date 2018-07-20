package mm.kb.com.mondaymorning;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class sacInfo extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.info_sac);
        Toolbar tb=(Toolbar)findViewById(R.id.sacToolbar);
        tb.setTitle("SAC Info");
        tb.setTitleTextColor(getResources().getColor(R.color.cardview_light_background));
    }

    public void callPerson(View v) {
        String mobileNo = ((TextView) ((View) v.getParent()).findViewById(R.id.mobileNo_textView)).getText().toString();
        Log.i("Mobile Number", mobileNo);
        Intent callIntent = new Intent("android.intent.action.CALL");
        callIntent.setData(Uri.fromParts("tel", mobileNo, null));
        if (ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CALL_PHONE"}, 10);
            return;
        }
        try {
            startActivity(callIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Calling App not found!", Toast.LENGTH_SHORT).show();
        }
    }

    public void mailPerson(View v) {
        String emailId = ((TextView) ((View) v.getParent()).findViewById(R.id.emailId_textView)).getText().toString();
        Log.i("Email ID", emailId);
        Intent emailIntent = new Intent("android.intent.action.SENDTO", Uri.fromParts("mailto", emailId, null));
        emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{emailId});
        try {
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Email Client App not found!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
