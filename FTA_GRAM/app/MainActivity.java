package slamtronic.chat.app;

//import android.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.net.PasswordAuthentication;

public class MainActivity extends AppCompatActivity {
    
	
	Boolean ino=true,sinin =true;
    private EditText param1EditText, param2EditText;
    private Button updateButton, readButton, resignupButton;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        param1EditText = findViewById(R.id.param1EditText);
        param2EditText = findViewById(R.id.param2EditText);
        updateButton = findViewById(R.id.updateButton);
        readButton = findViewById(R.id.readButton);
        resignupButton = findViewById(R.id.resignupButton);

       try{ mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();}catch(Exception e)
		{ Toast.makeText(this,e.toString(),Toast.LENGTH_LONG) .show();}
if(user==null){
	signup();
}
      
    databaseReference = FirebaseDatabase.getInstance().getReference("clients");//.child(user.getUid());

	readButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
				if(sinin) { signout(); 	            }	
				else{      signig() ;    }		
						
			}	});
			
			
			
            updateButton.setOnClickListener(new View.OnClickListener() {
					
					
                @Override
                public void onClick(View v) {
				if(sinin){  		
						
                    String param1 = param1EditText.getText().toString().trim();
                    String param2 = param2EditText.getText().toString().trim();

                    if (!param1.isEmpty() && !param2.isEmpty()) {
                        databaseReference.child("param1").setValue(param1);
                        databaseReference.child("param2").setValue(param2);
                        Toast.makeText(MainActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Please enter both param1 and param2", Toast.LENGTH_SHORT).show();
                    }}
						
					else Toast.makeText(MainActivity.this, "Please sinin", Toast.LENGTH_SHORT).show();
						
					//	
                }
            });
		
		resignupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signup();
                }
            });

           }
	
	
public void signup(){ startActivity(new Intent(MainActivity.this, AuthActivity.class));
				signout();
            }
	
public void signig(){try{  
		sinin=true;
		 readButton.setText("SIGN OUT"); 
						          	   
		   String em,pw;
			em= param1EditText.getText().toString().trim();
            pw = param2EditText.getText().toString().trim();
			 updateButton.setText("send");
		mAuth.signInWithEmailAndPassword( em,pw).
	addOnCompleteListener(MainActivity.this,task->{
		if(task.isSuccessful()){
		user= mAuth.getCurrentUser();
	Toast.makeText(this,"SUCCESS==>"+em +"/"+ pw+"<==",Toast.LENGTH_LONG).show();
			listenForDataUpdates();						
					
		}else Toast.makeText(this,em+" class" +pw,Toast.LENGTH_LONG).show();				
		param1EditText.setText("param1"); param2EditText.setText("Param2");		
	});}catch(Exception e){    Toast.makeText(this,e.toString() ,Toast.LENGTH_LONG).show();				 }
		
   	
						
}


public void signout(){
	
	mAuth.signOut();	sinin=false;readButton.setText( "SIGN IN");  
	param1EditText.setText("Email"); param2EditText.setText("Password");	
	Toast.makeText(this,"urebout" ,Toast.LENGTH_LONG).show();				
		
}

///////////__/////////////	
private void listenForDataUpdates() {
           

databaseReference.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
        // Called when a new user (uid) is added
        if (dataSnapshot.exists() && dataSnapshot.hasChild("param1")) {
            String uid = dataSnapshot.getKey(); // Get the UID
            String message = dataSnapshot.child("param1").getValue(String.class); // Get the message
          //  Log.d("FirebaseChat", "New message from UID: " + uid + " - Message: " + message);
        }
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
        // Called when an existing child's data is updated (e.g., message is updated)
        if (dataSnapshot.exists() && dataSnapshot.hasChild("param1")) {
            String uid = dataSnapshot.getKey(); // Get the UID
            String message = dataSnapshot.child("param1").getValue(String.class); // Get the updated message
           // Log.d("FirebaseChat", "Updated message from UID: " + uid + " - Message: " + message);
						disp(uid+":"+ message);
        }
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
        // Called when a child is deleted
        String uid = dataSnapshot.getKey(); // Get the UID of the removed child
       // Log.d("FirebaseChat", "Message removed for UID: " + uid);
					disp("message moved by "+uid);
    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
        // Called when a child is moved (not applicable in most cases for chat apps)
					disp("mover");
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        // Called if the listener fails
       // Log.e("FirebaseChat", "Failed to listen for updates: " + databaseError.getMessage());
					disp("cancelled");
    }
});

       }
				
				
	public void disp(String message){
//	param2EditText.setText(message);
	Toast.makeText(this,message,Toast.LENGTH_LONG).show();
		
	}	
		
		
         }
	
	
		
	

