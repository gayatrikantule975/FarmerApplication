package com.example.farmerapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class UserHomeActivity extends AppCompatActivity {
TextView tvEmailName,tvEmailId;
AppCompatButton btnSignOut;
GoogleSignInOptions googleSignInOptions;
GoogleSignInClient googleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        tvEmailName=findViewById(R.id.tvEmailName);
        tvEmailId=findViewById(R.id.tvEmailId);
        btnSignOut=findViewById(R.id.btnSignOut);
        googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient= GoogleSignIn.getClient(UserHomeActivity.this,googleSignInOptions);
        GoogleSignInAccount googleSignInAccount=GoogleSignIn.getLastSignedInAccount(this);
        if(googleSignInAccount!=null)
        {
            String name=googleSignInAccount.getDisplayName();
            String email=googleSignInAccount.getEmail();
            tvEmailName.setText(name);
            tvEmailId.setText(email);
            btnSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                          Intent intent=new Intent(UserHomeActivity.this,LoginActivity.class);
                          startActivity(intent);
                          finish();
                        }
                    });
                }
            });
        }

    }
}