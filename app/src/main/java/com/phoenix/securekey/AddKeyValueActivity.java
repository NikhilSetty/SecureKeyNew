package com.phoenix.securekey;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



/**
 * Created by anujkumars on 9/12/2015.
 */
public class AddKeyValueActivity extends Activity {
    EditText editTextKey;
    EditText editTextValue;
    Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_keyvalue);

        editTextKey=(EditText)findViewById(R.id.edittext_key);
        editTextValue=(EditText)findViewById(R.id.edittext_value);
        saveButton=(Button)findViewById(R.id.btn_add);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save data
            }
        });
    }
}
