package com.example.shopfinity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    // Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    // Google Sign-In
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    // UI References
    private EditText fullName, email, password, confirmPassword;
    private CheckBox termsCheckbox;
    private Button btnCreateAccount, loginBtn; // Updated to Button
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        // Initialize Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Web Client ID
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Initialize UI Components
        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        termsCheckbox = findViewById(R.id.termsCheckbox);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        loginBtn = findViewById(R.id.loginBtn); // Updated to Button
        progressBar = findViewById(R.id.progressBar);

        // Set Click Listeners
        btnCreateAccount.setOnClickListener(v -> createAccount());

        // Handle Login Button Click
        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Handle Password Visibility Toggle
        setupPasswordVisibilityToggle(password);
        setupPasswordVisibilityToggle(confirmPassword);

        // Google Sign-In Button
        findViewById(R.id.googleSignIn).setOnClickListener(v -> signInWithGoogle());
    }
    // Toggle Password Visibility
    @SuppressLint("ClickableViewAccessibility")
    private void setupPasswordVisibilityToggle(EditText editText) {
        editText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    // Toggle password visibility
                    if (editText.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                        editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_off, 0);
                    } else {
                        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0);
                    }
                    return true;
                }
            }
            return false;
        });
    }
    // Create Account with Email/Password
    private void createAccount() {
        String strFullName = fullName.getText().toString().trim();
        String strEmail = email.getText().toString().trim();
        String strPassword = password.getText().toString().trim();
        String strConfirmPassword = confirmPassword.getText().toString().trim();

        // Validate Inputs
        if (TextUtils.isEmpty(strFullName)) {
            fullName.setError("Full Name is required.");
            return;
        }

        if (TextUtils.isEmpty(strEmail) || !Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            email.setError("Valid email is required.");
            return;
        }

        if (TextUtils.isEmpty(strPassword) || !isPasswordValid(strPassword)) {
            password.setError("Password must be at least 6 characters with 1 uppercase letter and 1 symbol.");
            return;
        }

        if (!strPassword.equals(strConfirmPassword)) {
            confirmPassword.setError("Passwords do not match.");
            return;
        }

        if (!termsCheckbox.isChecked()) {
            Toast.makeText(this, "Please accept the terms and conditions.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show Progress Bar
        progressBar.setVisibility(View.VISIBLE);