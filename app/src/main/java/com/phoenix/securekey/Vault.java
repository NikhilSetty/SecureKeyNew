package com.phoenix.securekey;

/**
 * Created by anujkumars on 9/12/2015.
 */
public class Vault {
    String Name;
    int keyNumber;

    public Vault()
    {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(int keyNumber) {
        this.keyNumber = keyNumber;
    }
}
