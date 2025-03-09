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

        // Create User with Firebase Auth
        mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign-Up Success
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveUserToDatabase(user.getUid(), strFullName, strEmail);
                            Toast.makeText(SignUpActivity.this, "Account created successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            finish();
                        } else {
                            // Sign-Up Failed
                            Toast.makeText(SignUpActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        // Hide Progress Bar
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    // Save User Details to Firebase Realtime Database
    private void saveUserToDatabase(String userId, String name, String email) {
        User user = new User(name, email);
        mDatabase.child(userId).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "User details saved to database.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Failed to save user details: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Password Validation
    private boolean isPasswordValid(String password) {
        return password.length() >= 6 &&
                !password.equals(password.toLowerCase()) && // At least 1 uppercase letter
                password.matches(".*[!@#$%^&*()_+].*"); // At least 1 symbol
    }
    // Google Sign-In
    private void signInWithGoogle() {
        // Show Progress Bar
        progressBar.setVisibility(View.VISIBLE);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            // Hide Progress Bar on error
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Google Sign-In failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Google Sign-In Success
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveUserToDatabase(user.getUid(), user.getDisplayName(), user.getEmail());
                            Toast.makeText(SignUpActivity.this, "Google Sign-In successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            finish();
                        } else {
                            // Google Sign-In Failed
                            Toast.makeText(SignUpActivity.this, "Google Sign-In failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        // Hide Progress Bar
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    // User Model Class
    public static class User {
        public String name, email;

        public User() {}

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }
}
