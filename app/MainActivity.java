
package my.maps;
//import com.google.android.gms.maps.OnMapReadyCallback;


import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import my.maps.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

  
    private GoogleMap myMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     try{    setContentView(R.layout.activity_main);}
    catch(Exception e){   Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();}
       SupportMapFragment mapFragment=(SupportMapFragment)
       getSupportFragmentManager().findFragmentById(R.id.map);
   if(mapFragment!=null){mapFragment.getMapAsync(this);}
        
        
}//oncreate

public void onMapReady(GoogleMap gm) {
	
 myMap=gm;
        
   LatLng  somewhere  =new LatLng(32.9427875,5.9754918) ;   
    myMap.addMarker(new MarkerOptions().position(somewhere).title("???")) ;     
    myMap.moveCamera(CameraUpdateFactory.newLatLng(somewhere)) ;     
        
}

    
    
}
