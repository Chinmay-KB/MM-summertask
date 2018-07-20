package mm.kb.com.mondaymorning;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
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

public class ContactUs extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.contactus_layout);
Toolbar tv=(Toolbar)findViewById(R.id.contact_us_toolbar);
tv.setTitle("Contact Us");
tv.setTitleTextColor(getResources().getColor(R.color.cardview_light_background));
    }

    public void locateMM(View v) {
        Intent mapIntent = new Intent("android.intent.action.VIEW", Uri.parse("geo:" + getString(R.string.mm_location_latitude) + "," + getString(R.string.mm_location_longitude)));
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Google Maps App not found!", 0).show();
        }
    }

    public void mailPerson(View v) {
        String emailId = ((TextView) ((View) v.getParent()).findViewById(R.id.emailId_textView)).getText().toString();
        Log.i("Email ID", emailId);
        Intent emailIntent = new Intent("android.intent.action.SENDTO", Uri.fromParts("mailto", emailId, null));
        emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{emailId});
        try {
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Email Client App not found!", Toast.LENGTH_SHORT).show();
        }
    }

    public String getFacebookPageURL(Context context) {
        String FACEBOOK_URL = "https://www.facebook.com/mondaymorning.NITRKL";
        String FACEBOOK_PAGE_ID = "mondaymorning.NITRKL";
        try {
            if (getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode >= 3002850) {
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            }
            return "fb://page/" + FACEBOOK_PAGE_ID;
        } catch (NameNotFoundException e) {
            return FACEBOOK_URL;
        }
    }

    public void openMmFacebookPage(View v) {
        Intent facebookIntent = new Intent("android.intent.action.VIEW");
        facebookIntent.setData(Uri.parse(getFacebookPageURL(this)));
        startActivity(facebookIntent);
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
