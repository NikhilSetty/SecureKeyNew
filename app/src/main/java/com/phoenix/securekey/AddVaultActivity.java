package com.phoenix.securekey;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

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
                //save data
                onBackPressed();
            }
        });

    }
}
