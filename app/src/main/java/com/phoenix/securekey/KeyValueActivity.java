package com.phoenix.securekey;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by anujkumars on 9/12/2015.
 */
public class KeyValueActivity extends Activity {

    ArrayAdapter mAdapter;
    ListView mListView,list2;
    List<String> keyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyvalue);
        mListView = (ListView) findViewById(R.id.activity_vault_list_view);
        list2=(ListView)findViewById(R.id.list1);
        keyList=new ArrayList<>();
        //set the data
        keyList.add("card");
        keyList.add("pin");
        keyList.add("cvv");
        //String[] key={"card","pin","cvv"};

        mAdapter = new ArrayAdapter<String>(KeyValueActivity.this,android.R.layout.simple_list_item_1,keyList);
        list2.setAdapter(mAdapter);

    }
}
