package com.listview1;
import android.content.Intent;
import android.app.PendingIntent;
import android.content.Context;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Map;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.crypto.NullCipher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
//import android.widget.ListView;
import android.widget.ScrollView;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.List;

public class MainActivity extends AppCompatActivity {

String[] esay;int col=0xffff0000;
ScrollView lnn2;
LinearLayout lnn,roww;
LayoutInflater inflater;View[] itemV;
TextView index,b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15;
Random ron;
UsbManager usbManager;
PendingIntent pendingIntent;
UsbDeviceConnection connection;
UsbInterface usbInterface;
UsbEndpoint inEp=null;
UsbEndpoint outEp=null;
UsbDevice device;

String  sval,data,exrow,state,s,stage;
int i,val,rq,trq,wv,in,l,intt,coll,pos;
int sec0,sec1,sec2,sec3;
Button num0,num1;
EditText secc0,secc1,secc2,secc3,numm0,numm1;
TextView sector;
List<Integer> bytes;
public byte[] devicdecriptor(int u){ return new byte[u];}

public String hax(int i){ i=i&0xff; if(i<16){return
	"0"+Integer.toHexString(i);	            }
else{	return  Integer.toHexString(i);   }}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	 lnn=findViewById(R.id.parent_layout);
	 roww=findViewById(R.id.row);
	 sector=findViewById(R.id.sector);	
    esay= new String[17];
	sec0=0;sec1=0;sec2=0;sec3=0;pos=0xffffffff;
	wv=1000;
	ron= new Random();
	itemV=new View[65];
	/////////_
	
	secc0=findViewById(R.id.sec0);//numm0=findViewById(R.id.num0);
	secc1=findViewById(R.id.sec1);//numm1=findViewById(R.id.num1);
	secc2=findViewById(R.id.sec2);secc3=findViewById(R.id.sec3);
	
	
	//req=findViewById(R.id.req);
// 	reqdata=findViewById(R.id.reqdata);
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
	
	 addBlock();
	
	/////___
	
	}
	public void down(View v){ 
		pos=sec0+(sec1*256)+(sec2*65536)+(sec3*16777216);
		if(pos==0xffffffff){sec3=0;  sec2=0; sec1=0; sec0=0; }
		else{
		pos=pos+1;
		sec3=pos/16777216;pos=pos%16777216;
		sec2=pos/65536;pos=pos%65536;
		sec1=pos/256;sec0=pos%256;}
		
		get_data();
		 }
		
	public void up(View v){ 
		
		pos=sec0+(sec1*256)+(sec2*65536)+(sec3*16777216);
		if(pos==0){sec3=0xff;  sec2=0xff; sec1=0xff; sec0=0xff; }
		else{  pos=pos-1;
		  sec3=pos/16777216;pos=pos%16777216;
		  sec2=pos/65536;pos=pos%65536;
		  sec1=pos/256;sec0=pos%256;}
		  
		  get_data();  }	
	
	public void addBlock(){
		
		col=0xaa000000+(0xffffff&ron.nextInt());
		esay[0] ="@";
		esay[1]="pl";
		esay[2]="ea";
		esay[3]="se";
		esay[4]="";
		esay[5]="pl";
		esay[6]="ug";
		esay[7]="";
		esay[8]="m";
		esay[9]="a";
		esay[10]="ss";
		esay[11]="";
		esay[12]="st";
		esay[13]="or";
		esay[14]="a";
		esay[15]="ge";
		esay[16]="@";
		itemV[0]=addRow(esay,0);
		col=-col;
		
		esay[1]="xx";
		esay[2]="xx";
		esay[3]="xx";
		esay[4]="xx";
		esay[5]="xx";
		esay[6]="xx";
		esay[7]="xx";
		esay[8]="xx";
		esay[9]="xx";
		esay[10]="xx";
		esay[11]="xx";
		esay[12]="xx";
		esay[13]="xx";
		esay[14]="xx";
		esay[15]="xx";
		esay[16]="xx";
		for(int i=0;i<32;i++)
		{esay[0] =hax(i*16);
			itemV[i+1]=addRow(esay,i);}
		
		wv++;
		
		
		
		}
		
		
		
		
    public void add_Block(View v){addBlock();}
	

