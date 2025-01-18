package slam.data.base1;



import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.FirebaseDatabaseComponent;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.io.IOException;
import java.util.EventListener;

public class MainActivity extends AppCompatActivity {
   
    TextView valueEditText2;
    private EditText nameEditText, valueEditText;
    private Button sendButton,recButton;

    // Reference to the Firebase Realtime Database
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Data"); // "Data" is the root node

        // Initialize UI elements
        nameEditText = findViewById(R.id.nameEditText);
        valueEditText = findViewById(R.id.valueEditText);
		
	valueEditText2 = findViewById(R.id.valueEditText2);
        sendButton = findViewById(R.id.sendButton);
       recButton = findViewById(R.id.recButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String value = valueEditText.getText().toString().trim();

                if (!name.isEmpty() && !value.isEmpty()) {
                    sendDataToFirebase(name, value);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter both name and value", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
       recButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       databaseReference=FirebaseDatabase.getInstance().getReference("Data");
                String name = nameEditText.getText().toString().trim();
                   if (!(databaseReference.child(name)).equals(null)) {
                      Toast.makeText(MainActivity.this, "trying to read", Toast.LENGTH_SHORT).show();
                    recDataToFirebase(databaseReference,name);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter  an existant key name a", Toast.LENGTH_SHORT).show();
                }
                    
        }});
        
     
        
        
        
    }//oncreate///////////////////////====//÷///////////

    private void sendDataToFirebase(String name, String value) {
        // Create a unique key under the "Data" node
        String key = databaseReference.push().getKey();

        if (key != null) {
            // Create a model or use a HashMap to store the name and value
            DataModel data = new DataModel(name, value);

            // Send the data to Firebase under the generated key
            databaseReference.child(key).setValue(data)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Data sent successfully", Toast.LENGTH_SHORT).show();
                            nameEditText.setText("");
                            valueEditText.setText("");
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to send data", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // Simple model class to hold name and value
    public static class DataModel {
        private String name;
        private String value;

        public DataModel() {
            // Default constructor required for Firebase
        }

        public DataModel(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
        
        
        
    }
    
   private void recDataToFirebase(DatabaseReference db,String name){
      // databaseReference.child(name);
        
        
   Query qr=  db.orderByChild("name").equalTo(name);
		qr.addListenerForSingleValueEvent(new  ValueEventListener() 
        {
         public void onDataChange(DataSnapshot ds){
             try { String val="";
				if(ds.exists()){ for(DataSnapshot dsnp:ds.getChildren()){ val=val+dsnp.child("value").getValue(String.class)+"\n"; }
							 valueEditText.setText(val);}
					else {valueEditText.setText(" no value attributed to this key "+ name);}}
					catch(Exception e){valueEditText2.setText(e.toString());}
         }
                
        public void onCancelled(DatabaseError e){
		valueEditText2.setText(e.toString());
         }
		
     }) ; }
        
      
      }
    
        
    
    
   
   
    

