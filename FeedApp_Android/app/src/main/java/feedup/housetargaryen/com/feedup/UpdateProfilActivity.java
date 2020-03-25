package feedup.housetargaryen.com.feedup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import feedup.housetargaryen.com.feedup.Connection.Model.User;

public class UpdateProfilActivity extends AppCompatActivity {

    private EditText editTextFirstName,editTextLastName,editTextAdresse,editTextRIB;
    private Button buttonUpdate;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private User user1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profil);
        initViews();
        initObjects();
        fillForm();
        updateUserInformations();
    }

    private void initViews(){
        editTextFirstName = (EditText) findViewById(R.id.FirstName);
        editTextLastName = (EditText) findViewById(R.id.LastName);
        editTextAdresse = (EditText) findViewById(R.id.Address);
        editTextRIB = (EditText) findViewById(R.id.RIB);
        buttonUpdate = (Button) findViewById(R.id.Update);
    }

    private void initObjects(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users/");
    }

    private void fillForm(){
        databaseReference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user1 = dataSnapshot.getValue(User.class);
                editTextFirstName.setText(user1.getFirstName());
                editTextLastName.setText(user1.getLastName());
                editTextAdresse.setText(user1.getAdresse());
                editTextRIB.setText(user1.getRib());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void updateUserInformations(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validSignUpInfo()) {
                    user1.setFirstName(editTextFirstName.getText().toString());
                    user1.setLastName(editTextLastName.getText().toString());
                    user1.setAdresse(editTextAdresse.getText().toString());
                    user1.setRib(editTextRIB.getText().toString());
                    databaseReference.child(user.getUid()).setValue(user1);
                    Toast.makeText(getApplicationContext(), "your profil has been updated ", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    public boolean validSignUpInfo() {



        if (editTextFirstName.getText().toString().isEmpty()) {
            editTextFirstName.setError("Please insert a first name.");
            return false;
        }

        if (editTextLastName.getText().toString().isEmpty()) {
            editTextLastName.setError("Please insert a last name.");
            return false;
        }

        if (editTextRIB.getText().toString().isEmpty()) {
            editTextRIB.setError("Please insert an RIB.");
            return false;
        } else if (editTextRIB.getText().toString().length() != 16) {
            editTextRIB.setError("Please insert a valid RIB.");
            return false;
        }

        return true;
    }
}
