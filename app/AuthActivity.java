package slam.data.base4;

import slam.data.base4.MainActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.firebaseauthdemo.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button signupButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signupButton = findViewById(R.id.signupButton);

        mAuth = FirebaseAuth.getInstance();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(AuthActivity.this, "Please enter Email and Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if user is already authenticated
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    // Update Email and Password
                    user.updateEmail(email).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.updatePassword(password).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Toast.makeText(AuthActivity.this, "Email and Password updated", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AuthActivity.this, MainActivity.class));
                                } else {
                                    Toast.makeText(AuthActivity.this, "Failed to update Password", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(AuthActivity.this, "Failed to update Email", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Sign up new user
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(AuthActivity.this, "Sign-up successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AuthActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(AuthActivity.this, "Sign-up failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
							signupButton.setText(  task.getException().getMessage().toString());	
									
									
                        }
                    });
                }
            }
        });
    }
}