public int parsEditx(EditText etx){return Integer.
parseInt(etx.getText().toString(),16) ;}



public void bulk(View v){
		
		
		
		
	try{
			sec0=   Integer.parseInt(secc0.getText().toString(),16) ;
			
		}
		catch (NumberFormatException e){
	//	Toast.makeText(this,"please enter a hex valor",Toast.LENGTH_SHORT ).show();
		 }
	
	   try{
		    sec1=   Integer.parseInt(secc1.getText().toString(),16) ;
		
		}
	catch (NumberFormatException e){
//	Toast.makeText(this,"please enter a hex valor",Toast.LENGTH_SHORT ).show();
	 }
	try{
			sec2= Integer.parseInt(secc2.getText().toString(),16) ;
		}
	catch (NumberFormatException e){
//	Toast.makeText(this,"please enter a hex valor",Toast.LENGTH_SHORT ).show(); 
	}
	try{
			sec3= Integer.parseInt(secc3.getText().toString(),16) ;
			
			    
	 	}
		catch (NumberFormatException e){
	//	Toast.makeText(this,"please enter a hex valor",Toast.LENGTH_SHORT ).show(); 
		}
	       get_data();	}////
		
		
	public void get_data(){	 
	
		byte[] responseData;
		byte[] request= new byte[]{(byte)0x55,(byte)0x53,
			(byte)0x42,(byte)0x43,(byte)0xa8,
			(byte)0x32,(byte)0xde,(byte)0x85,
			(byte)0,2,0,0,(byte)0x80,0,10,
			(byte)0x28,0,(byte)sec3,(byte)sec2,(byte)sec1,(byte)sec0,0,0,1,
			0,0,0,0,0,0,0
		};   // Al
		
		responseData = new byte[ 0x200];//( ( (
		for(int i=0;i<0x200;i++){responseData[i]=(byte)0xff;}
		
		// Al
		int bytsent= connection.bulkTransfer(outEp,request,0,request.length,5000);
		
		if(bytsent>0){
			//Toast.makeText(this,"request sent",Toast.LENGTH_SHORT).show();
			
			int bytesRead = connection.bulkTransfer(inEp, responseData, 0,responseData.length, 5000);
			
			byte[] statuss = new byte[13];
			int statusRead = connection.bulkTransfer(inEp, statuss, 0,13, 5000);
			
			
			
			
			if (bytesRead >=0) {
				
				esay[0] ="Sec";
				esay[1]="0";
				esay[2]="x";
				esay[3]=hax(sec3);
				esay[4]=hax( sec2 );
				esay[5]=hax( sec1);
				esay[6]=hax( sec0 );
				esay[7]="|||";
				esay[8]="|||";
				esay[9]="|||";
				esay[10]="|||";
				esay[11]="|||";
				esay[12]="|||";
				esay[13]="|||";
				esay[14]="|||";
				esay[15]="|||";
				esay[16]="|||";
				setRow(esay,0);
				
				for (int i = 0; i < 32; i++) {
					esay[0] =""+hax( i*16);
					esay[1]=hax( responseData[i*16] );
					esay[2]=hax( responseData[(i*16)+1] );
			    	esay[3]=hax( responseData[(i*16)+2] );
					esay[4]=hax( responseData[(i*16)+3] );
					esay[5]=hax( responseData[(i*16)+4] );
					esay[6]=hax( responseData[(i*16)+5] );
					esay[7]=hax( responseData[(i*16)+6] );
					esay[8]=hax( responseData[(i*16)+7] );
					esay[9]=hax( responseData[(i*16)+8]);
					esay[10]=hax( responseData[(i*16)+9] );
					esay[11]=hax( responseData[(i*16)+10] );
					esay[12]=hax( responseData[(i*16)+11] );
					esay[13]=hax( responseData[(i*16)+12] );
					esay[14]=hax( responseData[(i*16)+13] );
					esay[15]=hax( responseData[(i*16)+14] );
					esay[16]=hax( responseData[(i*16)+15] );
					
					
			 	//	addRow(esay,wv+i+1);
				setRow(esay,i+1);
					
					sector.setText("Sector 0x"+ 
					 Integer.toHexString ( (    ( ( 
					 ((sec3*0x100)+sec2)*0x100 )+sec1 
					 )*0x100)+sec0    ) );
					
				}}
				
				}
				
  	
}		
		
	
		
	
	
	
	
		
		
		public View addRow(String[ ] ary,int j){//col=col+0xff;
			//
		 inflater = LayoutInflater.from(this);
		View itemView= inflater.inflate(R.layout.itemp,lnn, false);
		
			itemView.setBackgroundColor(col);
		
		
		index = itemView.findViewById(R.id.index);
		index.setText(ary[0]); index.setTextColor(~col);
		
		b0 = itemView.findViewById(R.id.b0);
		b0.setText(ary[1]);
		
	    b1 = itemView.findViewById(R.id.b1);
		b1.setText(ary[2]);
		
		b2 = itemView.findViewById(R.id.b2);
		b2.setText(ary[3]);
		b3 = itemView.findViewById(R.id.b3);
		b3.setText(ary[4]);
		b4 = itemView.findViewById(R.id.b4);
		b4.setText(ary[5]);
		
		b5 = itemView.findViewById(R.id.b5);
		b5.setText(ary[6]);
		b6 = itemView.findViewById(R.id.b6);
		b6.setText(ary[7]);
		b7 = itemView.findViewById(R.id.b7);
		b7.setText(ary[8]);
		//////_____///////
		b8 = itemView.findViewById(R.id.b8);
		b8.setText(ary[9]);
		
		b9 = itemView.findViewById(R.id.b9);
		b9.setText(ary[10]);
		
		b11 = itemView.findViewById(R.id.b11);
		b11.setText(ary[12]);
		b12 = itemView.findViewById(R.id.b12);
		b12.setText(ary[13]);
		b13 = itemView.findViewById(R.id.b13);
		b13.setText(ary[14]);
		
		b14 = itemView.findViewById(R.id.b14);
		b14.setText(ary[15]);
		b15 = itemView.findViewById(R.id.b15);
		b15.setText(ary[16]);
		b10 = itemView.findViewById(R.id.b10);
		b10.setText(ary[11]);
		
		
		
			
		lnn.addView(itemView);
		itemView.setId(j+wv);
		return itemView;}
		
		
	public void setRow(  String[] ary,int i ){
		index = itemV[i].findViewById(R.id.index);
		index.setText(ary[0]);
		
		b0 = itemV[i].findViewById(R.id.b0);
		b0.setText(ary[1]);
		
		b1 = itemV[i].findViewById(R.id.b1);
		b1.setText(ary[2]);
		
		b2 = itemV[i].findViewById(R.id.b2);
		b2.setText(ary[3]);
		b3 = itemV[i].findViewById(R.id.b3);
		b3.setText(ary[4]);
		b4 = itemV[i].findViewById(R.id.b4);
		b4.setText(ary[5]);
		
		b5 = itemV[i].findViewById(R.id.b5);
		b5.setText(ary[6]);
		b6 = itemV[i].findViewById(R.id.b6);
		b6.setText(ary[7]);
		b7 = itemV[i].findViewById(R.id.b7);
		b7.setText(ary[8]);
		
		
		
		
		b8 = itemV[i].findViewById(R.id.b8);
		b8.setText(ary[9]);
		
		b9 = itemV[i].findViewById(R.id.b9);
		b9.setText(ary[10]);
		
		b10 = itemV[i].findViewById(R.id.b10);
		b10.setText(ary[11]);
		b11 = itemV[i].findViewById(R.id.b11);
		b11.setText(ary[12]);
		b12 = itemV[i].findViewById(R.id.b12);
		b12.setText(ary[13]);
		
		b13 = itemV[i].findViewById(R.id.b13);
		b13.setText(ary[14]);
		b14 = itemV[i].findViewById(R.id.b14);
		b14.setText(ary[15]);
		b15 = itemV[i].findViewById(R.id.b15);
		b15.setText(ary[16]);
		
		
		
		}	
		
		
		
		}