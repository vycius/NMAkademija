package com.nmakademija.nmaakademija;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.NMAPreferences;

public class StartActivity extends BaseActivity {
    GoogleSignInOptions gso;
    GoogleApiClient mGoogleApiClient;
    CallbackManager mCallbackManager;

    private TextView errorTV;

    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean notificationsEnabled = NMAPreferences.getNotifications(this);

        AppEvent.getInstance(this).setNotificationEnabledUserProperty(notificationsEnabled);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_start);

        errorTV = (TextView) findViewById(R.id.error);

        AppEvent.getInstance(this).trackCurrentScreen(this, "open_sign_in");

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        findViewById(R.id.google_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });

        mCallbackManager = CallbackManager.Factory.create();
        final LoginButton loginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (!loginResult.getAccessToken().isExpired())
                    signIn(FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken()));
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                signIn(GoogleAuthProvider.getCredential(result.getSignInAccount().getIdToken(), null));
            }
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    protected void signIn(AuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    if (task.getException() != null) {
                        String errorText;
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            errorText = getString(R.string.error_user_collision);
                        } else {
                            errorText = task.getException().toString();
                        }
                        errorTV.setText(errorText);
                        errorTV.setVisibility(View.VISIBLE);
                    }
                    LoginManager.getInstance().logOut();
                }
            }
        });
    }

    @Override
    protected void onUserChange(@Nullable FirebaseUser user) {
        super.onUserChange(user);
        if (user != null) {
            Intent intent;
            if (NMAPreferences.getSection(this) == 0) {
                intent = new Intent(this, OnboardingActivity.class);
            } else {
                intent = new Intent(this, MainActivity.class);
            }

            startActivity(intent);
            finish();
        }
    }

}
