package feedup.housetargaryen.com.feedup.Connection.FirebaseUtils;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Moez on 18/10/2017.
 */

public class FireBaseUserUtils {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public FireBaseUserUtils() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void signOutCurrentlySignedInUser() {
        mAuth.signOut();
    }
}
