package feedup.housetargaryen.com.feedup.Fragment;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import feedup.housetargaryen.com.feedup.Connection.Model.Application;
import feedup.housetargaryen.com.feedup.MyRecyclerAdapter;
import feedup.housetargaryen.com.feedup.R;

/**
 * Created by ASUS on 31/10/2017.
 */

public class liste_app extends android.support.v4.app.Fragment {

    public String nomAPP;
    private RecyclerView mRecyclerView;
    private ArrayList<Application> Items = new ArrayList<Application>();
    private MyRecyclerAdapter mAdapter;
    private EditText editTextSearchApp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recycleview, container, false);

        editTextSearchApp = (EditText) rootView.findViewById(R.id.searchAppEditText);

        editTextSearchApp.setText("");

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Firebase.setAndroidContext(getActivity());

        DatabaseReference myFirebaseRef = FirebaseDatabase.getInstance().getReference("/Application");

        myFirebaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Application app = dataSnapshot.getValue(Application.class);
                Items.add(app);

                mAdapter = new MyRecyclerAdapter(Items);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        editTextSearchApp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ArrayList<Application> newList = new ArrayList<Application>();
                for (Application applicationSearch : Items) {
                    String name = applicationSearch.getNomAPP().toLowerCase();
                    if (name.contains(s.toString().toLowerCase())) {
                        newList.add(applicationSearch);
                    }
                }
                mAdapter.setFilter(newList);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootView;
    }
}
