package com.phoenix.securekey;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
        vaultList=new ArrayList<>();

       vaultList=DbHandler.readfromvault(getApplicationContext());

        mAdapter = new VaultListAdapter(this, vaultList);
        mListView.setAdapter(mAdapter);


        //List item listener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "Item clicked " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), KeyValueActivity.class);
                intent.putExtra("vaultId",vaultList.get(position).getId());
                startActivity(intent);
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
}
