package com.phoenix.securekey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by anujkumars on 9/12/2015.
 */
public class AddVaultActivity extends AppCompatActivity {
    EditText editTextName;
    EditText editTextPassword;
    CheckBox checkBoxSetPassword;
    Button addVault;
    android.support.design.widget.TextInputLayout inputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vault);

        editTextName=(EditText)findViewById(R.id.edittext_vault_name);
        editTextPassword=(EditText)findViewById(R.id.edittext_vault_password);
        checkBoxSetPassword=(CheckBox)findViewById(R.id.set_vault_password);
        inputLayout=(android.support.design.widget.TextInputLayout)findViewById(R.id.vault_password_edittext);
        addVault=(Button)findViewById(R.id.btn_add_vault);

        inputLayout.setVisibility(View.GONE);

        checkBoxSetPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxSetPassword.isChecked()) {
                    inputLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    inputLayout.setVisibility(View.GONE);
                }
            }
        });

        addVault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vaultName="";
                int vaultPin;
                vaultName = editTextName.getText().toString();
                Vault vault = new Vault();

                if(!vaultName.matches(""))
                {
                    vault.setName(vaultName);
                    vault.setKeyNumber(0);
                    vault.setIsSecure(0);
                    if(checkBoxSetPassword.isChecked())
                    {
                        if(!editTextPassword.getText().toString().matches("")){
                            vaultPin = Integer.parseInt(editTextPassword.getText().toString());
                            vault.setPasscode(vaultPin);
                            vault.setIsSecure(1);
                            DbHandler.insertVault(getApplicationContext(), vault);
                            finish();
                        }
                    }
                    else {
                       vault.setPasscode(0);
                        DbHandler.insertVault(getApplicationContext(), vault);
                        Intent intent = new Intent(AddVaultActivity.this, VaultActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(), "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
