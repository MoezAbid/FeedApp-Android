package feedup.housetargaryen.com.feedup;

import android.support.annotation.IdRes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import 	java.util.Date;


import feedup.housetargaryen.com.feedup.Connection.Model.Payement;
import feedup.housetargaryen.com.feedup.Connection.Model.User;

import static android.R.attr.name;


import java.util.Calendar;


public class Conversion extends AppCompatActivity {

    private Payement payement;
    Date now = Calendar.getInstance().getTime();
    private TextView NbrPoints;
    private FirebaseUser user;
    private RadioGroup pts;
    private RadioButton pts10,pts20,pts30,pts40,pts50;
    private int sum;
    private Button conversion;
    private String cumulDePoints;
    private String userUID;
    private TextView Message;
    private TextView date;
    private String RIB;
    private DatabaseReference databaseReference;
    private User user1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        initializeView();
        initObjects();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users/");


        userUID=user.getUid();

        mRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                user1 = dataSnapshot.getValue(User.class);
               cumulDePoints = Integer.toString(user1.getCumulDePoints());
               RIB = user1.getRib();
               NbrPoints.setText(cumulDePoints);
               }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });


        pts.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.pts10000 :{if(Integer.parseInt(cumulDePoints)<10000){
                        Message.setText("vous n'avez pas assez de points pour convertir ");
                        conversion.setEnabled(false);
                    }else
                    {Message.setText("vous pouvez convertir ");
                        conversion.setEnabled(true);}
                        break;}
                    case R.id.pts20000 :{if(Integer.parseInt(cumulDePoints)<20000){
                        Message.setText("vous n'avez pas assez de points pour convertir ");
                        conversion.setEnabled(false);
                    }else
                    {Message.setText("vous pouvez convertir ");
                        conversion.setEnabled(true);}
                        break;}

                    case R.id.pts30000 :{if(Integer.parseInt(cumulDePoints)<30000){
                        Message.setText("vous n'avez pas assez de points pour convertir ");
                        conversion.setEnabled(false);
                    }else
                    {Message.setText("vous pouvez convertir ");
                        conversion.setEnabled(true);}
                        break;}
                    case R.id.pts40000 :{if(Integer.parseInt(cumulDePoints)<40000){
                        Message.setText("vous n'avez pas assez de points pour convertir ");
                        conversion.setEnabled(false);
                    }else
                    {Message.setText("vous pouvez convertir ");
                        conversion.setEnabled(true);}
                        break;}
                    case R.id.pts50000 :{if(Integer.parseInt(cumulDePoints)<50000){
                        Message.setText("vous n'avez pas assez de points pour convertir ");
                        conversion.setEnabled(false);
                    }else
                    {Message.setText("vous pouvez convertir ");
                        conversion.setEnabled(true);}
                        break;}
                }
            }
        });






        conversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                int selectId = pts.getCheckedRadioButtonId();

                if(selectId == pts10.getId()){
                    sum=10;

                }else if(selectId == pts20.getId()) {
                    sum = 20;
                }else if(selectId == pts30.getId()){
                    sum = 30;
                }else if(selectId == pts40.getId()){
                    sum = 40;
                }else {
                    sum = 50;
                }
                user1.setCumulDePoints(user1.getCumulDePoints()-(sum*1000));
                payement.setMontant(sum);
                payement.setUIDUser(userUID);
                payement.setEtatPayement("En cours");
                payement.setDate(mydate);
                payement.setRIB(RIB);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                //myRef.child().setValue(payement);
                myRef.child("Payements").push().setValue(payement);
                DatabaseReference myRefusers = database.getReference("Users");
                myRefusers.child(userUID).setValue(user1);
            }
        });

    }

    private void initializeView(){
        Message = (TextView)  findViewById(R.id.Message);
        NbrPoints = (TextView) findViewById(R.id.NbrPoints);
        pts = (RadioGroup) findViewById(R.id.pts);
        pts10 = (RadioButton) findViewById(R.id.pts10000);
        pts20 = (RadioButton) findViewById(R.id.pts20000);
        pts30 = (RadioButton) findViewById(R.id.pts30000);
        pts40 = (RadioButton) findViewById(R.id.pts40000);
        pts50 = (RadioButton) findViewById(R.id.pts50000);
        conversion = (Button) findViewById(R.id.Convertir);

        date = (TextView) findViewById(R.id.date);
        payement=new Payement();
        conversion.setEnabled(false);



    }

    private void initObjects(){
        user = FirebaseAuth.getInstance().getCurrentUser();
    }
}
