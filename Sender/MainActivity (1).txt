package fyhk.ghuh;

import android.content.Intent;
import android.net.Uri;
//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.net.URI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
	
	
	public void onLaunch(View v){ 
	//Intent intt=new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/@SLAMTRONIC")  );
	Intent intt=new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com")  );
	startActivity(intt);	
		  }
}