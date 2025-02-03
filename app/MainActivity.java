package com.example.jarfetcher;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 100;
    private EditText groupIdInput, artifactIdInput;
    private Button searchButton;
    String jar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize PRDownloader
        PRDownloader.initialize(getApplicationContext(), PRDownloaderConfig.newBuilder().build());

        groupIdInput = findViewById(R.id.groupIdInput);
        artifactIdInput = findViewById(R.id.artifactIdInput);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(v -> {
            String groupId = groupIdInput.getText().toString().trim();
            String artifactId = artifactIdInput.getText().toString().trim();
            jar=groupId.replace(".","_");
				jar=jar+"-"+artifactId;
            if (groupId.isEmpty() || artifactId.isEmpty()) {
                Toast.makeText(this, "Please enter both Group ID and Artifact ID", Toast.LENGTH_SHORT).show();
                return;
            }

            if (checkStoragePermission()) {
                searchAndDownloadDependency(groupId, artifactId);
            } else {
                requestStoragePermission();
            }
        });
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void searchAndDownloadDependency(String groupId, String artifactId) {
        new Thread(() -> {
            try {
                // Convert Group ID to Maven path format (replace dots with slashes)
                String groupPath = groupId.replace(".", "/");

                // Construct the Maven Central Search API URL
                String searchUrl = "https://search.maven.org/solrsearch/select?q=g:" + groupId + "+AND+a:" + artifactId + "&rows=1&wt=json";

                // Make the HTTP request
                URL url = new URL(searchUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                // Parse the response
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray docs = jsonResponse.getJSONObject("response").getJSONArray("docs");

                if (docs.length() > 0) {
                    // Extract version and construct JAR URL
                    JSONObject dependencyInfo = docs.getJSONObject(0);
                    String version = dependencyInfo.getString("latestVersion");
                    String jarUrl = "https://repo1.maven.org/maven2/" + groupPath + "/" + artifactId + "/" + version + "/" + artifactId + "-" + version + ".jar";
                    jar=jar+"-"+version;
                    // Download the JAR file
                    downloadJarFile(jarUrl);
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Dependency not found", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Error occurred while searching", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void downloadJarFile(String jarUrl) {
        String outputPath = Environment.getExternalStorageDirectory().getAbsolutePath()
		 +"/"+".sketchware"+"/"+"libs"+"/"+"local_libs"+"/"+jar+"/"+ "classes.jar";

        PRDownloader.download(jarUrl, Environment.getExternalStorageDirectory().getAbsolutePath()
	+"/"+".sketchware"+"/"+"libs"+"/"+"local_libs"+"/"+jar+"/", "classes.jar")
                .build()
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "JAR file downloaded to: " + outputPath, Toast.LENGTH_LONG).show());
                    }

                    @Override
                    public void onError(com.downloader.Error error) {
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error downloading JAR file", Toast.LENGTH_SHORT).show());
                    }
                });
    }
}
