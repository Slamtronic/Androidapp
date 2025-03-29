package android.ip.Adr;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {


TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		tx=findViewById(R.id.tx);
    tx.setText(  getIPAddress(MainActivity.this)  );
	;
}







	
	// Method to get the IP address
	public String getIPAddress(Context context) {
		try {
			// Check for active network type
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager != null) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					Network activeNetwork = connectivityManager.getActiveNetwork();
					if (activeNetwork != null) {
						NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
						if (networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
							return getMobileIPAddress();
						}
					}
					} else {
					// For devices below Android 6.0 (Marshmallow)
					NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
					if (activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
						return getMobileIPAddress();
					}
				}
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return "Unable to fetch IP address";
	}
	
	// Helper method to fetch the mobile network IP address
	private static String getMobileIPAddress() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();
				Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress inetAddress = addresses.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
						return inetAddress.getHostAddress();
					}
				}
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return "Unable to fetch mobile IP address";
	}
}
