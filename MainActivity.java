package Usb.Lab_Thread;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOError;
import java.io.IOException;
import java.util.Map;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.NullCipher;

public class MainActivity extends AppCompatActivity {
	

Thread three;
UsbManager usbManager;
PendingIntent pendingIntent;
UsbDeviceConnection connection;
UsbInterface usbInterface;
UsbEndpoint inEp=null;
UsbEndpoint outEp=null;
UsbDevice device;
String sval,data,exrow,state,s,stage,newData;
boolean newScreen=false;
int i,val,rq,trq,wv,in,l,intt=0;
byte into=0;
EditText vl;
public TextView req,reqdata; 
List<Integer> bytes;
public byte[] devicdecriptor(int u){ return new byte[u];}
public String hax(int i){i=i&0xff; if(i<17){ return "0" +Integer.toHexString(i);}   
	else{return Integer.toHexString(i); }
	
	} 
	
 public class Scriin extends Thread{
	TextView tv;
	
		@Override
		public void run(){while(true)
			{byte[] reeData = new byte[ 12];
				byte[] reqst=new byte[1]; into++;reqst[0]=into;
				if(connection.bulkTransfer(outEp,reqst,0,reqst.length,5000)>=0){
					if( connection.bulkTransfer(inEp, reeData, 0,reeData.length, 0)>=0){
						data="";
						
						for (int i = 0; i < reeData.length; i++) {
						data=data+"||" +hax( reeData[i] );}
						
						
						
						runOnUiThread(new Runnable(){
							public void run(){
						dispo();}});
						
						
						
						
						
					}
					
					
				}
				
		//while((i%9)<8); i++;reqdata.setText("%%%%");	}
		}
	}	

 }
 
    @Override
 public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sval="";data="";exrow="||";stage="";
        vl=findViewById(R.id.val);
        //req=findViewById(R.id.req);
        reqdata=findViewById(R.id.reqdat);
        bytes=new ArrayList<>();
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
	   three=new Scriin();
		 three .start();}
  
  
  }////////////////////////////////////
 //////////////////////on create
 
 
 
 
 
 
   public void add(View v) { 
		
			if( vl.getText().length()==0)  {
				bytes.add(i,0); sval="";i++;disp();
				}
			
	else{try{		
	val=  Integer.parseInt(vl.getText().toString(),16) ;
	bytes.add(i,val); sval="";i++;disp();}
	catch (NumberFormatException e){
		Toast.makeText(this,"please enter a hex valor",Toast.LENGTH_SHORT ).show();   }
   }}
	
	public void clear(View w){
	if(i>=1) {i--;sval="";bytes.remove(i); 
	disp();}	    
    }
	
	
	 		
     public void disp(){
	 for(int j=0;j<bytes.size();j++){//intt=bytes.get(j);
		//if(intt>0xff){intt=intt-0xffffff00;}
		 sval=sval+"||";if(bytes.get(j)<=9){sval=sval+"0";  }
	 sval=sval+Integer.toHexString(bytes.get(j));} //dispo(exrow+"PARAMS"+sval);
	 newData= exrow+"PARAMS"+sval; dispo2(newData) ; }
	 
	
	 public void dispo(){   exrow=exrow+"\n"+data;
		  reqdata.setText(exrow);
		    }
	
	
	
		
	 public void ctrl(View m){ if(i==8){exrow=exrow+"CTRL"+sval+"\n"+getctrldata() +"\n" ;
	sval="";dispo2(exrow);bytes.clear();i=0;}
	else{Toast.makeText(this,"enter 8 byte of the cntrl",Toast.LENGTH_SHORT ).show(); }}
	
	public void bulk(View n){ i++;    }
	public void bulk4(View n){ if((bytes.size()>=1)||( bytes.size()>30&&bytes.size()==bytes.get(14)+15)){
			exrow=exrow+"BULK"+sval+"\n"+getbulkdata() +"\n" ;
			  sval="";dispo2(exrow);bytes.clear();i=0;
			  }		 
              else{  Toast.makeText(this,"please enter at least 1 params ",Toast.LENGTH_SHORT ).show();  }
		}
		
		
			          		  
	public String getctrldata(){ 
		data="no device";
		if(device!=null){ 
		
		trq=bytes.get(0); rq=bytes.get(1);
		wv=(bytes.get(2)*256)+bytes.get(3);
		in=(bytes.get(4)*256)+bytes.get(5); 
		l= (bytes.get(6)*256)+bytes.get(7);                     
		byte[] des_rec=devicdecriptor(l);
		
		
		int res=	connection.controlTransfer(
		trq,rq,wv,in,
		des_rec,
		des_rec.length,
		5000);
		data="no data received from device";
		if (res==des_rec.length)	{data="|";
		 	
			for(int k=1;k<=l;k++){data=data+"|"+hax(des_rec[k-1]);}
		
			
        }}
		
		reqdata.setTextColor(0xffffff00);
		return "DATA "+data;  }
	//////////////~/////////
	

public String getbulkdata(){return data; }


/*	public String getbulkdata2(){data ="no bulk data";
		if(inEp!=null && outEp!=null){
		Toast.makeText(this,"endpoint ok",Toast.LENGTH_SHORT).show();	
		byte[] request=new byte[i];	
		for(int k=0;k<i;k++){int kl=bytes.get(k);request[k] =(byte)kl;} 
		byte[] request1= 
	 new byte[]{(byte)0x55,(byte)0x53,
				(byte)0x42,(byte)0x43,(byte)0xa8,
				(byte)0x32,(byte)0xde,(byte)0x85,
				(byte)0x24,0,0,0,(byte)0x80,0,6,
				(byte)0x12,0,0,0,(byte)0x24,0,0,0,
				0,0,0,0,0,0,0,0
			};   	
		     int bytsent= connection.bulkTransfer(outEp,request,0,request.length,5000);
			
			if(bytsent>0){
				Toast.makeText(this,"request sent",Toast.LENGTH_SHORT).show();
		
			
			byte[] responseData = new byte[ 12];
				int bytesRead = connection.bulkTransfer(inEp, responseData, 0,responseData.length, 0);
			
			
			
				if (bytesRead >=0) {data="";
					
					for (int i = 0; i < responseData.length; i++) {
				      data=data+"||" +hax( responseData[i] );
					  
					  
					  
					  
					}
						
			    }
				
				
				
		    }   
			
			
			 
	    } else{ Toast.makeText(this,"endpoint does nt exist",Toast.LENGTH_SHORT).show();  }
		return "DATA "+data+ "\n"+"STATUS"+state; 
    }
	*/	
  		
		
		
public void dispo2(String s){reqdata.setText(s);//vl.setText("");
		}		
		
			
}//ou