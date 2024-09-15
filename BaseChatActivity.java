package de.quandoo.android2androidaccessory;

import android.content.Context;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseChatActivity extends AppCompatActivity {

   // @BindView(R.id.content_text)
    TextView contentTextView;

 //   @BindView(R.id.input_edittext)
    EditText input;
String inputString;
   // @OnClick(R.id.send_button)
   // Button button;
	
	public void onButtonClick(View v) {
		Toast.makeText(this,"sending",Toast.LENGTH_SHORT).show();
		input=findViewById(  R.id.input_edittext );
		contentTextView=findViewById(  R.id.content_text  );
          inputString = input.getText().toString();
        if (inputString.length() == 0) {
            return;
        }

        
        printLineToUI( inputString);//getString(R.string.local_prompt) +
       sendString(inputString);
	   input.setText("");
    }

    protected abstract void sendString(final String string);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

    }

   /* protected void printLineToUI(final String line) {
        runOnUiThread(new Runnable() {
            @Override
           public void run() {
                contentTextView.setText(contentTextView.getText() + "\n" + line);
			  
            }
        });
    }*/

protected void printLineToUI(final String line) {
	if (contentTextView != null) {
		runOnUiThread(() -> contentTextView.append("\n" + line));
	}
}



}
