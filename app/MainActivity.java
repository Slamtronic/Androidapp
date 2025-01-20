package gr.gen;

import android.view.View;
import android.widget.Button;
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

EditText edt;
Button btn;

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
		btn= findViewById(R.id.btn);
		edt=findViewById(R.id.etd);
		edt.setText("enter string to convert\n to QR code"   );
		btn.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				try {
					//String data = "slamronic.ddns.net"; // The string to encode
					String data = edt.getText().toString().trim();
					BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
					Bitmap bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 600, 600);
					imageView.setImageBitmap(bitmap); // Set the QR code bitmap to ImageView
					} catch (WriterException e) {
					e.printStackTrace();
				}
				
				}
			
			});
		
	}
}