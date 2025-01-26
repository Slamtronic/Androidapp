package slamtronic.chat.app;

//import android.annotation.Nullable;
import android.widget.TextView;
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
   
   
    String str="\n",pw,em,para,psd="$$$";
	TextView chat;
	Boolean ino=true,sinin =false;
    private EditText param1EditText, param2EditText;
    private Button updateButton, readButton, resignupButton;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference0     , databaseReference;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        param1EditText = findViewById(R.id.param1EditText);
      chat = findViewById(R.id.chat);
        updateButton = findViewById(R.id.updateButton);
        readButton = findViewById(R.id.readButton);
        resignupButton = findViewById(R.id.resignupButton);
        updateButton.setText("تسجيل");
       try{ mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();}catch(Exception e)
		{ Toast.makeText(this,"user:"+e.toString(),Toast.LENGTH_LONG) .show();}

        if(user==null) {signup();
	 	resignupButton.setText("انخراط"); 
	//	readButton.setText(" تسجيل الدخول");
			param1EditText.setHint("البريد الالكتروني");// param2EditText.setHint("كلمة السر");
			} else {
		resignupButton.setText("سحب الانخراط"); 
		//	readButton.setText(" تسجيل الدخول");
			param1EditText.setHint("البريد الالكتروني");// param2EditText.setHint("كلمة السر");
			
			}
        
        
        
     databaseReference0 = FirebaseDatabase.getInstance().getReference("clients") ;
    databaseReference = FirebaseDatabase.getInstance().getReference("clients") .child(user.getUid());

/*	readButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
				if(sinin) { signout(); 	            }	
				else{      unsubs() ;    }		
						
			}	});
			*/
			
			
    updateButton.setOnClickListener(new View.OnClickListener() {
					
					
                @Override
                public void onClick(View v) {
					if(user!=null){ 
				if(!sinin){  		
					//	getParam();
						if(ino){   
                    em = param1EditText.getText().toString().trim();
						param1EditText.setHint("الرقم السري");ino=!ino;
						param1EditText.setText(null);
							return;
							
							}
						else{ pw = param1EditText.getText().toString().trim();
						     param1EditText.setText(null);
							ino=!ino;
							
							param1EditText.setText(null);
							param1EditText.setHint(" رسالتك");
							signig();
						
                   // String param2 = param2EditText.getText().toString().trim();

                    /*if (!em.isEmpty()&& !pw.isEmpty()) 
						{
                       // databaseReference.child("param1").setValue(em);
                        //databaseReference.child("param2").setValue(param2);
                        Toast.makeText(MainActivity.this, "paralink succes", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Please enter msg", Toast.LENGTH_SHORT).show();
                    }*/
								}
						}
						
					else { para=param1EditText.getText().toString().trim();
						
						databaseReference.child("param1").setValue(para);
						
					   // Toast.makeText(MainActivity.this, "msg sent", Toast.LENGTH_SHORT).show();
							}
						}else{
							updateButton.setText("يجب الانخراط");
						}
					//===========	
                }
            });
		
		
	readButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
			
				if(sinin)	
				{  signout();}//else	signig();
                }
            });
		
		
		
	resignupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
			
				if(user!=null&&sinin)	
				{  unsubs(); signup();}else	Toast.makeText(MainActivity.this, "سجل الدخول اولا او قم بمسح بينات التطبيق", Toast.LENGTH_SHORT).show();
                }
            });
		Intent  intt= getIntent();
		psd=intt.getStringExtra("psd");
		
		
          signout();
           }
	
	
public void signup(){ startActivity(new Intent(MainActivity.this, AuthActivity.class));
			signout();
            }
	
