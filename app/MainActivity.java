package example.zxing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Start QR code scanner
		new IntentIntegrator(this).initiateScan();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		// Handle the result of the scan
		IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		if (result != null) {
			if (result.getContents() != null) {
				// QR code was successfully scanned
				String scannedData = result.getContents();
				Toast.makeText(this, "Scanned: " + scannedData, Toast.LENGTH_LONG).show();
				} else {
				// Scan was cancelled
				Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show();
			}
			} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
}