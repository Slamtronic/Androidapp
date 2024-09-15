package coo.file;
//import java.util.jar.Manifest;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import android.content.Intent;
public class MainActivity extends AppCompatActivity {
	
	private ListView listView;
	private List<String> fileList;
	private String currentPath;
	
	private static final int PERMISSION_REQUEST_CODE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listView = findViewById(R.id.listView);
		fileList = new ArrayList<>();
		if(perm()){
		if (checkPermission()) {
			loadFileList(Environment.getExternalStorageDirectory().getPath().toString());
			} else {
			requestPermission();
		}
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Toast.makeText(MainActivity.this,"$$$$",Toast.LENGTH_SHORT).show();	
				String selectedFile = fileList.get(position);
				String newPath = currentPath + File.separator + selectedFile;
				File clickedFile = new File(newPath);
				
				if (clickedFile.isDirectory()) {
					loadFileList(newPath);
					} else {
					Toast.makeText(MainActivity.this, "Selected file: " + selectedFile, Toast.LENGTH_SHORT).show();
				}
			}
		});}
		
		else req_perm();
	}
	
	private void loadFileList(String path) {
		currentPath = path;
		setTitle("File Manager: " + currentPath);
		
		fileList.clear();
		
		File root = new File(path);
		File[] files = root.listFiles();
		String s="";
		if (files != null) {
			for (File file : files) {
				fileList.add(file.getName());s=s+file.getName()+" \n";
			}
			//Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
		}else setTitle("dir empty");
		
		Collections.sort(fileList);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileList);
		listView.setAdapter(adapter);
	}
	
	private boolean checkPermission() {
		int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
		return result == PackageManager.PERMISSION_GRANTED;
	}
	
	private void requestPermission() {
		ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
}


public void req_perm(){
	if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,READ_EXTERNAL_STORAGE)){//tost("please set permission\n from seting");
Toast.makeText(this,"perm from setting",Toast.LENGTH_LONG).show();		}
	else{ActivityCompat.requestPermissions(MainActivity.this,new String[]{READ_EXTERNAL_STORAGE},111);}
	
	
	
}
public boolean perm() {
int	result1 = ContextCompat.checkSelfPermission(MainActivity.this,READ_EXTERNAL_STORAGE);
	if(result1==PackageManager.PERMISSION_GRANTED){return true;}
	else{ return false;}
}

}