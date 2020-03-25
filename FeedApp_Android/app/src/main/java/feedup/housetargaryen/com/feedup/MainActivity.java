package feedup.housetargaryen.com.feedup;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import feedup.housetargaryen.com.feedup.Connection.Controller.SignInActivity;
import feedup.housetargaryen.com.feedup.Connection.Model.User;
import feedup.housetargaryen.com.feedup.Fragment.liste_app;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView name, email;
    private ImageView profilePic;
    private String firstName;
    private String lastName;
    private String displayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi_draw);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users/");






        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.tool);
        toolbar.setOverflowIcon(drawable);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setFragment(new liste_app());

        View header = navigationView.getHeaderView(0);
        email = (TextView) header.findViewById(R.id.Email_User);
        email.setText(user.getEmail());
        name = (TextView) header.findViewById(R.id.Name_User);


        profilePic = (ImageView) header.findViewById(R.id.profilePic);
        Picasso.with(getApplicationContext()).load(user.getPhotoUrl()).into(profilePic);

        mRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                firstName = user1.getFirstName();
                lastName = user1.getLastName();
                String key = dataSnapshot.getKey();
                displayName = firstName + " " + lastName;
                name.setText(displayName);
                //Toast.makeText(getApplicationContext(),displayName,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//View view=navigationView.inflateHeaderView(R.layout.nav_header_main);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
        // Inflate the menu; this adds items to the action bar if it is present.

        // getMenuInflater().inflate(R.menu.menu_page_accueil1, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int id1 = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        }
     if (id1 == R.id.action_settings1) {

            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_accueil) {

            //  Tabs f = new Tabs();

            // setFragment(f);
            Intent a = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(a);

        } else if (id == R.id.nav_device) {

        }  else if (id == R.id.nav_contact) {

            Intent a = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(a);
        } else if (id == R.id.nav_disconnect) {
            Toast.makeText(getApplicationContext(), "Disconnected see you again" ,Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().signOut();
            Intent goToLoginScreen = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(goToLoginScreen);
            finish();
        }
        else if (id == R.id.nav_profile) {

            Intent a = new Intent(getApplicationContext(), Profil.class);
            startActivity(a);


        }
        else if (id == R.id.nav_points) {
            Intent a = new Intent(getApplicationContext(), Conversion.class);
            startActivity(a);
        }

        return true;
    }


    public void setFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_navi_draw, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
