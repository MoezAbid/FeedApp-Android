package feedup.housetargaryen.com.feedup;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;

import feedup.housetargaryen.com.feedup.Connection.Model.Application;
import feedup.housetargaryen.com.feedup.Connection.Model.FeedBack;
import feedup.housetargaryen.com.feedup.Connection.Model.User;

public class FeedbackActivity extends AppCompatActivity {

    private FeedBack feedBack;
    private String appKey;

    private static final String TAG = "FeedbackActivity.java";

    private RadioGroup radioGroupQ1, radioGroupQ2, radioGroupQ3, radioGroupQ4, radioGroupQ5, radioGroupQ6,
            radioGroupQ7, radioGroupQ8, radioGroupQ9, radioGroupQ10;
    private EditText commentaire;
    private Button choosePicture;

    private TextView picturePath;
    private Button sendFeedback;

    private int noteQ1, noteQ2, noteQ3, noteQ4, noteQ5, noteQ6, noteQ7, noteQ8, noteQ9, noteQ10;

    private int noteTotale = 0;
    private float newAppRate;
    private int nbrFeedBack;


    private String keyApp;

    private String dateFeedBack;

    private String temporaryFeedBackId;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference myRefUser;
    private FirebaseUser user;
    private StorageReference screenshotsForFeedback;

    private static final int PICK_IMAGE = 100;
    private static final int GALLERY_INTENT = 2;

    private Uri imageUri;

    private Application applic;

    private User user1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        screenshotsForFeedback = FirebaseStorage.getInstance().getReference();

        final Application application = getIntent().getParcelableExtra(Application.TAG);
        keyApp = application.getNomAPP();

        initViews();
        initObjects();
        checkNotePerQuestion();

