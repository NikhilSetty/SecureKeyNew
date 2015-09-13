package com.phoenix.securekey;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nravishankar on 9/13/2015.
 */
public class DbHandler {

    public static List<UserModel> readUserData(Context context) {
        List<UserModel> userList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse(DbTableStrings.AUTH_URI),null,null,null,null);
        if(cursor.moveToFirst()){
             do {
                UserModel user = new UserModel();
                user.UserName = cursor.getString(cursor.getColumnIndex(DbTableStrings.USERNAME));
                user.Password = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbTableStrings.PASSWORD)));
                userList.add(user);
            }while(cursor.moveToNext());

        }
        cursor.close();

        return userList;
    }

    public static List<Vault> readfromvault(Context context){
        List<Vault> vaultList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse(DbTableStrings.VAULT_URI),null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                Vault vault = new Vault();
                vault.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                vault.setName(cursor.getString(cursor.getColumnIndex(DbTableStrings.VAULT_NAME)));
                vault.setPasscode(cursor.getLong(cursor.getColumnIndex(DbTableStrings.VAULT_PASSWORD)));
                vault.setIsSecure(cursor.getInt(cursor.getColumnIndex(DbTableStrings.IS_SECURE)));
                vaultList.add(vault);
            }while(cursor.moveToNext());

        }
        cursor.close();

        return vaultList;
    }

    public static List<Vault> readfromvaultwithKeyNumber(Context context){
        List<Vault> vaultList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse(DbTableStrings.VAULT_URI),null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                Vault vault = new Vault();
                vault.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                vault.setName(cursor.getString(cursor.getColumnIndex(DbTableStrings.VAULT_NAME)));
                vault.setPasscode(cursor.getLong(cursor.getColumnIndex(DbTableStrings.VAULT_PASSWORD)));
                vault.setIsSecure(cursor.getInt(cursor.getColumnIndex(DbTableStrings.IS_SECURE)));
                vault.setKeyNumber((DbHandler.getPairsByvaultId(context,vault.getId()).size()));
                vaultList.add(vault);
            }while(cursor.moveToNext());

        }
        cursor.close();

        return vaultList;
    }

    public static List<KeyValue> getAllPairs(Context context){
        List<KeyValue> kvList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse(DbTableStrings.DATA_URI),null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                KeyValue kv = new KeyValue();
                kv.setName(cursor.getString(cursor.getColumnIndex(DbTableStrings.KEY)));
                kv.setValue(cursor.getString(cursor.getColumnIndex(DbTableStrings.VALUE)));
                kv.setVaultId(cursor.getInt(cursor.getColumnIndex(DbTableStrings.VAULT_ID)));
                kvList.add(kv);
            }while(cursor.moveToNext());

        }
        cursor.close();

        return kvList;
    }

    public static List<KeyValue> getPairsByvaultId(Context context,int vaultId){
        List<KeyValue> kvList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse(DbTableStrings.DATA_URI), null ,null,null,null);
        if(cursor.moveToFirst()){
            do{
                KeyValue kv = new KeyValue();
                kv.setName(cursor.getString(cursor.getColumnIndex(DbTableStrings.KEY)));
                kv.setValue(cursor.getString(cursor.getColumnIndex(DbTableStrings.VALUE)));
                kv.setVaultId(cursor.getInt(cursor.getColumnIndex(DbTableStrings.VAULT_ID)));
                if(kv.getVaultId()==vaultId)
                kvList.add(kv);
            }while(cursor.moveToNext());

        }
        cursor.close();

        return kvList;
    }

    public static void insertUser(Context context,UserModel model){
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbTableStrings.USERNAME, model.UserName);
        contentValues.put(DbTableStrings.PASSWORD, model.Password);
        contentResolver.insert(Uri.parse(DbTableStrings.AUTH_URI), contentValues);
    }


    public static void insertVault(Context context,Vault vault)
    {
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbTableStrings.VAULT_NAME, vault.getName());
        contentValues.put(DbTableStrings.IS_SECURE, vault.getIsSecure());
        contentValues.put(DbTableStrings.VAULT_PASSWORD, vault.getPasscode());
        contentResolver.insert(Uri.parse(DbTableStrings.VAULT_URI), contentValues);
    }

    public static void insertKeyValue(Context context,KeyValue kv)
    {
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DbTableStrings.KEY, kv.getName());
            contentValues.put(DbTableStrings.VALUE, kv.getValue());
        contentValues.put(DbTableStrings.VAULT_ID, kv.getVaultId());
        contentResolver.insert(Uri.parse(DbTableStrings.DATA_URI), contentValues);
    }

}
