package com.example.iugales;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iugales.databinding.ActivityRegisterBinding;
import com.example.iugales.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity  extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    // user inputs
    private EditText firstNameEt, lastNameEt, emailEt, password1Et;
    private CheckBox termsAndConditionsChkb, projectManagerChkb;

    private String TAG = "register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // user inputs
        firstNameEt             = view.findViewById(R.id.register_fistName_editText);
        lastNameEt              = view.findViewById(R.id.register_lastName_editText);
        emailEt                 = view.findViewById(R.id.register_email_editText);
        password1Et             = view.findViewById(R.id.register_password_editText);
        termsAndConditionsChkb  = view.findViewById(R.id.register_tesrmsAndConditions_chkB);
        projectManagerChkb      = view.findViewById(R.id.register_manager_chkB);

        binding.registerBtn.setOnClickListener(v -> {
            registerWithEmailAndPassword();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currUsr = mAuth.getCurrentUser();
        if (currUsr != null) {
            // if logged in go back to main and make them to the rest
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void registerWithEmailAndPassword() {
        String firstName    = firstNameEt.getText().toString();
        String lastName     = lastNameEt.getText().toString();
        String email        = emailEt.getText().toString();
        String password1    = password1Et.getText().toString();
        Boolean termsAndConditions  = termsAndConditionsChkb.isChecked();
        Boolean projectManager      = projectManagerChkb.isChecked();

        Boolean isError = false;

        // check data
        // terms and conditions checked
        if (!termsAndConditions) {
            termsAndConditionsChkb.setError(getString(R.string.errorTermsAndConditions));
            isError = true;
        }
        // first name no empty
        if (TextUtils.isEmpty(firstName)) {
            firstNameEt.setError(getString(R.string.errorFirstName));
            isError = true;
        }
        // last name no empty
        if (TextUtils.isEmpty(lastName)) {
            lastNameEt.setError(getString(R.string.errorLastName));
            isError = true;
        }
        // valid email
        if (!Util.isValidEmail(email)) {
            emailEt.setError(getString(R.string.errorEmail));
            isError = true;
        }
        // password larger then
        if (TextUtils.isEmpty(password1) || password1.length() <= 6) {
            password1Et.setError(getString(R.string.errorPassword));
            isError = true;
        }

        if (isError) {
            return;
        } else {
            // register
            mAuth.createUserWithEmailAndPassword(email, password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // ur in
                            String uid = task.getResult().getUser().getUid();
                            // put meta for user
                            Map<String, Object> newUsr = new HashMap<>();
                            newUsr.put("projectManager", projectManager);
                            db.collection("Users").document(uid).set(newUsr);
                            FirebaseUser curUsr = mAuth.getCurrentUser();
                            curUsr.sendEmailVerification();
                            // logged in, go to main and do the rest there
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // something went wrong
                            Log.d(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(
                                RegisterActivity.this,
                                getString(R.string.errorRegister),
                                Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                });
        }
    }
}