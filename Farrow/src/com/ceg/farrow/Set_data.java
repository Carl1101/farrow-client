package com.ceg.farrow;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Set_data extends ActionBarActivity {
	
	private Button btnSend;
	private EditText ipView, portView;
	private String serverIp; 
	private int serverPort;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_data);
		
		this.btnSend = (Button)this.findViewById(R.id.btnSend);
		this.ipView = (EditText)this.findViewById(R.id.server_ip);
		this.portView = (EditText)this.findViewById(R.id.server_port);
		
		btnSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendData();
			}
		});
	}
	
	private void sendData(){
		serverIp = ipView.getText().toString();
		serverPort = Integer.parseInt(portView.getText().toString());
		
		Intent i = new Intent(this, ChatActivity.class);
		i.putExtra("ip", serverIp);
		i.putExtra("port", serverPort);
		Log.i("test","voy por ahi");
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
