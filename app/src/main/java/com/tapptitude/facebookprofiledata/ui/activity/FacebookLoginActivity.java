package com.tapptitude.facebookprofiledata.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.tapptitude.facebookprofiledata.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FacebookLoginActivity extends AppCompatActivity {
    public static final String FACEBOOK_ACCESS_TOKEN = "FACEBOOK_ACCESS_TOKEN";
    public static final String[] FACEBOOK_PERMISSIONS = {"email", "user_photos", "user_posts", "user_friends"};

    private CallbackManager mCallbackManager;
    @BindView(R.id.am_lb_facebook_login)
    LoginButton mFbLoginButton;
    private AccessToken mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        ButterKnife.bind(this);
        initializeFbLogin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initializeFbLogin() {
        LoginManager.getInstance().logOut();
        mCallbackManager = CallbackManager.Factory.create();
        mFbLoginButton.setReadPermissions(FACEBOOK_PERMISSIONS);
        mFbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mAccessToken = loginResult.getAccessToken();
                Intent intent = new Intent(FacebookLoginActivity.this, DataBaseScreenActivity.class);
                intent.putExtra(FACEBOOK_ACCESS_TOKEN, mAccessToken);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Log.e("tagtag", "cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("tagtag", error.toString());
            }
        });
    }
}
