package com.usb;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import java.sql.Connection;

public class MainActivity extends Activity {

	UsbInterface usbInterface;
	Connection connection2;
	UsbDeviceConnection connection;
	 static final String ACTION_USB_PERMISSION = "com.example.usbhostapp.USB_PERMISSION";
	 UsbManager usbManager;
	 UsbDevice usbDevice;
	UsbEndpoint  endpoint,inEp,outEp;
	public boolean permissionRequested;
	
	public final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (ACTION_USB_PERMISSION.equals(action)) {
				synchronized (this) {
					UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
					boolean permissionGranted = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false);
					
					if (usbDevice != null && usbDevice.equals(device)) {
						if (permissionGranted) {
							// Permission granted, start your USB communication or launch the app
							// TODO: Implement your logic here
							} else {
							// Permission denied, stop the app or handle it accordingly
							// TODO: Implement your logic here
						}
					}
				}
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(usbReceiver, new IntentFilter(ACTION_USB_PERMISSION));
		checkForExistingDevice();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(usbReceiver);
	}
	
	public void checkForExistingDevice() {
		UsbDevice[] deviceList = usbManager.getDeviceList().values().toArray(new UsbDevice[0]);
		if (deviceList.length > 0) {
			usbDevice = deviceList[0];
			requestPermission();
			handl_usb();
			
			} else {
			// No USB device found
			// TODO: Implement your logic here
		}
	}
	
	private void requestPermission() {
		if (!permissionRequested) {
			permissionRequested = true;
			PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0,
			new Intent(ACTION_USB_PERMISSION), 0);
			usbManager.requestPermission(usbDevice, permissionIntent);
		}
	}
	
	
////////////////////////	
public void handl_usb()	
{//usbdevicebexists	
	connection = usbManager.openDevice(usbDevice);
	usbInterface = usbDevice.getInterface(0);
	connection.claimInterface(usbInterface, true);
	
	for (int i = 0; i < usbInterface.getEndpointCount(); i++) {
		 endpoint = usbInterface.getEndpoint(i);
		if (endpoint.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
			if ((endpoint.getDirection() & UsbConstants.USB_DIR_IN) != 0) {
				inEp = endpoint;
				} else {
				outEp= endpoint;
			}
		}
	}

byte[] des_rec=new byte[8];


int res=	connection.controlTransfer(
81,44,00,00,
des_rec,
des_rec.length,
5000);



	
}//handl	
}//end