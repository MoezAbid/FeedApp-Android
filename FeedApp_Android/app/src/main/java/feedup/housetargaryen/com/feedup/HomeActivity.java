package feedup.housetargaryen.com.feedup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    ImageView showMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_test);

        showMe = (ImageView) findViewById(R.id.showMe);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Picasso.with(getApplicationContext()).load(user.getPhotoUrl()).into(showMe);
        } else {
            Toast.makeText(getApplicationContext(), "No user connected ",Toast.LENGTH_LONG).show();
        }
    }
}
