package feedup.housetargaryen.com.feedup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import feedup.housetargaryen.com.feedup.Connection.Model.User;

public class Profil extends AppCompatActivity {

    private Button convertir;
    private TextView nom;
    private TextView prenom;
    private TextView adresse;
    private TextView cin;
    private TextView mail;
    private TextView rib;
    private TextView Mypoints;
    private ImageView photoImageView;
    private DatabaseReference mRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);
        mRef = FirebaseDatabase.getInstance().getReference("Users/");
        initializeView();
    }

    private void initializeView() {
        convertir = (Button) findViewById(R.id.Convertir);
        nom = (TextView) findViewById(R.id.NomProfil);
        prenom = (TextView) findViewById(R.id.PrenomProfil);
        adresse = (TextView) findViewById(R.id.adresseProfil);
        cin = (TextView) findViewById(R.id.CinProfil);
        mail = (TextView) findViewById(R.id.mailProfil);
        rib = (TextView) findViewById(R.id.RIBProfil);
        Mypoints=(TextView) findViewById(R.id.mypoints);
        photoImageView = (ImageView) findViewById(R.id.ImageProfil);

        user = FirebaseAuth.getInstance().getCurrentUser();

        mail.setText(user.getEmail());

        mRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                prenom.setText(user1.getFirstName());
                nom.setText(user1.getLastName());
                adresse.setText(user1.getAdresse());
                cin.setText(Integer.toString(user1.getCin()));
                mail.setText(user1.getMail());
                rib.setText(user1.getRib());
                Mypoints.setText(Integer.toString(user1.getCumulDePoints()));
                String key = dataSnapshot.getKey();
                Picasso.with(getApplicationContext()).load(user.getPhotoUrl().toString()).into(photoImageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users/");

        convertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(), UpdateProfilActivity.class);
                startActivity(a);

            }
        });
    }


}
