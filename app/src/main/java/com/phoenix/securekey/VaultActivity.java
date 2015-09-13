package com.phoenix.securekey;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by anujkumars on 9/12/2015.
 */
public class VaultActivity extends AppCompatActivity {
    ListView mListView;
    private List<Vault> vaultList;
    VaultListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault);

        mListView = (ListView) findViewById(R.id.activity_vault_list_view);
        PopulateListView();
    }

    public void PopulateListView(){
        vaultList = new ArrayList<>();

        vaultList = DbHandler.readfromvaultwithKeyNumber(getApplicationContext());

        mAdapter = new VaultListAdapter(this, vaultList);
        mListView.setAdapter(mAdapter);


        //List item listener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Vault vault = vaultList.get(position);

                if(vault.getIsSecure() == 1){
                    LayoutInflater li = LayoutInflater.from(VaultActivity.this);
                    View promptsView = li.inflate(R.layout.alert_vault_activity, null);
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VaultActivity.this);
                    final EditText input = (EditText) promptsView.findViewById(R.id.editTextPassword);

                    alertDialogBuilder.setView(promptsView);
                    alertDialogBuilder.setMessage("Authenticate to SecureKey!");
                    alertDialogBuilder.setPositiveButton("Login",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    String enteredPassword = input.getText().toString();
                                    if (!enteredPassword.equals("") && enteredPassword.equals(Long.toString(vault.getPasscode()))) {
                                        Toast.makeText(getApplicationContext(), "Vault Login Successful!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), KeyValueActivity.class);
                                        intent.putExtra("vaultId", vault.getId());
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Vault Login Failed, Try Again!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    );

                    alertDialogBuilder.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }
                    );
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else {

                    Intent intent = new Intent(getApplicationContext(), KeyValueActivity.class);
                    intent.putExtra("vaultId", vaultList.get(position).getId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vault, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_key) {
            Intent intent = new Intent(VaultActivity.this, AddVaultActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PopulateListView();
    }
}
