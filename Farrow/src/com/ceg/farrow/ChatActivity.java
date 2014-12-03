package com.ceg.farrow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChatActivity extends ActionBarActivity {
	
	private Socket socket;
	private Intent i;
	private int serverPort;
	private String serverIp;
	private ListView chatView;
	private EditText messageView;
	private Button sendButton;
	private PrintWriter out; 
	private BufferedReader in ;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> chatList = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		this.i = getIntent();
		this.serverIp = this.i.getStringExtra("ip");
		this.serverPort = this.i.getIntExtra("port", 0);
		Log.i("test", serverIp);
		this.chatView = (ListView)this.findViewById(R.id.chat);
		this.messageView = (EditText)this.findViewById(R.id.message);
		this.sendButton = (Button)this.findViewById(R.id.sendBtn);
		
		new Thread(new ClientThread()).start();
		sendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMessage();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}
	public void sendMessage()
	{
	try {
		String str = messageView.getText().toString();
		out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(socket.getOutputStream())),
				true);
		out.println(str);
	} catch (UnknownHostException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	
	public void populateListView(){
		this.adapter = new ArrayAdapter<String>(this, R.layout.items,chatList);
		chatView.setAdapter(adapter);
	}
	
	public void addMessage(String message){
		chatList.add(message);
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
	class ClientThread implements Runnable {

		@Override
		public void run() {
			
			try {
				InetAddress serverAddr = InetAddress.getByName(serverIp);

				socket = new Socket(serverAddr, serverPort);
				new Thread(new MessageListener()).start();

			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}
	class MessageListener implements Runnable {

		@Override
		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(
				        socket.getInputStream()));
				while (true) {
					String message = in.readLine();
					if(message != null){
						addMessage(message);
						
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								populateListView();
							}
						});
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
