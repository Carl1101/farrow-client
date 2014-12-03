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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChatActivity extends ActionBarActivity {

	//Creates Variables to be used
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

		//Instantiates variables to be used
		this.i = getIntent();
		this.serverIp = this.i.getStringExtra("ip");
		this.serverPort = this.i.getIntExtra("port", 0);
		this.chatView = (ListView)this.findViewById(R.id.chat);
		this.messageView = (EditText)this.findViewById(R.id.message);
		this.sendButton = (Button)this.findViewById(R.id.sendBtn);

		//Starts ClientThread
		new Thread(new ClientThread()).start();

		//Listens to send button click event
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

	//Method that sends the message to the server chosen through a socket
	public void sendMessage()
	{
		try {
			//Gets message from screen
			String str = messageView.getText().toString();

			//Creates PrintWriter to send message through socket
			out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())),
					true);

			//Sends message to server through socket
			out.println(str);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Fills the list view on screen with the values of the chat list
	public void populateListView(){
		this.adapter = new ArrayAdapter<String>(this, R.layout.items,chatList);
		chatView.setAdapter(adapter);
	}

	//Adds string message to the chat list
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

	//Thread that manages the socket
	class ClientThread implements Runnable {

		@Override
		public void run() {

			try {
				InetAddress serverAddr = InetAddress.getByName(serverIp);

				socket = new Socket(serverAddr, serverPort);

				//Starts thread that listens for messages after there has been a socket connection
				new Thread(new MessageListener()).start();

			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	//Thread that listens for messages
	class MessageListener implements Runnable {

		@Override
		public void run() {
			try {
				//Creates BufferedReader that reads messages
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));

				//Reads message, adds message to chat list and shows it on screen
				while (true) {
					String message = in.readLine();
					if(message != null){
						addMessage(message);

						//Runs UI thread so that items on the screen can be modified
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								//Populates the list on the screen
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