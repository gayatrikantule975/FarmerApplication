package com.example.farmerapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class ForgetPasswordVerifyOTPActivity extends AppCompatActivity {
    EditText etInputOTP1, etInputOTP2,etInputOTP3,etInputOTP4, etInputOTP5, etInputOTP6;
    TextView tvMobileNumber;
    TextView tvResendOTP;
    AppCompatButton btnVerify;
    ProgressDialog progressDialog;
    private String strVerificationCode,strMobileNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpverification);
        etInputOTP1 = findViewById(R.id.etOTPVerification1);
        etInputOTP2 = findViewById(R.id.etOTPVerification2);
        etInputOTP3 = findViewById(R.id.etOTPVerification3);
        etInputOTP4 = findViewById(R.id.etOTPVerification4);
        etInputOTP5 = findViewById(R.id.etOTPVerification5);
        etInputOTP6 = findViewById(R.id.etOTPVerification6);
        tvMobileNumber=findViewById(R.id.tvOTPVerificationMobileNo);
        tvResendOTP = findViewById(R.id.tvOTPVerificationResentOTP);
        btnVerify = findViewById(R.id.btnOTPVerificationVerify);
        strVerificationCode = getIntent().getStringExtra("verificationcode");
        strMobileNo = getIntent().getStringExtra("mobileno");
        tvMobileNumber.setText("+91"+strMobileNo);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etInputOTP1.getText().toString().trim().isEmpty()||etInputOTP2.getText().toString().trim().isEmpty()||
                        etInputOTP3.getText().toString().trim().isEmpty()||etInputOTP4.getText().toString().trim().isEmpty()||
                        etInputOTP5.getText().toString().trim().isEmpty()||etInputOTP6.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(ForgetPasswordVerifyOTPActivity.this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
                }
                String otpCode = etInputOTP1.getText().toString()+etInputOTP2.getText().toString()+
                        etInputOTP3.getText().toString()+etInputOTP4.getText().toString()+
                        etInputOTP5.getText().toString()+etInputOTP6.getText().toString();

                if (strVerificationCode!=null){
                    progressDialog= new ProgressDialog(ForgetPasswordVerifyOTPActivity.this);
                    progressDialog.setTitle("Verifying OTp");
                    progressDialog.setMessage("Please Wait.....");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(strVerificationCode,otpCode);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Intent i=new Intent(ForgetPasswordVerifyOTPActivity.this,SetUpNewPasswordActivity.class);
                                i.putExtra("mobile",strMobileNo);
                                i.putExtra("verificationCode",strVerificationCode);
                                startActivity(i);

                               // userRegister();
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(ForgetPasswordVerifyOTPActivity.this, "Invalid OTP !! Enter Valid OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(ForgetPasswordVerifyOTPActivity.this, "OTP not received", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + strMobileNo, 60, TimeUnit.SECONDS,
                        ForgetPasswordVerifyOTPActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                progressDialog.dismiss();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                progressDialog.dismiss();
                                Toast.makeText(ForgetPasswordVerifyOTPActivity.this,"Verification Failed",Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationCode, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                strVerificationCode = newVerificationCode;
                            }
                        });
            }
        });

        setOTP();
    }



    private void setOTP() {
        etInputOTP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    etInputOTP2.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etInputOTP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    etInputOTP3.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etInputOTP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    etInputOTP4.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etInputOTP4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    etInputOTP5.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etInputOTP5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    etInputOTP6.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
    }
}