package slamtronic.chat.app;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import slamtronic.chat.app.MainActivity;
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
	
	DatabaseReference databaseReference;
	
    public  String email,password,psd;
    protected EditText emailEditText,pssodo, passwordEditText;
    private Button signupButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

		
		
		
	
        pssodo = findViewById(R .id.pssodo);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signupButton = findViewById(R.id.signupButton);
        emailEditText.setHint("البريد");
		passwordEditText.setHint("كلمة السر");
   	signupButton.setText("انخراط")   ;
		pssodo.setHint("الاسم المستعار");
		
        mAuth = FirebaseAuth.getInstance();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();
		     	psd=pssodo.getText().toString().trim();
   
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)||  TextUtils.isEmpty(psd )) {
                    Toast.makeText(AuthActivity.this, "ها بن عمي عمر قاع المعلومات", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if user is already authenticated
                FirebaseUser user = mAuth.getCurrentUser();
					
                
					//else {
                    // Sign up new user
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
									new MainActivity().ino=false;
                            Toast.makeText(AuthActivity.this, "تم الانخراط  ", Toast.LENGTH_SHORT).show();
						//	databaseReference = FirebaseDatabase.getInstance().getReference("clients") .child(user.getUid());
					    	
						//	databaseReference.child("pssodo").setValue(psd);
							//	.
								
                            startActivity(new Intent(AuthActivity.this, MainActivity.class).   putExtra("psd",psd) );
                        } else {
                            Toast.makeText(AuthActivity.this, " خطاء" +"\n" +task.getException().getMessage(), Toast.LENGTH_LONG).show();
							
									
									
                        }
                    });
              //  }
            }
        });
    }
	public String gtem(){return email;}
    public String gtpw(){return password;}
	public String gtpsd(){return psd;}
	
	
 	
	
	
}
