package slamtronic.chat.app;

//import android.annotation.Nullable;
import android.graphics.Color;
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
   
   
    String str="\n",pw,em,para,psd="xxx";
	TextView chat;
	Boolean ino=true,sinin =false;
    private EditText param1EditText, param2EditText;
    private Button updateButton, readButton, resignupButton,title;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference0     , databaseReference;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     try{     setContentView(R.layout.activity_main);}
		catch(Exception e){Toast.makeText(this,"AAA\n"+e.toString(),Toast.LENGTH_LONG).show();}

        param1EditText = findViewById(R.id.param1EditText);
      chat = findViewById(R.id.chat);
        updateButton = findViewById(R.id.updateButton);
        readButton = findViewById(R.id.readButton);
        resignupButton = findViewById(R.id.resignupButton);
    	title= findViewById(R.id.title);
        updateButton.setText("تسجيل");
       try{ mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();}catch(Exception e)
		{ Toast.makeText(this,"user:"+e.toString(),Toast.LENGTH_LONG) .show();}
		
	   
	 //try{    getRef();}catch(Exception e){}
		
		
		try{ 

        if(user==null) {  signup("a"); 
	 	resignupButton.setText("انخراط"); 
	//	readButton.setText(" تسجيل الدخول");
			param1EditText.setHint("سجل الدخول");// param2EditText.setHint("كلمة السر");
			} else {if (em==null){  getparm(); 
					//signup("b");
					}
			       // else getRef();
			
			
			
			
		resignupButton.setText("حذف الحساب");
		//	readButton.setText(" تسجيل الدخول");
			param1EditText.setHint("سجل الدخول");// param2EditText.setHint("كلمة السر");
			
			}} catch(Exception e){ Toast.makeText(this,"BBB\n"+e.toString(),Toast.LENGTH_LONG).show(); }
        
        
        
     

			
			
			
    updateButton.setOnClickListener(new View.OnClickListener() {
					
					
                @Override
                public void onClick(View v) {
			    if(user!=null){ 
						
				  if(em!=null){ 		
				   if(!sinin){signig();}
					else { para=param1EditText.getText().toString().trim();
						databaseReference.child("param1").setValue(para);
						param1EditText.setText(null);}}
					else  signup("b");	
				
				 }else{
							updateButton.setText("يجب الانخراط");
						}
					//===========	
                }
            });
		
		
			readButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
				if(sinin) { signout();  }
				else      info()    ;		
						
						
			}	});
	 
		
		
		
	resignupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
			
				if(user!=null&&sinin)	
				{  unsubs(); signup("a");}
				else { 	Toast.makeText(MainActivity.this, "سجل الدخول", Toast.LENGTH_SHORT).show();}
                if(user==null){  signup("a");  }
			    }});
			//if(user==null||em.equals(null)||pw.equals(null)||psd.equals(null))
            	//{  	signup(); }
          
           }
	
public void getparm()	{   Intent  intt= getIntent();
	psd=intt.getStringExtra("psd");
	pw=intt.getStringExtra("pasword");
	em=intt.getStringExtra("email");}
	
public void getRef(){ 	databaseReference0 = FirebaseDatabase.getInstance().getReference("clients") ;
                     databaseReference = FirebaseDatabase.getInstance().getReference("clients") .child(user.getUid());
		}
	public void info() {
	Toast.makeText(this," developper  \nslamharb@gmail.com\n",Toast.LENGTH_LONG).show();
	}
	
public void signup(String st){ 
		startActivity(new Intent(MainActivity.this, AuthActivity.class).putExtra("st",st));
	getparm();  
	//	
			
            }
	
public void signig(){try{  
		
		 
						          	   
			
		mAuth.signInWithEmailAndPassword( em,pw).
	addOnCompleteListener(MainActivity.this,task->{
		if(task.isSuccessful()){
		user= mAuth.getCurrentUser();
			getRef();			
	Toast.makeText(this,"==>نجاح التسجيل<==",Toast.LENGTH_LONG).show();
		 		
			listenForDataUpdates();
				//	param1EditText.setTextColor(Color.parseColor( "#ff0000"));
               // databaseReference.child("param1").setValue(psd+"دخو ل "); 
				databaseReference.child("param1").setValue(psd+"دخول "); 	
			//	param1EditText.setTextColor(Color.parseColor( "#000000"));
			param1EditText.setHint("دردشة");
						updateButton.setText("ارسال");
				resignupButton.setText("حذف الحساب");
				readButton.setText("تسجيل الخروج");		
				updateButton.setText("<==");
				title.setText("upload to FTA");		
						sinin=true;	
											
					param1EditText.setHint("اكتب رسالتك");
						param1EditText.setText(null);
		}else { Toast.makeText(this,"اعد.التسجيل", Toast.LENGTH_LONG).show();				
		param1EditText.setHint(" البريد الالكتروني");} //param2EditText.setHint("التعليق");		
	});}catch(Exception e){    Toast.makeText(this,e+"%%"+em.toString() ,Toast.LENGTH_LONG).show();				 }
		
   	
						
}


public void signout(){
  
		
	 mAuth.signOut();
			sinin=false;//readButton.setText( "سحب الانخراط");  
	param1EditText.setHint("سجل اولا"); //param2EditText.setHint("كلمة السر");
    	
	Toast.makeText(this,"الان سجل الدخول" ,Toast.LENGTH_LONG).show();
	   updateButton.setText("سجل");
   	resignupButton.setText("حسابي"); 
   	readButton.setText( "info");
	   title.setText("FTA CHAT ROOM");
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
	      str=psd+"دخول"+"==================>\n";
					dispchat(str);
						
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
				//	disp("message moved by "+uid);
					str=psd+"خروج"+"<==================\n";
					dispchat(str);
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
public void sharFile(View v){
		if(sinin&&em!=null){  upload() ;}
		else{    	
	Toast.makeText(this,"سجل الدخول",Toast.LENGTH_LONG).show(); }
		}
	
	
public void upload(){
	
		
		
}	
	
}	
	
	
	
         
	
	
		
	