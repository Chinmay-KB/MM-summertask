package mm.kb.com.mondaymorning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
    }
    public void goToSignin(View view)
    {
        startActivity(new Intent(this,SignIn.class));
    }
    public void goToLogin(View view)
    {
        Toast.makeText(this,"Log in Ability yet to be implemented", Toast.LENGTH_SHORT);
    }

}
