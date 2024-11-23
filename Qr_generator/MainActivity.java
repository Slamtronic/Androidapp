package gr.gen;

import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	//	Scanner sc= new Scanner(System.in);
	//	System.out.println("enter ard to conb to QR");
	//EditText ed=new EditText();
//	ed= findViewById(R.id.activity_main);
	//	String data= s;
		ImageView imageView = findViewById(R.id.imageView); // Your ImageView
		
		try {
			String data = "slamronic.ddns.net"; // The string to encode
			BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
			Bitmap bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 600, 600);
			imageView.setImageBitmap(bitmap); // Set the QR code bitmap to ImageView
			} catch (WriterException e) {
			e.printStackTrace();
		}
	}
}