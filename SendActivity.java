package usb.broadcast.sender;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendActivity extends Activity {
	
	private static final String ACTION_USB_MESSAGE = "com.example.usbhostapp.USB_MESSAGE";
	private static final String EXTRA_MESSAGE = "message";
	private static final String  ACTION_MESSAGE_static="receive.from.app2";
	private static final String  any_ACTION="android.intent.action.ACTION_POWER_CONNECTED";
	private UsbManager usbManager;
	private boolean permissionRequested;
	private EditText editTextMessage;TextView tx;
	int i=0,j=0;Button bbt1,bbt2,bbt3;
	IntentFilter dynamic_intent ;
	private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (ACTION_USB_MESSAGE.equals(action)|| 
			 ACTION_MESSAGE_static.equals(action)||
			      "dynamic_filter".equals(action) ) {
				String message = intent.getStringExtra(EXTRA_MESSAGE);
				// Handle the received message here
				// TODO: Implement your logic
				tx.setText(action+"/"+message);
			}
			
		 
		else	 {tx.setText(action);}
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {tost("create");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send);
		bbt1= findViewById(R.id.buttonSend);
		bbt2= findViewById(R.id.buttonSend2);
		bbt3= findViewById(R.id.buttonSend3);
		//usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
		editTextMessage = findViewById(R.id.editTextMessage);
		tx=findViewById(R.id.TextMessage);
		dynamic_intent= new IntentFilter("dynamic_filter");//if use empty constructor u can use object.addAction("string action")
		bbt1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMessage();
			}
		});
		
		bbt2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMessage2();
			}
		});
		
		bbt3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMessage3();
			}
		});
		
	registerReceiver(usbReceiver, new IntentFilter(ACTION_USB_MESSAGE));
		registerReceiver(usbReceiver, new IntentFilter(ACTION_MESSAGE_static));
		registerReceiver(usbReceiver, new IntentFilter(any_ACTION));
		registerReceiver(usbReceiver, new IntentFilter("dynamic_filter"));
}
	private void sendMessage() {
		String message = editTextMessage.getText().toString();
		Intent intent = new Intent();
		intent.setAction(ACTION_USB_MESSAGE);
		intent.putExtra(EXTRA_MESSAGE, message);
		sendBroadcast(intent);
		i=i+1;
		bbt1.setText("sent "+i);}
		
	private void sendMessage2() {
			String message = editTextMessage.getText().toString();
			Intent intent2 = new Intent();
			intent2.setAction(ACTION_MESSAGE_static);
			intent2.putExtra(EXTRA_MESSAGE, message);
			sendBroadcast(intent2);
			j=j+1;
			bbt2.setText("usbsent "+j);	
	}
	
	private void sendMessage3() {
		String message = editTextMessage.getText().toString();
		Intent intent3 = new Intent();// u can use constructor new Intent("string action")
		intent3.setAction("dynamic_filter");
		intent3.putExtra(EXTRA_MESSAGE, message);
		sendBroadcast(intent3);
	}
	public void tost(String s){
		Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
	}
}