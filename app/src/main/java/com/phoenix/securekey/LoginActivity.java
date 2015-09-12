package com.phoenix.securekey;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    EditText name;
    EditText password;
    public static final String NAME="name";
    public static final String PASSWORD="password";
    String Name;
    String Password;
    Context applicationContext;
    TextView signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        applicationContext=getApplicationContext();

        name=(EditText)findViewById(R.id.edittext_name);
        password=(EditText)findViewById(R.id.edittext_password);
        loginButton=(Button)findViewById(R.id.btn_login);
        Name=name.getText().toString();
        Password=password.getText().toString();
        signupButton=(TextView)findViewById(R.id.link_signup);
        signupButton.setClickable(true);



        SharedPreferences prefs = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(LoginActivity.this, VaultActivity.class);
                startActivity(intent);

                //registerInBackground(Name, Password);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    private void registerInBackground(final String UserName, final String UserPassword) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
              
                try {
                    SharedPreferences prefs = getSharedPreferences("UserDetails",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(NAME, UserName);
                    editor.putString(PASSWORD, UserPassword);
                    editor.commit();
                    return true;
                }
                catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean status) {
                if (status==true) {
                    Toast.makeText(applicationContext,"Session Saved",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(applicationContext,"Session Failed", Toast.LENGTH_LONG).show();
                }
            }
        }.execute(null, null, null);
    }

    // When Application is resumed, check for Play services support to make sure app will be running normally
    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
      public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}