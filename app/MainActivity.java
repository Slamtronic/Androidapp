package qr.share.it;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.core.content.FileProvider;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
	
	private static final int STORAGE_PERMISSION_CODE = 100;
	EditText edt;
	Button btn;
	ImageView imageView;
	Bitmap bitmap; // Declare the bitmap as a class member
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imageView = findViewById(R.id.imageView); // Your ImageView
		btn = findViewById(R.id.btn);
		edt = findViewById(R.id.etd);
		edt.setText("Enter string to convert to QR code");
		
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					String data = edt.getText().toString().trim();
					BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
					bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 600, 600);
					imageView.setImageBitmap(bitmap); // Set the QR code bitmap to ImageView
					} catch (WriterException e) {
					e.printStackTrace();
				}
			}
		});
		
		imageView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if (bitmap != null) {
					checkStoragePermission();
					} else {
					Toast.makeText(MainActivity.this, "QR code not generated yet", Toast.LENGTH_SHORT).show();
				}
				return true; // Return true to indicate the event was handled
			}
		});
	}
	
	private void checkStoragePermission() {
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
		!= PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this,
			new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
			STORAGE_PERMISSION_CODE);
			} else {
			shareQRCode(bitmap);
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == STORAGE_PERMISSION_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				shareQRCode(bitmap);
				} else {
				Toast.makeText(this, "Storage permission is required to share the QR code", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private void shareQRCode(Bitmap bitmap) {
		// Save the bitmap to a file
		try {
			File cachePath = new File(getCacheDir(), "images");
			cachePath.mkdirs(); // Create the directory
			FileOutputStream stream = new FileOutputStream(cachePath + "/qr_code.png");
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		 	stream.close();
			
			// Get the URI for the image
			File imagePath = new File(getCacheDir(), "images/qr_code.png");
		//	Uri contentUri = Uri.fromFile(imagePath);
			Uri contentUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", imagePath);
			// Create the share intent
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.setType("image/png");
			shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
			shareIntent.putExtra(Intent.EXTRA_TEXT, "Here is my QR code!");
			startActivity(Intent.createChooser(shareIntent, "Share QR Code"));
			
			} 
			catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "Error sharing QR code", Toast.LENGTH_SHORT).show();
			edt.setText(e.toString());
		}
	}
}