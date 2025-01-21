package file.stor;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
	
	private static final int STORAGE_PERMISSION_CODE = 100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btnCreateFile = findViewById(R.id.btnCreateFile);
		btnCreateFile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkStoragePermission();
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
			createFile();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == STORAGE_PERMISSION_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				createFile();
				} else {
				Toast.makeText(this, "Storage permission is required", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private void createFile() {
		File directory = new File(Environment.getExternalStorageDirectory(), "MyAppDir");
//	File directory=new File(Environment.getStorageDirectory(),"mudir");

		if (!directory.exists()) {
			directory.mkdirs(); // Create the directory
		}
		
		File file = new File(directory, "example.txt");
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write("holloworld".getBytes());
			fos.flush();
			Toast.makeText(this, "File created: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
			} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(this, "Error creating file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
}