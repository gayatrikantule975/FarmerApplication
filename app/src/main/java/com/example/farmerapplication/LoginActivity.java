package com.example.farmerapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.farmerapplication.common.NetworkChangeListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    ImageView ivLoginLogo;
    TextView tvLoginText,tvNewUser,tvLoginForgetPassword;
    EditText etLoginUserName,etLoginPassword;
    CheckBox cbLogin;
    Button btnLogin;
    ProgressDialog progressDialog;
    NetworkChangeListener networkChangeListener=new NetworkChangeListener();
    GoogleSignInOptions googleSignInOptions;//when we click on button then gmail option from which we can login shown
    GoogleSignInClient googleSignInClient;//option which has selcted is stored there
    AppCompatButton btnLoginSigninwithgoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ivLoginLogo = findViewById(R.id.ivLoginLogo);
        tvLoginText = findViewById(R.id.tvLoginText);
        tvNewUser = findViewById(R.id.tvLoginNewuser);
        etLoginUserName = findViewById(R.id.etLoginUsername);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvLoginForgetPassword=findViewById(R.id.tvLoginForgotPassword);
        tvLoginForgetPassword=findViewById(R.id.tvLoginForgotPassword);
        tvLoginForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,ConfirmRegisterMobileNoActivity.class);
                startActivity(i);
            }
        });
        btnLoginSigninwithgoogle=findViewById(R.id.btnLoginwithgoogle);
        cbLogin = findViewById(R.id.cbLogin);
        cbLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
//GoogleSignInClient->GoogleSignIn->GoogleSignInClient->GoogleSignIn->GoogleSignInClient(name,id)
        googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
googleSignInClient= GoogleSignIn.getClient(LoginActivity.this,googleSignInOptions);
btnLoginSigninwithgoogle.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        signIn();//user defined:made by user,predefined:provied by system or package
    }


});
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etLoginUserName.getText().toString().isEmpty()) {
                    etLoginUserName.setError("Please enter UserName");
                } else if (etLoginUserName.getText().toString().length() < 8) {
                    etLoginUserName.setError("Please enter At least 8 characters");
                } else if (!etLoginUserName.getText().toString().matches(".*[A-Z].*")) {
                    etLoginUserName.setError("Please Enter At least one capital letter");
                } else if (!etLoginUserName.getText().toString().matches(".*[a-z].*")) {
                    etLoginUserName.setError("Please enter At least one small letter");
                } else if (etLoginPassword.getText().toString().isEmpty()) {
                    etLoginPassword.setError("Please enter Password");
                } else if (etLoginPassword.getText().toString().length() < 8) {
                    etLoginPassword.setError("Please Enter At least 8 characters");
                } else if (!etLoginPassword.getText().toString().matches(".*[A-Z].*")) {
                    etLoginPassword.setError("Please enter atleast one capital letter");
                } else if (!etLoginPassword.getText().toString().matches(".*[a-z].*")) {
                    etLoginPassword.setError("Please enter atleast one small letter");
                } else if (!etLoginPassword.getText().toString().matches(".*[@,#,$].*")) {
                    etLoginPassword.setError("Please enter any special symbol such as @,#,$");
                } else if (!etLoginPassword.getText().toString().matches(".*[0-9].*")) {
                    etLoginPassword.setError("Please enter At least one number");
                } else {
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Please Wait");
                    progressDialog.setMessage("Login is in progress");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    userLoginDetails();
                }
            }
        });
        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });
    }
//onCreate :All intializatio:first method when loginActivity start
        //onStart:start Activity
        //onResume:interact with activity
        //onPause:goes from login Activity to RegistrationActivity
        //onStop:interact on another pause
        //onRestart:goes from regisration to logim
        //onDestroy:Activity close

    private void signIn() {
Intent intent=googleSignInClient.getSignInIntent();
startActivityForResult(intent,999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)//selected otpion in googlesigninclient now stored in data
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==999)
        {
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                Intent intent=new Intent(LoginActivity.this,UserHomeActivity.class);
                startActivity(intent);
                finish();
            } catch (ApiException e) {
                Toast.makeText(LoginActivity.this,"Something went Wrong",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);//Check internet connectivity,perfrom one Action using coo
        registerReceiver(networkChangeListener,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListener);
    }

    private void userLoginDetails() {
        AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
        RequestParams params=new RequestParams();

        params.put("username",etLoginUserName.getText().toString());
        params.put("password",etLoginPassword.getText().toString());

        asyncHttpClient.post("http://192.168.172.27:80/farmerappAPI/userLogin.php",params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status=response.getString("success");
                    if(status.equals("1"))
                    {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Login Done Successfully",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(LoginActivity.this,UserHomeActivity.class);
                        startActivity(i);
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"InValid UserName and Password",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,"Server Error",Toast.LENGTH_SHORT).show();

            }
        });

    }
}