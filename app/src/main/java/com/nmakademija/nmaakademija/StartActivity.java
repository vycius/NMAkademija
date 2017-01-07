package com.nmakademija.nmaakademija;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.AuthUI.IdpConfig;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.provider.FacebookProvider;
import com.firebase.ui.auth.provider.GoogleProvider;
import com.firebase.ui.auth.provider.IdpProvider;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.crash.FirebaseCrash;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.NMAPreferences;

public class StartActivity extends BaseActivity implements IdpProvider.IdpCallback, View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private GoogleProvider googleProvider;
    private FacebookProvider facebookProvider;

    private TextView errorTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean notificationsEnabled = NMAPreferences.getNotifications(this);

        AppEvent.getInstance(this).setNotificationEnabledUserProperty(notificationsEnabled);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            startNextActivity();
        }

        setContentView(R.layout.activity_start);

        AppEvent.getInstance(this).trackCurrentScreen(this, "open_sign_in");

        errorTV = (TextView) findViewById(R.id.error);
        findViewById(R.id.google_sign_in_button).setOnClickListener(this);
        findViewById(R.id.facebook_login_button).setOnClickListener(this);

        initProviders();
    }

    private void initProviders() {
        googleProvider = new GoogleProvider(this,
                new IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());
        googleProvider.setAuthenticationCallback(this);

        facebookProvider = new FacebookProvider(this,
                new IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build());
        facebookProvider.setAuthenticationCallback(this);
    }


    private void signInWithGoogle() {
        logOut();
        googleProvider.startLogin(this);
    }

    private void signInWithFacebook() {
        logOut();
        facebookProvider.startLogin(this);
    }

    private void startNextActivity() {
        Intent intent;
        if (NMAPreferences.getSection(this) == 0) {
            intent = new Intent(this, OnboardingActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }

        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccess(IdpResponse idpResponse) {
        AuthCredential authCredential;

        switch (idpResponse.getProviderType()) {
            case GoogleAuthProvider.PROVIDER_ID:
                authCredential = GoogleProvider.createAuthCredential(idpResponse);
                break;
            case FacebookAuthProvider.PROVIDER_ID:
                authCredential = FacebookProvider.createAuthCredential(idpResponse);
                break;
            default:
                throw new UnsupportedOperationException("Unsupoorted auth provider has been called");
        }

        firebaseAuth.signInWithCredential(authCredential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                startNextActivity();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                FirebaseCrash.report(e);

                String errorText;
                if (e instanceof FirebaseAuthUserCollisionException) {
                    errorText = getString(R.string.error_user_collision);
                } else {
                    errorText = e.getLocalizedMessage();
                }

                errorTV.setText(errorText);
                errorTV.setVisibility(View.VISIBLE);
                logOut();
            }
        });
    }

    @Override
    public void onFailure(Bundle extra) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.google_sign_in_button:
                signInWithGoogle();
                break;
            case R.id.facebook_login_button:
                signInWithFacebook();
                break;
        }
    }

    private void logOut() {
        LoginManager.getInstance().logOut();
        firebaseAuth.signOut();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookProvider.onActivityResult(requestCode, resultCode, data);
        googleProvider.onActivityResult(requestCode, resultCode, data);
    }
}