public void signig(){try{  
		
		 
						          	   
		 //  String em,pw;
		//	em= param1EditText.getText().toString().trim();
            //pw = param2EditText.getText().toString().trim();
		
			//param1EditText.setHint("البريد الالكتروني");
		
			
		mAuth.signInWithEmailAndPassword( em,pw).
	addOnCompleteListener(MainActivity.this,task->{
		if(task.isSuccessful()){
		user= mAuth.getCurrentUser();
	Toast.makeText(this,"==>نجاح التسجيل<==",Toast.LENGTH_LONG).show();
            //readButton.setText("تسجيل الخروج"); 
			listenForDataUpdates();
				updateButton.setText("ارسال");
				resignupButton.setText("تغير الحساب");
				readButton.setText("تسجيل الخروج");		
						updateButton.setText("<==");
						sinin=true;	
											
					param1EditText.setHint("اكتب رسالتك");
						param1EditText.setText(null);
		}else Toast.makeText(this,"اعد.التسجيل", Toast.LENGTH_LONG).show();				
		param1EditText.setHint(" البريد الالكتروني"); //param2EditText.setHint("التعليق");		
	});}catch(Exception e){    Toast.makeText(this,e.toString() ,Toast.LENGTH_LONG).show();				 }
		
   	
						
}


public void signout(){
  //	user.signOut();
		em=null;pw=null;
	 mAuth.signOut();
			sinin=false;//readButton.setText( "سحب الانخراط");  
	param1EditText.setHint("البريد الالكتروني"); //param2EditText.setHint("كلمة السر");
    	
	Toast.makeText(this,"الان سجل الدخول" ,Toast.LENGTH_LONG).show();
			updateButton.setText("سجل");
	resignupButton.setText("$$$$$$$"); 
		readButton.setText("تسجيل الدخول");
		//	listenForDataUpdates();
				
						sinin=false;
		
}

///////////__/////////////	
private void listenForDataUpdates() {
           

databaseReference0.addChildEventListener(new ChildEventListener() {
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
         //   String psd = dataSnapshot.child("pssodo").getValue(String.class); // Get the message	
					   
					    // Log.d("FirebaseChat", "Updated message from UID: " + uid + " - Message: " + message);
						str=str+ psd  +":"+ message+"\n";
						dispchat(str);
					//	disp(  ":"+ message);
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
					disp("moved");
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        // Called if the listener fails
       // Log.e("FirebaseChat", "Failed to listen for updates: " + databaseError.getMessage());
				//	disp(        "مشكل اتصال ");disp(  databaseError.getMessage());
    }
});

       }
				
				
	public void disp(String message){
//	param2EditText.setText(message);
	Toast.makeText(this,message,Toast.LENGTH_LONG).show();
		
	}public void oStop()	
		{      super.onStop();signout();
		 Toast.makeText(this,"مع السلامة",Toast.LENGTH_LONG).show();   }
	
public void onDestoy()	
		{      super.onDestroy();unsubs();
		 Toast.makeText(this,"حذف الانخراط",Toast.LENGTH_LONG).show();   }
	

	
	public void unsubs(){
		
	user= mAuth.getCurrentUser();
	if (user != null) {
    String uid = user.getUid(); // Get the current user's UID

    // First, remove the user's data from the database
    databaseReference0.child(uid).removeValue().addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
            //Log.d("DeleteUser", "User data removed from database.");

            // Then, delete the user from Firebase Authentication
         user.delete().addOnCompleteListener(deleteTask -> {
         if (deleteTask.isSuccessful()) {
								Toast.makeText(this,"تم الغاء الانخراط",Toast.LENGTH_LONG).show();   }
	
                    //Log.d("DeleteUser", "User account deleted from Firebase Authentication.");
                else {
									
						Toast.makeText(this,"اعد المحاولة",Toast.LENGTH_LONG).show();   
	
                    //Log.e("DeleteUser", "Failed to delete user account: " + deleteTask.getException().getMessage());
              }  
            });
        } else { Toast.makeText(this,"اعد المحاولة",Toast.LENGTH_LONG).show();   
           // Log.e("DeleteUser", "Failed to delete user data: " + task.getException().getMessage());
        }
    });
}///_____
		

		
}
		
	public void dispchat(String str){
		
		chat.setText(str);
	}
	public void getParam(){
		param1EditText.setHint("ادخل بريدك الالكتروني");em=param1EditText.getText().toString().trim();
		
		param1EditText.setHint("ادخل الرقم السري");pw=param1EditText.getText().toString().trim();
	}
 	
	
	
	
         }
	
	
		
	