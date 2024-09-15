package com.example.farmerapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class RegistrationActivity extends AppCompatActivity {
ImageView ivRegistrationLogo;
TextView tvRegistrationText;
EditText etRegistrationName,etRegistrationMobileNo,etRegistrationEmailId,etRegistrationUserName,etRegistrationPassword;
Button btnRgistration;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ivRegistrationLogo = findViewById(R.id.ivRegistrationLogo);
        tvRegistrationText = findViewById(R.id.tvRegistationText);
        etRegistrationName = findViewById(R.id.etRegistrationName);
        etRegistrationMobileNo = findViewById(R.id.etRegistrationMobileNo);
        etRegistrationEmailId = findViewById(R.id.etRegistrationEmailId);
        etRegistrationUserName = findViewById(R.id.etRegistrationUserName);
        etRegistrationPassword = findViewById(R.id.etRegistrationPassword);
        btnRgistration = findViewById(R.id.btnRegistration);
        btnRgistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etRegistrationName.getText().toString().isEmpty()) {
                    etRegistrationName.setError("Please enter UserName");
                } else if (etRegistrationName.getText().toString().length() < 8) {
                    etRegistrationName.setError("Please enter At least 8 characters");
                } else if (!etRegistrationName.getText().toString().matches(".*[A-Z].*")) {
                    etRegistrationName.setError("Please Enter At least one capital letter");
                } else if (!etRegistrationName.getText().toString().matches(".*[a-z].*")) {
                    etRegistrationName.setError("Please enter At least one small letter");
                } else if (etRegistrationMobileNo.getText().toString().isEmpty()) {
                    etRegistrationMobileNo.setError("Please enter Mobile No.");
                } else if (etRegistrationMobileNo.getText().toString().length() < 10) {
                    etRegistrationMobileNo.setError("Please enter At least 10 numbers");
                } else if (etRegistrationEmailId.getText().toString().isEmpty()) {
                    etRegistrationEmailId.setError("Please enter EmailId");
                } else if (etRegistrationEmailId.getText().toString().length() < 8) {
                    etRegistrationEmailId.setError("Please enter At least 8 characters");
                } else if (!etRegistrationEmailId.getText().toString().matches(".*[@].*")) {
                    etRegistrationEmailId.setError("Please enter valid Email Address");
                } else if (!etRegistrationEmailId.getText().toString().contains(".com")) {
                    etRegistrationEmailId.setError("Please enter valid Email Address");
                } else if (etRegistrationUserName.getText().toString().isEmpty()) {
                    etRegistrationUserName.setError("Please enter Password");
                } else if (etRegistrationUserName.getText().toString().length() < 8) {
                    etRegistrationUserName.setError("Please Enter At least 8 characters");
                } else if (!etRegistrationUserName.getText().toString().matches(".*[A-Z].*")) {
                    etRegistrationUserName.setError("Please enter atleast one small letter");
                } else if (!etRegistrationUserName.getText().toString().matches(".*[a-z].*")) {
                    etRegistrationUserName.setError("Please enter atleast one small letter");
                } else if (etRegistrationPassword.getText().toString().isEmpty()) {
                    etRegistrationPassword.setError("Please enter Password");
                } else if (etRegistrationPassword.getText().toString().length() < 8) {
                    etRegistrationPassword.setError("Please Enter At least 8 characters");
                } else if (!etRegistrationPassword.getText().toString().matches(".*[A-Z].*")) {
                    etRegistrationPassword.setError("Please enter atleast one small letter");
                } else if (!etRegistrationPassword.getText().toString().matches(".*[a-z].*")) {
                    etRegistrationPassword.setError("Please enter atleast one small letter");
                } else if (!etRegistrationPassword.getText().toString().matches(".*[@,#,$].*")) {
                    etRegistrationPassword.setError("Please enter any special symbol such as @,#,$");
                } else if (!etRegistrationPassword.getText().toString().matches(".*[0-9].*")) {
                    etRegistrationPassword.setError("Please enter At least one number");
                } else {
                    progressDialog = new ProgressDialog(RegistrationActivity.this);
                    progressDialog.setTitle("Please Wait...");
                    progressDialog.setMessage("Registration is in process");
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.show();
                    // userRegistrationDetails();
//Help to verify phone number by send otp


                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    "+91"+etRegistrationMobileNo.getText().toString(),//on which mobile no otp received
                                    60,//in how many seconds otp to be sent
                                    TimeUnit.SECONDS,//mention time unit
                                    RegistrationActivity.this,//Current java class
                                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks()//callback:verification complete or not
                                    {
                                        @Override
                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                            progressDialog.dismiss();
                                            Toast.makeText(RegistrationActivity.this,"Verification Successful",Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onVerificationFailed(@NonNull FirebaseException e) {
progressDialog.dismiss();
Toast.makeText(RegistrationActivity.this,"Verification Failed",Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCodeSent(@NonNull String verificationCode, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)//1 st arg:verificationCode:firebase sended free otp received here
                                        {
                                            Intent i=new Intent(RegistrationActivity.this,OTPVerificationActivity.class);
                                            i.putExtra("verificationcode",verificationCode);//key:string and value
                                            i.putExtra("name",etRegistrationName.getText().toString());//To transfer recieved code all user info to verificationActivity,in verification Activity there is no edittext so we sent this data from here and recieve there
                                            i.putExtra("mobileno",etRegistrationMobileNo.getText().toString());
                                            i.putExtra("emailid",etRegistrationEmailId.getText().toString());
                                            i.putExtra("username",etRegistrationUserName.getText().toString());
                                            i.putExtra("password",etRegistrationPassword.getText().toString());
                                            startActivity(i);
                                        }
                                    }
                            );
                }
            }
        });
    }



}

