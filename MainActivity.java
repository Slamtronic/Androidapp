package Usb.thread;

import android.os.Bundle;
import android.content.Intent;
import android.app.PendingIntent;
import android.content.Context;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
	
	private TextView counterTextView;
	private EditText userInputTextView;
	private TextView reqdata;
	private int counter = 0;
	private boolean wait=true;
    String DAta="no data";
	UsbManager usbManager;
	PendingIntent pendingIntent;
	UsbDeviceConnection connection;
	UsbInterface usbInterface;
	UsbEndpoint inEp=null;
	UsbEndpoint outEp=null;
	UsbDevice device;
	String sval,data,exrow,state,s,stage,newData;
	public String hax(int i){i=i&0xff; if(i<17){ return "0" +Integer.toHexString(i);}else{return Integer.toHexString(i);} }
	private class CoThread implements Runnable {
	@Override
		public void run() {
			while (  wait ) {
				
				byte[] reeData = new byte[ 12]; 
				byte[] reqst=new byte[1]; reqst[0]=12;
				if(connection.bulkTransfer(outEp,reqst,0,reqst.length,5000)>=0){
					if( connection.bulkTransfer(inEp, reeData, 0,reeData.length, 0)>=0){
						data="";
						
						for (int i = 0; i < reeData.length; i++) {
						data=data+"||" +hax( reeData[i] );}
				
				runOnUiThread(new Runnable(){
					public void run(){
						onlayout();}});
						
					}
					
					
				}
		///////___	
		}
 	}
    }	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		wait = false;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		counterTextView = findViewById(R.id.counter_text_view);
		userInputTextView = findViewById(R.id.user_input_text_view);
		reqdata = findViewById(R.id.display_text_view);
		Button transferButton = findViewById(R.id.transfer_button);
		
	//	displayTextView.setText("%%%%");
		
		transferButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			//	transferUserInput();
			wait=false;
			}
		});
	   // reqdata=findViewById(R.id.reqdat);
	//	bytes=new ArrayList<>();
		usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
		Map<String, UsbDevice> deviceList = usbManager.getDeviceList();
		if(!deviceList.isEmpty()){
			device=new ArrayList<>( deviceList.values()).get(0) ;
			if(device!=null) {  pendingIntent= PendingIntent.getBroadcast(this, 0, new Intent("com.android.example.USB_PERMISSION"), 0);
				usbManager.requestPermission(device, pendingIntent);
				connection = usbManager.openDevice(device);
				usbInterface = device.getInterface(0);
				connection.claimInterface(usbInterface, true);
				
				for (int i = 0; i < usbInterface.getEndpointCount(); i++) {
					UsbEndpoint endpoint = usbInterface.getEndpoint(i);
					if (endpoint.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
						if ((endpoint.getDirection() & UsbConstants.USB_DIR_IN) != 0) {
							inEp = endpoint;
							} else {
							outEp= endpoint;
						}
					}
				}
			}
		}
		
		
		
		
	if(inEp!=null&&outEp!=null){
		new Thread(new CoThread()).start();}
	}//////////////////////////////
	/////////////////onCreate//////
	
	private void transferUserInput() {counter++;
		String userInput = userInputTextView.getText().toString();
		reqdata.setText(userInput);
	}
 public void onlayout(){counter++;
 //	Toast.makeText(MainActivity.this,"%%%%",Toast.LENGTH_SHORT).show();
 reqdata.setText(counter+":"+data);wait=true;}	
	
 }
