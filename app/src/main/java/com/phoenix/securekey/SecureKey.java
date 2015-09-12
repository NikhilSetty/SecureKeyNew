package com.phoenix.securekey;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

/**
 * Created by nravishankar on 9/12/2015.
 */
public class SecureKey extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView kv;
    private Keyboard keyboard;
    LayoutInflater li;
    static Boolean pavan = false;

    private boolean caps = false;

    @Override
    public View onCreateInputView() {
        li = LayoutInflater.from(this);
        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE :
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            case -22:
                Authenticate();
                break;
            case -33:
                VaultAccess();
                break;
            case -44:
                NewTest();
                break;
            default:
                char code = (char)primaryCode;
                if(Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
        }
    }

    private void NewTest() {
        pavan = true;
        RelativeLayout v = (RelativeLayout) getLayoutInflater().inflate(R.layout.alert_generate_service_request, null);
        setInputView(v);
    }

    private void AddKey() {
        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.newquery);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        setInputView(kv);
    }

    private void Authenticate() {
        View promptsView = li.inflate(R.layout.authenticate, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final EditText input = (EditText) promptsView.findViewById(R.id.editTextPassword);
        Button button1 = (Button) promptsView.findViewById(R.id.click_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.append("1");
            }
        });
        Button button2 = (Button) promptsView.findViewById(R.id.click_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.append("2");
            }
        });
        Button button3 = (Button) promptsView.findViewById(R.id.click_3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.append("3");
            }
        });
        Button button4 = (Button) promptsView.findViewById(R.id.click_4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.append("4");
            }
        });
        Button button5 = (Button) promptsView.findViewById(R.id.click_5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.append("5");
            }
        });
        Button button6 = (Button) promptsView.findViewById(R.id.click_6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.append("6");
            }
        });
        Button button7 = (Button) promptsView.findViewById(R.id.click_7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.append("7");
            }
        });
        Button button8 = (Button) promptsView.findViewById(R.id.click_8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.append("8");
            }
        });
        Button button9 = (Button) promptsView.findViewById(R.id.click_9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.append("9");
            }
        });
        Button button0 = (Button) promptsView.findViewById(R.id.click_0);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.append("0");
            }
        });

        Button buttonDel = (Button) promptsView.findViewById(R.id.click_del);
        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = input.getText().toString();
                if (text.length() > 0){
                    text = text.substring(0, text.length()-1);
                    input.setText("");
                    input.append(text);
                }
            }
        });

        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setMessage("Authenticate to SecureKey!");
        alertDialogBuilder.setPositiveButton("Login",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        List<UserModel> userList = DbHandler.readfromvault(getApplicationContext());
                        if(userList.get(0).UserName.equals("admin")){
                            String enteredPassword = input.getText().toString();
                            if(!enteredPassword.equals("") && enteredPassword.equals(Long.toString(userList.get(0).Password))){
                                Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                                AddKey();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Login Failed, Try Again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.token = kv.getWindowToken();
        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertDialog.show();
        input.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void VaultAccess() {
        View promptsView = li.inflate(R.layout.alert_list, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setMessage("Choose your vault");
        final AlertDialog alertDialog = alertDialogBuilder.create();


        final ListView listView = (ListView) promptsView.findViewById(R.id.listViewList);
        String[] vaults = new String[]{"Hello", "How", "are", "you"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.plain_text_list_item, vaults );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "You have clicked :" + item, Toast.LENGTH_LONG).show();
                String[] vaults = new String[]{"New", "Set", "Of", "Strings"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.plain_text_list_item, vaults );
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view,
                                            int position, long id) {
                        final String item = (String) parent.getItemAtPosition(position);
                        Toast.makeText(getApplicationContext(), "You have clicked :" + item, Toast.LENGTH_LONG).show();
                        InputConnection ic = getCurrentInputConnection();
                        ic.commitText(item, item.length());
                        alertDialog.dismiss();
                    }
                });
            }
        });
        alertDialogBuilder.setView(promptsView);
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.token = kv.getWindowToken();
        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertDialog.show();
    }

    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
        if(pavan){
            RelativeLayout v = (RelativeLayout) getLayoutInflater().inflate(R.layout.alert_generate_service_request, null);
            setInputView(v);
        }
    }

    @Override
    public void swipeUp() {
    }

    @Override
    public void onStartInputView(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);

        setInputView(onCreateInputView());
    }
}
