package coj.mgyui;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import java.net.URL;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		Intent recInt = getIntent();
		Uri uri=recInt.getData();
		URL webPage=null;
		if(uri !=null){
		try {
			webPage=new URL(uri.getScheme(),uri.getHost(),uri.getPath());
			}
		catch (Exception e){
			Toast.makeText(this,"intent exception "+e.toString(),Toast.LENGTH_LONG).show();
		  	}
		WebView wv= findViewById(R.id.webview);
		wv.setWebViewClient(new WebViewClient());	  
	    wv.loadUrl(webPage.toString());		  
			  
	}	
    }
}