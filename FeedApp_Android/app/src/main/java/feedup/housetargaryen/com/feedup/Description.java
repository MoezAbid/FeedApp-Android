package feedup.housetargaryen.com.feedup;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import feedup.housetargaryen.com.feedup.Connection.Model.Application;

public class Description extends AppCompatActivity {
    private TextView Nom,Version,Description,Ram,Taille,dateMA;
    private ImageView AppImage,pubApp;
    private Button FeedBack,Telecharger,Convertir;
    private Application application;
    private Application aFN;
    private String ApkUrl;
    private RatingBar rateApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        initializeView();

        //Moez
    }

    private boolean checkPackage(String packageName, PackageManager pm){
        try{ pm.getPackageInfo(packageName, 0);
            return true;
        }
        catch(PackageManager.NameNotFoundException e){ return false; }
    }

    private void initializeView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });


       // Convertir= (Button) findViewById(R.id.Convertir);
        Nom =(TextView)findViewById(R.id.Nom);
        dateMA =(TextView)findViewById(R.id.Date_MA);
        Version =(TextView)findViewById(R.id.Version);
        Description =(TextView)findViewById(R.id.Description);
        Ram =(TextView)findViewById(R.id.Ram);
        Taille =(TextView)findViewById(R.id.Taille);
        AppImage = (ImageView) findViewById(R.id.AppImage);
        FeedBack = (Button) findViewById(R.id.FeedBack);
        Telecharger = (Button) findViewById(R.id.Telecharger);
        pubApp = (ImageView) findViewById(R.id.PubliciteAPP);
        rateApp = (RatingBar) findViewById(R.id.rateBar);
        Convertir = (Button) findViewById(R.id.Convertir);



        //Moez
        if (getIntent().getStringExtra("cf").equals("notif")) {
            loadFromNotificaion();
        } else if (getIntent().getStringExtra("cf").equals("liste")) {
            loadFromIntent();
        }

        //1
        Telecharger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(application.getApkUri().toString()));
                startActivity(browserIntent);
            }
        });



        FeedBack.setEnabled(false);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Application/");





        if(checkPackage(application.getNomPackage(), getApplicationContext().getPackageManager()))
        {
            FeedBack.setEnabled(true);
        }
        Nom.setText(application.getNomAPP());
        Version.setText(application.getVersionApp());
        dateMA.setText(application.getDateAjoutApp());
        Description.setText(application.getDescription());
        Ram.setText(Integer.toString(application.getRam()));
        Taille.setText(Float.toString(application.getTaille()));
        Picasso.with(getApplicationContext()).load(application.getLogoUri()).into(AppImage);
        Picasso.with(getApplicationContext()).load(application.getPubliciteUri()).into(pubApp);


        FeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),FeedbackActivity.class);
                intent.putExtra(Application.TAG, application);
                startActivity(intent);
            }
        });
        rateApp.setIsIndicator(true);
        rateApp.setRating(application.getRateAPP());
    }


    //Moez
    public void loadFromIntent() {

        application = getIntent().getParcelableExtra(Application.TAG);
        Nom.setText(application.getNomAPP());
        Version.setText(application.getVersionApp());
        Description.setText(application.getDescription());
        Ram.setText(Integer.toString(application.getRam()));
        Taille.setText(Float.toString(application.getTaille()));
        Picasso.with(getApplicationContext()).load(application.getLogoUri()).into(AppImage);
        ApkUrl = application.getApkUri().toString();
    }

    //Moez
    public void loadFromNotificaion() {

        Intent iFN = getIntent();
        application = (Application) iFN.getParcelableExtra("afn");
        Nom.setText(application.getNomAPP());
        Version.setText(application.getVersionApp());
        Description.setText(application.getDescription());
        Ram.setText(Integer.toString(application.getRam()));
        Taille.setText(Float.toString(application.getTaille()));
        Picasso.with(getApplicationContext()).load(application.getLogoUri()).into(AppImage);
        ApkUrl = application.getApkUri().toString();
    }
}

