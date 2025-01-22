
package slamtronic.bus.base;

import android.Manifest;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import androidx.annotation.NonNull;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import android.widget.Toast;
import androidx.annotation.IntegerRes;
import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
 
	String  str="",strr,sha1,sha256,sig1,sig256;
	
	private static final int PERMISSION_REQUEST_CODE= 1;
	
		
	
	FileWriter wr;
	File dir,file;
    private static final String TAG = "AppSignature";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		TextView textView = findViewById(R.id.textView);
                

        // Function call to display SHA-1 and SHA-256
		
	 sha1 = getAppFingerprint("SHA-1");
     sha256 = getAppFingerprint("SHA-256");
		
      getAppSignatures();
			 if(checkPermission()){  
			uploadfile();
		textView.setText(strr);
			
			}
			else{requestPermission();}
			
			
			
		
    }

    private void getAppSignatures() {
		
        try {
			
			
            // Get the package info containing the signatures
            PackageInfo packageInfo = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNING_CERTIFICATES // Use GET_SIGNATURES for older APIs
            );

            // Loop through each signature in the app's package
            for (Signature signature : packageInfo.signingInfo.getApkContentsSigners()) {
                // Calculate and log SHA-1
                sig1 = getSignatureHash(signature, "SHA-1");
                Log.d(TAG, "SHA-1: " + sig1);

                // Calculate and log SHA-256
                sig256 = getSignatureHash(signature, "SHA-256");
                Log.d(TAG, "SHA-256: " + sig256);

                // Optional: Display in a TextView
                
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Package not found", e);
        }
	
		
    }

    private String getSignatureHash(Signature signature, String algorithm) {
		
        try {
            // Get the MessageDigest instance for the specified algorithm
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(signature.toByteArray());

            // Convert the byte array to a Base64 string
			byte[] res=Base64.encode(messageDigest.digest(), Base64.NO_WRAP);
			for(byte bt:res){
		//str=str+	":"+Integer.toHexString(bt);
			str=str+":"+bt;	
			}
			
			
			return str;
           // return Base64.encodeToString(messageDigest.digest(), Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Algorithm not found: " + algorithm, e);
            return null;
        }
		

} 
	
	private String getAppFingerprint(String algorithm) {
        try {
            // Get the app's package name
            String packageName = getPackageName();

            // Retrieve the package info with signing certificates
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES);

            // Get the signing certificates
            Signature[] signatures = packageInfo.signingInfo.getApkContentsSigners();
            if (signatures == null || signatures.length == 0) {
                return null; // No signatures found
            }

            // Use the first signature to compute the fingerprint
            byte[] cert = signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hashBytes = md.digest(cert);

            // Convert the hash bytes to a hexadecimal string
            return bytesToHex(hashBytes);

        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null; // Handle errors
        }
    }

    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @param bytes The byte array to convert.
     * @return The resulting hexadecimal string.
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = ":"+Integer.toHexString(0xFF & b); // Convert each byte to hex
            if (hex.length() == 1) {
                hexString.append('0'); // Append leading zero if necessary
            }
            hexString.append(hex);
        }
        return hexString.toString().toUpperCase(); // Return uppercase hex
    }


	
	
	
	
	
public void uploadfile() {
		
		 strr="SIG-1 \n"+sig1+"\nSIG-256 \n"+sig256
		+"\n\nSha-1 \n"+sha1+"\nSha-256 \n"+sha256;

			
			try{  
		File dir= new File(Environment.getExternalStorageDirectory(), "AndroidIDEProjects/Firebase/SHADir");
//	File dir= new File(getFilesDir(),"shadir");

			if(! dir.exists()){dir.mkdirs();creafile(dir);}
		else{ 
			creafile(dir);		}
								
		}
		catch(Exception e){
			Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
		}
			
			
			
}
	

public void creafile(File dir) {
		
 File file = new File(dir, "sha.txt");
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(strr.getBytes());
			fos.flush();
			Toast.makeText(this, "File created: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
			} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(this, "Error creating file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
		}
}	
	
	private void requestPermission() {
		ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
		
}
	
private boolean checkPermission() {
		int result= ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return ( result== PackageManager.PERMISSION_GRANTED) ;
}
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == PERMISSION_REQUEST_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				uploadfile();
				} else {
				Toast.makeText(this, "Storage permission is required", Toast.LENGTH_LONG).show();
			}
		}
	}
}