        myRefUser.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user1 = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.child("Application").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    if(childSnapshot.getValue(Application.class).getNomAPP().equals(application.getNomAPP())) {
                        appKey = childSnapshot.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                dateFeedBack = mydate;

                noteTotale = noteQ1 + noteQ2 + noteQ3 + noteQ4 + noteQ5 + noteQ6 + noteQ7 + noteQ8
                        + noteQ9 + noteQ10;

                feedBack = new FeedBack("ee", commentaire.getText().toString(), mydate, user.getUid()
                        , appKey, noteTotale);
                myRef.child("Feedbacks").push().setValue(feedBack);

                nbrFeedBack = application.getNombreFeedBack()+1;
                newAppRate = (application.getRateAPP()+noteTotale)/nbrFeedBack;

                Application aaa;
                aaa = new Application(application.getApkUri(),application.getNbrPoints(),application.getNomAPP()
                        ,application.getNomPackage(),application.getPubliciteUri(),application.getRam()
                        ,newAppRate,application.getVersionApp(),application.getDateAjoutApp(),application.getDescription()
                        ,application.getLogoUri(),nbrFeedBack,application.getTaille());


                DatabaseReference myRefApp = database.getReference("Application");
                myRefApp.child(feedBack.getIdApplication()).setValue(aaa);
                user1.setCumulDePoints(user1.getCumulDePoints()+aaa.getNbrPoints());
                myRefUser.child(user.getUid()).setValue(user1);







                uploadPicture();
                Toast.makeText(getApplicationContext(), "The FeedBack has been sent, thank you.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),Description.class);
                intent.putExtra(Application.TAG, aaa);
                intent.putExtra("cf", "liste");
                startActivity(intent);

            }
        });

        choosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

    }

    private void initViews() {
        commentaire = (EditText) findViewById(R.id.Commentaire);
        choosePicture = (Button) findViewById(R.id.uplaodpic);
        picturePath = (TextView) findViewById(R.id.imagePath);

        sendFeedback = (Button) findViewById(R.id.valider);
        sendFeedback.setEnabled(false);

        radioGroupQ1 = (RadioGroup) findViewById(R.id.Q1);
        radioGroupQ2 = (RadioGroup) findViewById(R.id.Q2);
        radioGroupQ3 = (RadioGroup) findViewById(R.id.Q3);
        radioGroupQ4 = (RadioGroup) findViewById(R.id.Q4);
        radioGroupQ5 = (RadioGroup) findViewById(R.id.Q5);
        radioGroupQ6 = (RadioGroup) findViewById(R.id.Q6);
        radioGroupQ7 = (RadioGroup) findViewById(R.id.Q7);
        radioGroupQ8 = (RadioGroup) findViewById(R.id.Q8);
        radioGroupQ9 = (RadioGroup) findViewById(R.id.Q9);
        radioGroupQ10 = (RadioGroup) findViewById(R.id.Q10);
    }

    private void initObjects() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        myRefUser = FirebaseDatabase.getInstance().getReference("Users/");
    }

    private void checkNotePerQuestion() {

        radioGroupQ1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.Q1_0:
                        noteQ1 = 0;
                        break;

                    case R.id.Q1_1:
                        noteQ1 = 1;
                        break;

                    case R.id.Q1_2:
                        noteQ1 = 2;
                }
            }
        });

        radioGroupQ2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.Q2_0:
                        noteQ2 = 0;
                        break;

                    case R.id.Q2_1:
                        noteQ2 = 1;
                        break;

                    case R.id.Q2_2:
                        noteQ2 = 2;
                }
            }
        });

        radioGroupQ3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.Q3_0:
                        noteQ3 = 0;
                        break;

                    case R.id.Q3_1:
                        noteQ3 = 1;
                        break;

                    case R.id.Q3_2:
                        noteQ3 = 2;
                }
            }
        });

        radioGroupQ4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.Q4_0:
                        noteQ4 = 0;
                        break;

                    case R.id.Q4_1:
                        noteQ4 = 1;
                        break;

                    case R.id.Q4_2:
                        noteQ4 = 2;
                }
            }
        });

        radioGroupQ5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.Q5_0:
                        noteQ5 = 0;
                        break;

                    case R.id.Q5_1:
                        noteQ5 = 1;
                        break;

                    case R.id.Q5_2:
                        noteQ5 = 2;
                }
            }
        });

        radioGroupQ6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.Q6_0:
                        noteQ6 = 0;
                        break;

                    case R.id.Q6_1:
                        noteQ6 = 1;
                        break;

                    case R.id.Q6_2:
                        noteQ6 = 2;
                }
            }
        });

        radioGroupQ7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.Q7_0:
                        noteQ7 = 0;
                        break;

                    case R.id.Q7_1:
                        noteQ7 = 1;
                        break;

                    case R.id.Q7_2:
                        noteQ7 = 2;
                }
            }
        });

        radioGroupQ8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.Q8_0:
                        noteQ8 = 0;
                        break;

                    case R.id.Q8_1:
                        noteQ8 = 1;
                        break;

                    case R.id.Q8_2:
                        noteQ8 = 2;
                }
            }
        });

        radioGroupQ9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.Q9_0:
                        noteQ9 = 0;
                        break;

                    case R.id.Q9_1:
                        noteQ9 = 1;
                        break;

                    case R.id.Q9_2:
                        noteQ9 = 2;
                }
            }
        });

        radioGroupQ10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.Q10_0:
                        noteQ10 = 0;
                        break;

                    case R.id.Q10_1:
                        noteQ10 = 1;
                        break;

                    case R.id.Q10_2:
                        noteQ10 = 2;
                }
            }
        });
    }

    public void choosePicture() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select a Picture"), GALLERY_INTENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            imageUri = data.getData();
            Toast.makeText(getApplicationContext(), "The image has been chosen.", Toast.LENGTH_SHORT).show();
            picturePath.setText(imageUri.getLastPathSegment().toString());
            sendFeedback.setEnabled(true);
        }
    }

    public void uploadPicture() {

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference().child("FeedBackScreenShots/");

        StorageReference screenshotsRef = storageRef.child(user.getUid()+"|"+keyApp+"|"+dateFeedBack);

        screenshotsRef.putFile(imageUri);
    }

}
