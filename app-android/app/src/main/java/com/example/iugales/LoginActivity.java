package com.example.iugales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iugales.databinding.ActivityLoginBinding;
import com.example.iugales.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity  extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private EditText emailEt, passwordEt;
    private Button loginBtn, forgetPasswordBtn, reSendConfirmEmailBtn;
    private TextView errorIndicator;

    private String TAG = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        emailEt     = view.findViewById(R.id.login_email_editText);
        passwordEt  = view.findViewById(R.id.login_password_editText);
        loginBtn                = view.findViewById(R.id.login_btn);
        forgetPasswordBtn       = view.findViewById(R.id.forgotPassword_btn);
        reSendConfirmEmailBtn   = view.findViewById(R.id.reSendConfirmEmail_btn);
        errorIndicator = view.findViewById(R.id.login_errorIndicator);

        // Check if user is signed in (non-null) and go back or stay.
        if (Util.isLoggedIn()) {
            // if logged in, and email is varied. Go to main
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        binding.loginBtn.setOnClickListener(v -> {
//            Intent intent = new Intent(this, HomePageActivity.class);
//            startActivity(intent);
            loginWithEmailAndPassword();
        });

        binding.createAccountBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        binding.forgotPasswordBtn.setOnClickListener(v -> {
            forgetPassword();
        });

        binding.reSendConfirmEmailBtn.setOnClickListener(v -> {
            reSendConfirmEmail();
        });
    }

    private void forgetPassword() {
        String email = emailEt.getText().toString();
        boolean isError = false;

        if( !Util.isValidEmail(email)) {
            emailEt.setError(getString(R.string.logErrorEmail));
            isError = true;
        }

        if (!isError) {
            mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        binding.loginErrorIndicator.setVisibility(View.INVISIBLE);
                        Toast.makeText(
                                getApplicationContext(),
                                getString(R.string.sendingEmailPassword),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
        }
    }

    //
    //binding.loginErrorIndicator.setText(R.string.logErrorEmailOrPassword);
    //binding.loginErrorIndicator.setVisibility(View.INVISIBLE);

    private void reSendConfirmEmail() {
        reSendConfirmEmailBtn.setVisibility(View.GONE);

        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();
        boolean isError = false;

        if( !Util.isValidEmail(email)) {
            emailEt.setError(getString(R.string.logErrorEmail));
            isError = true;
        }

        if (!isError) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser curUsr = mAuth.getCurrentUser();
                                if (!curUsr.isEmailVerified()) {
                                    // email not verified
                                    curUsr.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                reSendConfirmEmailBtn.setVisibility(View.GONE);
                                                Toast.makeText(
                                                        getApplicationContext(),
                                                        getString(R.string.sendingEmailConfirmEmail),
                                                        Toast.LENGTH_SHORT
                                                ).show();
                                            }
                                        });
                                    mAuth.signOut();
                                }
                            } else {
                                errorIndicator.setText(getString(R.string.logErrorEmailOrPassword));
                                binding.loginErrorIndicator.setText(R.string.logErrorEmailOrPassword);
                                binding.loginErrorIndicator.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }
    }

    private void loginWithEmailAndPassword() {

        binding.loginErrorIndicator.setVisibility(View.INVISIBLE);
        reSendConfirmEmailBtn.setVisibility(View.GONE);

        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();
        boolean isError = false;

        if( !Util.isValidEmail(email)) {
            emailEt.setError(getString(R.string.logErrorEmail));
            isError = true;
        }

        if (!isError) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser curUsr = mAuth.getCurrentUser();
                            if (!curUsr.isEmailVerified()) {
                                // email not verified
                                errorIndicator.setText(getString(R.string.logErrorEmailNotConfirm));
                                reSendConfirmEmailBtn.setVisibility(View.VISIBLE);
                                mAuth.signOut();
                            } else {
                                // email verified
                                // logged in, go to main and do the rest there
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            errorIndicator.setText(getString(R.string.logErrorEmailOrPassword));
                            binding.loginErrorIndicator.setText(R.string.logErrorEmailOrPassword);
                            binding.loginErrorIndicator.setVisibility(View.VISIBLE);
                        }
                    }
                });
        }
    }
}
