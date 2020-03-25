package feedup.housetargaryen.com.feedup.Connection.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import feedup.housetargaryen.com.feedup.Connection.FirebaseUtils.FireBaseUserUtils;
import feedup.housetargaryen.com.feedup.Connection.Model.User;
import feedup.housetargaryen.com.feedup.R;

public class SignUpActivity extends AppCompatActivity {

    //Initializing views
    private EditText email;
    private EditText firstName;
    private EditText lastName;
    private EditText password;
    private EditText passwordConfirmation;
    private EditText cin;
    private EditText address;
    private EditText rib;
    private Button btnChooseProfilePic;
    private Button btnSignUp;
    private TextView clickToSignIn;

    ProgressDialog signingUserUpDialog;

    //Uploaded profile pic data:
    private Uri imageUri;

    //Initializing Strings :
    private String emailString;
    private String firstNameString;
    private String lastNameString;
    private String passwordString;
    private String passwordConfirmationString;
    private String cinString;
    private String addressString;
    private String ribString;

    //FireBase
    //FireBase Database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference refUsers = database.getReference("Users");

    //FireBase Storage
    private StorageReference profilePicsStorageReferenceFolder;

    private FirebaseAuth mAuth;

    private static final String TAG = "SignUpActivity.java";
    private static final int PICK_IMAGE = 100;
    private static final int GALLERY_INTENT = 2;
    //Id's

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initializeViews();

        initializeFireBase();
    }

    public void initializeViews() {

        //Finding views by id.
        email = (EditText) findViewById(R.id.signUpEmail);
        firstName = (EditText) findViewById(R.id.signUpFirstName);
        lastName = (EditText) findViewById(R.id.signUpLastName);
        password = (EditText) findViewById(R.id.signUpPass);
        passwordConfirmation = (EditText) findViewById(R.id.signUpPassConfirmation);
        cin = (EditText) findViewById(R.id.signUpCIN);
        address = (EditText) findViewById(R.id.signUpAddress);
        rib = (EditText) findViewById(R.id.signUpRIB);
        btnChooseProfilePic = (Button) findViewById(R.id.signUpBtnUploadProfilePic);
        btnSignUp = (Button) findViewById(R.id.signUpBtnSignUp);
        clickToSignIn = (TextView) findViewById(R.id.signUpTvSignIn);
        btnChooseProfilePic = (Button) findViewById(R.id.signUpBtnUploadProfilePic);

        clickToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLogin = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(goToLogin);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validSignUpInfo()) {
                    signingUserUpDialog.show();
                    signUpNewUser();
                }
            }
        });

        btnChooseProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        signingUserUpDialog = new ProgressDialog(SignUpActivity.this, R.style.GreenTheme);
        signingUserUpDialog.setMessage("Signing you up...");
    }

    public boolean validSignUpInfo() {

        if (email.getText().toString().isEmpty()) {
            email.setError("Please insert an e-mail.");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError("Enter a valid e-mail.");
            return false;
        }

        if (firstName.getText().toString().isEmpty()) {
            firstName.setError("Please insert a first name.");
            return false;
        }

        if (lastName.getText().toString().isEmpty()) {
            lastName.setError("Please insert a last name.");
            return false;
        }

        if (password.getText().toString().length() < 6) {
            password.setError("Password must be at least 6 characters long.");
            return false;
        } else if (!password.getText().toString().equals(passwordConfirmation.getText().toString())) {
            passwordConfirmation.setError("Passwords do not match.");
            return false;
        }

        if (cin.getText().toString().isEmpty()) {
            cin.setError("Please insert CIN number.");
            return false;
        } else if (cin.getText().toString().length() != 8) {
            cin.setError("CIN number must be 8 digits long.");
            return false;
        }

        if (rib.getText().toString().isEmpty()) {
            rib.setError("Please insert an RIB.");
            return false;
        } else if (rib.getText().toString().length() != 16) {
            rib.setError("Please insert a valid RIB.");
            //TODO (4) Moez: thabtou  fel RIB 9adech el longueur bté3ou  w chnowa el format.
            return false;
        }

        return true;
    }

    public void signUpNewUser() {

        //Filling strings with data from the form
        emailString = email.getText().toString();
        firstNameString = firstName.getText().toString();
        lastNameString = lastName.getText().toString();
        passwordString = password.getText().toString();
        passwordConfirmationString = passwordConfirmation.getText().toString();
        cinString = cin.getText().toString();
        ribString = rib.getText().toString();
        addressString = address.getText().toString();


        mAuth.createUserWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Sorry, sign up failed, maybe check the credentials again ?",
                                    Toast.LENGTH_SHORT).show();
                            signingUserUpDialog.hide();
                        } else {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                            uploadPicture();
                            insertUserIntoDatabase();
                            signingUserUpDialog.hide();
                            //Toast.makeText(SignUpActivity.this, "Sign up successful.",
                            // Toast.LENGTH_SHORT).show();
                            verifiyEmail();
                        }
                    }
                });
    }

    public void initializeFireBase() {

        mAuth = FirebaseAuth.getInstance();
        profilePicsStorageReferenceFolder = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().signOut();
    }

    public void insertUserIntoDatabase() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //TODO :  Effacer l'attirubut "verified Email."
            User newUser = new User(emailString, passwordString, firstNameString, lastNameString,
                    Integer.parseInt(cinString), ribString, addressString, 0);
            refUsers.child(user.getUid()).setValue(newUser);
        }
    }

    public void choosePicture() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select a Picture"), GALLERY_INTENT);
    }

    //TODO (5) Moez: Include code for marshmallow.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            imageUri = data.getData();
            Toast.makeText(getApplicationContext(), "The image has been added.", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadPicture() {

        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {

                Uri file = imageUri;
                StorageReference usersPicsRef = profilePicsStorageReferenceFolder.child("UsersProfilePics/" + user.getUid());

                usersPicsRef.putFile(file)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content

                                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                //Adding the picture to the user's profile :
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setPhotoUri(downloadUrl)
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "User profile updated.");
                                                }
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                                Toast.makeText(getApplicationContext(), "Problem uploading the image.", Toast.LENGTH_LONG).show();
                            }
                        });
            } else {
                Toast.makeText(getApplicationContext(), "Error : User not created (Already exists).", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verifiyEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            new AwesomeSuccessDialog(SignUpActivity.this)
                                    .setTitle("Congratulations !")
                                    .setMessage("Your account has been created, verify your e-mail to log in.")
                                    .setColoredCircle(R.color.dialogSuccessBackgroundColor)
                                    .setDialogIconAndColor(R.drawable.ic_success, R.color.white)
                                    .setCancelable(true)
                                    .setPositiveButtonText("Okay")
                                    .setPositiveButtonbackgroundColor(R.color.dialogSuccessBackgroundColor)
                                    .setPositiveButtonTextColor(R.color.white)
                                    .setPositiveButtonClick(new Closure() {
                                        @Override
                                        public void exec() {
                                            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                                        }
                                    })
                                    .show();
                        }
                    }
                });
    }
}