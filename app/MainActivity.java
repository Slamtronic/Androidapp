package slam.data.base4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText param1EditText, param2EditText;
    private Button updateButton, readButton, resignupButton;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        param1EditText = findViewById(R.id.param1EditText);
        param2EditText = findViewById(R.id.param2EditText);
        updateButton = findViewById(R.id.updateButton);
        readButton = findViewById(R.id.readButton);
        resignupButton = findViewById(R.id.resignupButton);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            // Redirect to AuthActivity if not authenticated
            startActivity(new Intent(MainActivity.this, AuthActivity.class));
            finish();
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference("clients").child(user.getUid());

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String param1 = param1EditText.getText().toString().trim();
                    String param2 = param2EditText.getText().toString().trim();

                    if (!param1.isEmpty() && !param2.isEmpty()) {
                        databaseReference.child("param1").setValue(param1);
                        databaseReference.child("param2").setValue(param2);
                        Toast.makeText(MainActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Please enter both param1 and param2", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            readButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference allClientsRef = FirebaseDatabase.getInstance().getReference("clients");
                    allClientsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            StringBuilder result = new StringBuilder();
                            for (DataSnapshot clientSnapshot : snapshot.getChildren()) {
                                String param1 = clientSnapshot.child("param1").getValue(String.class);
                                String param2 = clientSnapshot.child("param2").getValue(String.class);
                                result.append("Client: ").append(clientSnapshot.getKey())
                                        .append(" - param1: ").append(param1)
                                        .append(", param2: ").append(param2).append("\n");
                            }
                            Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, "Failed to read data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            resignupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, AuthActivity.class));
                }
            });
        }
    }
}
