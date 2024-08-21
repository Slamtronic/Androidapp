package control.bulk.usbRequest;
import android.app.PendingIntent;
import android.content.Context;
//import android.content.Intent;
import android.content.Intent;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
//import android.hardware.usb.UsbMassStorageDevice;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
TextView str;String strin,string="";
EditText reqdir,treq,req,wlength,wvalue,windex,recepient;
int l,i,wv,rq,trq,disc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		str=findViewById(R.id.str);
		
		reqdir=findViewById(R.id.RequestDir);
		treq=findViewById(R.id.Requesttype);
		req=findViewById(R.id.Request);
		wlength=findViewById(R.id.wlength);
		windex=findViewById(R.id.wInded);
		wvalue=findViewById(R.id.wvalue);
		recepient=findViewById(R.id.Recipient);
				
		l=0;i=0;wv=0;rq=0;trq=0;disc=0;
	
			
			
				
		}
    
 public void bulk(View v){
		str.setText(string);}
 public void control(View v){
		
		UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
		
		Map<String, UsbDevice> deviceList = usbManager.getDeviceList();
		for (UsbDevice device : deviceList.values()) {
			
			PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent("com.android.example.USB_PERMISSION"), 0);
			usbManager.requestPermission(device, pi);
			
		
	
		
		UsbDeviceConnection connection = usbManager.openDevice(device);
		UsbInterface usbInterface = device.getInterface(0);
		setparams();
		
		byte[] desrc=devicdecriptor(l);
	int res=	connection.controlTransfer(
		trq,rq,wv,i,
		desrc,
		desrc.length,
		5000);
	if (res==desrc.length)	{
		strin="REQ:"+hax(trq)+"|"+hax(rq)+"|"+hax(wv)+"|"+hax(i)+"|"+hax(l)+"|"+"\n"+"data"+"|";
	 	for(int k=1;k<=l;k++){strin=strin +"|"+hax(desrc[k-1]);}
		string =string+"\n"+strin;
		str.setText(string);
		
		}}
	}	
		
	public byte[] devicdecriptor(int i){ return new byte[i];}
	
	public int getResult( UsbDeviceConnection usbdc, int bmreq,
	int req,int wv,int wi,byte[] dec,int wl){  
	return usbdc.controlTransfer(bmreq,req,wv,wi,dec,wl,5000 );}

	public int parsEditx(EditText etx){return Integer.
		parseInt(etx.getText().toString(),16) ;}     

	public void setparams(){  
		l=parsEditx(wlength);devicdecriptor(l);
		i=parsEditx(windex);wv=parsEditx(wvalue);
		rq=parsEditx(req);trq=parsEditx(treq);
	}
	public String hax(int i){ return  Integer.toHexString(i);   }
		
	}
