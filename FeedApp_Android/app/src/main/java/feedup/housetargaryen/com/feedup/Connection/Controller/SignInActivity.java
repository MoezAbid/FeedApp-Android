package feedup.housetargaryen.com.feedup.Connection.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import feedup.housetargaryen.com.feedup.Connection.FirebaseUtils.FireBaseUserUtils;
import feedup.housetargaryen.com.feedup.HomeActivity;
import feedup.housetargaryen.com.feedup.MainActivity;
import feedup.housetargaryen.com.feedup.Notification.NotificationService;
import feedup.housetargaryen.com.feedup.R;

public class SignInActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btnSignIn;
    private EditText authEmail, authPass;
    private FirebaseAuth mAuth;
    private TextView clickToSignUp;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog loggingInDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.initializeView();

        this.initializeFirebase();
    }


    private void initializeView() {
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        authEmail = (EditText) findViewById(R.id.authEmail);
        authPass = (EditText) findViewById(R.id.authPass);
        clickToSignUp = (TextView) findViewById(R.id.tvSignUp);
        toolbar = (Toolbar) findViewById(R.id.actionBar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColor));
        setSupportActionBar(toolbar);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validLoginInfo();

                if (validLoginInfo()) {
                    loggingInDialog.show();
                    signInUser();
                }
            }
        });
        clickToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSignUpInent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(goToSignUpInent);
            }
        });

        loggingInDialog = new ProgressDialog(SignInActivity.this, R.style.GreenTheme);
        loggingInDialog.setMessage("Logging you in...");
    }

    private void initializeFirebase() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent goDirectlyToHome = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(goDirectlyToHome);
                    Log.d("Login Acitivity", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("Login Acitivity", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signInUser() {
        mAuth.signInWithEmailAndPassword(authEmail.getText().toString(), authPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loggingInDialog.hide();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new AwesomeErrorDialog(SignInActivity.this)
                        .setTitle("Oops, an error occurred.")
                        .setMessage("Could not connect to your FeedUp account, please verify your login informations.")
                        .setColoredCircle(R.color.dialogErrorBackgroundColor)
                        .setDialogIconAndColor(R.drawable.ic_dialog_error, R.color.white)
                        .setCancelable(true).setButtonText(getString(R.string.dialog_ok_button))
                        .setButtonBackgroundColor(R.color.dialogErrorBackgroundColor)
                        .setButtonText(getString(R.string.dialog_ok_button))
                        .setErrorButtonClick(new Closure() {
                            @Override
                            public void exec() {
                                // click
                            }
                        })
                        .show();
            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if (verifiyIfUsersVerifiedHisEmail()) {
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    Intent startServiceIntent = new Intent(getApplicationContext(), NotificationService.class);
                    startService(startServiceIntent);
                } else if (!verifiyIfUsersVerifiedHisEmail()) {
                    new AwesomeWarningDialog(SignInActivity.this)
                            .setTitle("Oh oh...")
                            .setMessage("It seems that you didn't verify your account yet, connect to your e-mail and click the confirmation link to be able to log in.")
                            .setColoredCircle(R.color.dialogNoticeBackgroundColor)
                            .setDialogIconAndColor(R.drawable.ic_notice, R.color.white)
                            .setCancelable(true)
                            .setButtonText(getString(R.string.dialog_ok_button))
                            .setButtonBackgroundColor(R.color.dialogNoticeBackgroundColor)
                            .setButtonText(getString(R.string.dialog_ok_button))
                            .setWarningButtonClick(new Closure() {
                                @Override
                                public void exec() {
                                    // click
                                }
                            })
                            .show();
                    FireBaseUserUtils fireBaseUserUtils = new FireBaseUserUtils();
                    fireBaseUserUtils.signOutCurrentlySignedInUser();
                }
            }
        });
    }

    public boolean validLoginInfo() {

        if (authEmail.getText().toString().isEmpty()) {
            authEmail.setError("Please insert an e-mail.");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(authEmail.getText().toString()).matches()) {
            authEmail.setError("Enter a valid e-mail.");
            return false;
        }

        if (authPass.getText().toString().length() < 6) {
            authPass.setError("Password must be at least 6 characters.", null);
            return false;
        }
        return true;
    }

    public boolean verifiyIfUsersVerifiedHisEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        boolean emailVerified = false;
        if (user != null) {
            emailVerified = user.isEmailVerified();
        }
        return emailVerified;
    }

}
