package com.ceg.farrow;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Set_data extends ActionBarActivity {

	//Creates variables to be used
	private Button btnSend;
	private EditText ipView, portView;
	private String serverIp; 
	private int serverPort;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_data);

		//Instantiates the variables to be used with values
		this.btnSend = (Button)this.findViewById(R.id.btnSend);
		this.ipView = (EditText)this.findViewById(R.id.server_ip);
		this.portView = (EditText)this.findViewById(R.id.server_port);

		//Listens for the accept button to be clicked
		btnSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Calls sendData method when button is clicked
				sendData();
			}
		});
	}

	//Method that sends entered IP & Port values to the chat screen
	private void sendData(){
		//Gets the values from screen
		serverIp = ipView.getText().toString();
		serverPort = Integer.parseInt(portView.getText().toString());

		//Creates the intent that will call the next screen
		Intent i = new Intent(this, ChatActivity.class);

		//Puts IP & Port values to the intent so that the chat screen can see them
		i.putExtra("ip", serverIp);
		i.putExtra("port", serverPort);

		//Starts chat screen
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_data, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}