package com.phoenix.securekey;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nravishankar on 9/13/2015.
 */
public class DbHandler {

    public static List<UserModel> readfromvault(Context context) {
        List<UserModel> userList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse(DbTableStrings.AUTH_URI),null,null,null,null);
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()) {
                UserModel user = new UserModel();
                user.UserName = cursor.getString(cursor.getColumnIndex(DbTableStrings.USERNAME));
                user.Password = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbTableStrings.PASSWORD)));
                userList.add(user);
            }

        }
        cursor.close();

        return userList;
    }

    public static List<Vault> readAll(Context context){
        List<Vault> vaultList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse(DbTableStrings.AUTH_URI),null,null,null,null);
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()) {
                Vault vault = new Vault();
                vault.setName(cursor.getString(cursor.getColumnIndex(DbTableStrings.VAULT_NAME)));
                vault.setId(cursor.getInt(cursor.getColumnIndex(DbTableStrings.VAULT_ID)));
                vaultList.add(vault);
            }

        }
        cursor.close();

        return vaultList;
    }
}
