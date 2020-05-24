package com.vegamex.redessociales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

public class MainActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;
    ShareButton shareButton;
    TextView enlaceTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shareButton = findViewById(R.id.fb_share_button);
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        enlaceTexto = findViewById(R.id.enlaceTexto);

        enlaceTexto.setVisibility(View.GONE);
        shareButton.setVisibility(View.GONE);

        callbackManager = CallbackManager.Factory.create();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn){
            Toast.makeText(MainActivity.this, "Aún estás logueado", Toast.LENGTH_SHORT).show();
            enlaceTexto.setVisibility(View.VISIBLE);
            shareButton.setVisibility(View.VISIBLE);
        }

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code

                        enlaceTexto.setVisibility(View.VISIBLE);
                        shareButton.setVisibility(View.VISIBLE);

                        Toast.makeText(MainActivity.this, "Iniciaste sesión en Facebook", Toast.LENGTH_SHORT).show();

                        ShareLinkContent content = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                                .build();

                        shareButton.setShareContent(content);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }
}
