package com.phoenix.securekey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.phoenix.securekey.Model.UserModel;

/**
 * Created by anujkumars on 9/12/2015.
 */
public class SignupActivity extends AppCompatActivity{
    Button btnSignUp;
    EditText editTextUserName;
    EditText editTextPassword;
    EditText editTextConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editTextUserName=(EditText)findViewById(R.id.input_name_edittext);
        editTextPassword=(EditText)findViewById(R.id.input_password_edittext);
        editTextConfirmPassword=(EditText)findViewById(R.id.input_confirmpassword_edittext);
        btnSignUp=(Button)findViewById(R.id.btn_signup);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=editTextUserName.getText().toString();
                String userPassword=editTextPassword.getText().toString();
                String confirmPassword=editTextConfirmPassword.getText().toString();
                UserModel userModel=new UserModel();

                if(!userPassword.matches("") &&  !userName.matches("") && !confirmPassword.matches(""))
                {
                    if(!userPassword.equals(confirmPassword))
                    {
                        Toast.makeText(getApplicationContext(),"Password doesn't Match ",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        userModel.UserName=userName;
                        userModel.Password=Integer.parseInt(userPassword);

                        DbHandler.insertUser(getApplicationContext(),userModel);
                        Intent intent = new Intent(SignupActivity.this,VaultActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }
}
