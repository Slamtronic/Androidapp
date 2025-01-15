package ip.local;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
//import java.util.jar.Manifest;
//import java.util.jar.Manifest;
import javax.xml.transform.Source;

public class MainActivity extends AppCompatActivity {
     
  public static final int PERMISSIONS_FINE_LOCATION=99;
    TextView mode;
    TextView tx,tx1,tx2 ;
    Switch gps,update;
    boolean isUpdate=false;
    private static final String TAG = "LocationActivity";
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx= findViewById(R.id.tx);
        
       tx1= findViewById(R.id.tx1);
        
       tx2= findViewById(R.id.tx2);
        mode=findViewById(R.id.mode);
        gps=findViewById(R.id.gps);
        update=findViewById(R.id.update);
        
        // Initialize the FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Create a LocationRequest
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(30000); // Update interval in milliseconds
        locationRequest.setFastestInterval(5000); // Fastest update interval
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY); // High accuracy
   gps.setOnClickListener(
    new View.OnClickListener() {
        
      public void onClick(View v) {
      	if(gps.isChecked()){  
             locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // High accuracy
             mode.setText(" Gps Mode");  }
          else{
             locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY); // High accuracy
             mode.setText(" network Mode");  }
          }          
                    
                
                
    }  
   );
       
     updateGPS(); 
 }//// start methods
  public void updateGPS() {
  fusedLocationClient=LocationServices.getFusedLocationProviderClient(MainActivity.this);
    if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
      fusedLocationClient.getLastLocation().addOnSuccessListener(this,new OnSuccessListener<Location>(){
          public void onSuccess(Location l) {
          	///
                        udateViews(l);
          } 
          	
          }
      );
    }else{
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_FINE_LOCATION);
        }
         else{ Toast.makeText(this,"verify build version",Toast.LENGTH_SHORT) .show();  }   
    }   	
  } // method
    
   
   public void onRequestPermissionsResult(int rcode,String[] permissions,int [] grantrs) {
   super.onRequestPermissionsResult(rcode,permissions,grantrs);
       switch(rcode){
          case PERMISSIONS_FINE_LOCATION:
            if(grantrs[0]==PackageManager.PERMISSION_GRANTED){updateGPS();}
            
            else{     Toast.makeText(this,"permission still not granted",Toast.LENGTH_SHORT) .show();    
                }
       }
   }
    public void udateViews(Location l) {
        /////
      tx.setText(String.valueOf(l.getLatitude()) ); 
      tx1.setText(String.valueOf(l.getLongitude()) ); 
        if(l.hasSpeed()){   
        tx2.setText(String.valueOf(l.getSpeed()) ); }
        else{tx2.setText(" Speed not available");}
        
        
        
        
    }
    

	
   }
   
   
    


    