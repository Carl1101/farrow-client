package com.ceg.farrow;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	private TextView usernameView, passwordView;
	private String username, password;
	private Button btnLogIn;
	private static final String USER = "farrow", PASS = "2014";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.btnLogIn = (Button)this.findViewById(R.id.logIn);
        this.usernameView = (TextView)this.findViewById(R.id.username);
        this.passwordView = (TextView)this.findViewById(R.id.password);
        
        btnLogIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				logIn();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    public void logIn(){
    	this.username = usernameView.getText().toString();
    	this.password = passwordView.getText().toString();
    	
    	if(username.equalsIgnoreCase(USER) && password.equals(PASS)){
    		//Here goes intent to chat activity
    	}
    }
}
