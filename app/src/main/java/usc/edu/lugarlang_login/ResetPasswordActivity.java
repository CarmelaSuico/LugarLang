package usc.edu.lugarlang_login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText etNewPassword, etVerifyPassword;
    private Button btnCancel, btnConfirm;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Get username from intent
        username = getIntent().getStringExtra("USERNAME");

        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        // Initialize views
        etNewPassword = findViewById(R.id.etNewPassword);
        etVerifyPassword = findViewById(R.id.etVerifyPassword);
        btnCancel = findViewById(R.id.btnCancel);
        btnConfirm = findViewById(R.id.btnConfirm);
        progressBar = findViewById(R.id.progressBar);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String newPassword = etNewPassword.getText().toString().trim();
        String verifyPassword = etVerifyPassword.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(newPassword)) {
            etNewPassword.setError("New password is required");
            return;
        }

        if (TextUtils.isEmpty(verifyPassword)) {
            etVerifyPassword.setError("Please verify your password");
            return;
        }

        if (!newPassword.equals(verifyPassword)) {
            etVerifyPassword.setError("Passwords do not match");
            return;
        }

        if (newPassword.length() < 6) {
            etNewPassword.setError("Password must be at least 6 characters");
            return;
        }

        // Show progress
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        btnConfirm.setEnabled(false);

        // Update password in Firebase
        Map<String, Object> updates = new HashMap<>();
        updates.put("password", newPassword);

        databaseReference.child(username).updateChildren(updates).addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                btnConfirm.setEnabled(true);

                if (task.isSuccessful()) {
                    Toast.makeText(ResetPasswordActivity.this, "Password reset successful!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Failed to reset password: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}