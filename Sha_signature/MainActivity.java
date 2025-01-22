package com.example.sha_gen;



//package com.example.appfingerprint;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
TextView tx;
	String str="no sha";
    private static final String TAG = "AppFingerprint";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
tx=findViewById(R.id.tx);
        // Generate and log SHA-1 and SHA-256 fingerprints
        String sha1 = getAppFingerprint("SHA-1");
        String sha256 = getAppFingerprint("SHA-256");

        if (sha1 != null) {
            str="sha-1\n"+sha1;
			//Log.i(TAG, "SHA-1: " + sha1);
        } else {
            Log.e(TAG, "Failed to generate SHA-1 fingerprint.");
        }

        if (sha256 != null) {
           
		  str=str+"\n"+"sha-256\n"+sha256;
		    //Log.i(TAG, "SHA-256: " + sha256);
        } else {
            Log.e(TAG, "Failed to generate SHA-256 fingerprint.");
        }
		tx.setText(str);
    }

    /**
     * Retrieves the app fingerprint for the specified algorithm (SHA-1 or SHA-256) in hexadecimal format.
     *
     * @param algorithm The hash algorithm to use ("SHA-1" or "SHA-256").
     * @return The app fingerprint in hexadecimal format, or null if an error occurs.
     */
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
            String hex = Integer.toHexString(0xFF & b); // Convert each byte to hex
            if (hex.length() == 1) {
                hexString.append('0'); // Append leading zero if necessary
            }
            hexString.append(hex);
        }
        return hexString.toString().toUpperCase(); // Return uppercase hex
    }
}
