/*
 * Emma Shu: Cryptography is a program that will encrypt and decrypt messages
 * based on the users' choice and output the result at the bottom of the screen.
 */
package com.example.emmas.cryptography;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    RadioButton[] buttons = new RadioButton[3];
    Button encrypt;
    Button decrypt;
    TextView Output1TV;
    TextView Output2TV;
    EditText settingsET;
    EditText inputEET;
    EditText inputDET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttons[0] = (RadioButton)this.findViewById(R.id.encryption1);
        buttons[1] = (RadioButton)this.findViewById(R.id.encryption2);
        buttons[2] = (RadioButton)this.findViewById(R.id.encryption3);
        for (int a = 0; a < buttons.length; a++) {
            buttons[a].setOnClickListener(this);
        }

        encrypt = (Button)this.findViewById(R.id.button1);
        encrypt.setOnClickListener(this);
        decrypt = (Button)this.findViewById(R.id.button2);
        decrypt.setOnClickListener(this);
        Output1TV = (TextView)this.findViewById(R.id.textView1);
        Output2TV = (TextView)this.findViewById(R.id.textView2);
        settingsET = (EditText)this.findViewById(R.id.text3);
        inputDET = (EditText)this.findViewById(R.id.text2);
        inputEET = (EditText)this.findViewById(R.id.text1);

    }

    @Override
    public void onClick(View v) {
        // Scytale encryption/decryption.
        if(buttons[0].isChecked()) {
            if(v.equals(encrypt)) {
                encrypt();
            }
            if (v.equals(decrypt)) {
                decrypt();
            }
        }
        // Vigenere encryption/decryption.
        if((buttons[1].isChecked())) {
            if(v.equals(encrypt)) {
                encrypt();
            }
            if (v.equals(decrypt)) {
                decrypt();
            }
        }
        // Caesar encryption/decryption.
        if((buttons[2]).isChecked()) {
            if(v.equals(encrypt)) {
                encrypt();
            }
            if (v.equals(decrypt)) {
                decrypt();
            }
        }
    }

    public void encrypt() {
        if((buttons[0]).isChecked() || (buttons[2]).isChecked()) {
            String settingsString;
            int settings;
            String encryptionString;
            settingsString = settingsET.getText().toString();
            settingsString = settingsString.replaceAll("[^a-zA-Z0-9]","");
            settings = Integer.parseInt(settingsString);
            encryptionString = inputEET.getText().toString();
            encryptionString = encryptionString.replaceAll(" ","");
            encryptionString = encryptionString.toUpperCase();
            encryptionString = encryptionString.replaceAll("[^a-zA-Z]","");
            if ((buttons[0]).isChecked()) {
                encryptionString = scytaleEncryption(encryptionString, settings);

            }
            if ((buttons[2]).isChecked()) {
                encryptionString = caesarEncryption(encryptionString, settings);
            }
            Output1TV.setText("Encrypt Output: " + encryptionString);
        }
        if ((buttons[1].isChecked())) {
            String settingsString;
            String encryptionString;
            settingsString = settingsET.getText().toString();
            settingsString = settingsString.replaceAll("[^a-zA-Z]","");
            encryptionString = inputEET.getText().toString();
            encryptionString = encryptionString.replaceAll(" ","");
            encryptionString = encryptionString.toUpperCase();
            encryptionString = encryptionString.replaceAll("[^a-zA-Z]","");
            settingsString = settingsString.toUpperCase();
            vigenereEncryption(encryptionString, settingsString);
            encryptionString = vigenereEncryption(encryptionString, settingsString);
            Output1TV.setText("Encrypt Output: " + encryptionString);
        }

    }

    public void decrypt() {
        if((buttons[0]).isChecked() || (buttons[2]).isChecked()) {
            if ((buttons[0]).isChecked()) {
                String settingsString;
                int settings;
                String decryptionString;
                settingsString = settingsET.getText().toString();
                settings = Integer.parseInt(settingsString);
                decryptionString = inputDET.getText().toString();
                decryptionString = decryptionString.toUpperCase();
                decryptionString = scytaleDecryption(decryptionString, settings);
                Output2TV.setText("Decrypt Output: " + decryptionString);
            }
            if ((buttons[2]).isChecked()) {
                String settingsString;
                int settings;
                String decryptionString;
                settingsString = settingsET.getText().toString();
                settingsString = settingsString.replaceAll("[^a-zA-Z0-9]","");
                settings = Integer.parseInt(settingsString);
                decryptionString = inputDET.getText().toString();
                decryptionString = decryptionString.replaceAll(" ","");
                decryptionString = decryptionString.toUpperCase();
                decryptionString = decryptionString.replaceAll("[^a-zA-Z]","");
                decryptionString = caesarDecryption(decryptionString, settings);
                Output2TV.setText("Decrypt Output: " + decryptionString);
            }
        }
        if ((buttons[1].isChecked())) {
            String settingsString;
            String decryptionString;
            settingsString = settingsET.getText().toString();
            decryptionString = inputDET.getText().toString();
            decryptionString = decryptionString.replaceAll(" ","");
            decryptionString = decryptionString.toUpperCase();
            decryptionString = decryptionString.replaceAll("[^a-zA-Z]","");
            settingsString = settingsString.replaceAll("[^a-zA-Z]","");
            settingsString = settingsString.toUpperCase();
            decryptionString = vigenereDecryption(decryptionString, settingsString);
            Output2TV.setText("Decrypt Output: " + decryptionString);
        }

    }

    /*
     * Shift by adding number to the original letter to shift using ASCII table.
     */
    public String caesarEncryption(String encryptionString, int settings) {
        char[] original = encryptionString.toCharArray();
        encryptionString = "";
        for(int i = 0; i < original.length; i++) {
            char newchar;
            newchar = (char)('A'+(original[i] - 'A' + settings) % 26);
            encryptionString += newchar;
        }
        return encryptionString;
    }

    /*
     * Shift backwards by taking the difference of the number and 26 and
     * adding it to the original.
     */
    public String caesarDecryption(String decryptionString, int settings) {
        char[] original = decryptionString.toCharArray();
        decryptionString = "";
        for(int i = 0; i < original.length; i++) {
            char newchar;
            newchar = (char)('A' + (original[i] - 'A' + (26-settings)) % 26);
            decryptionString += newchar;
        }
        return decryptionString;
    }

    /*
     * Subtract the shift character number from the ASCII table from the original character. Add 26
     * to prevent negative numbers.
     */
    public String vigenereDecryption(String decryptionString, String settingsString) {
        char [] original = decryptionString.toCharArray();
        char [] setting = settingsString.toCharArray();
        decryptionString = "";
        int j = 0;
        for (int i = 0; i < original.length; i++) {
            char newchar;
            newchar = (char)('A' + (original[i] - setting[j] + 26) % 26);
            decryptionString += newchar;
            j++;
            j %= settingsString.length();
        }
        return decryptionString;
    }

    /*
     * Shift each letter by the number of the shift character on ASCII table.
     */
    public String vigenereEncryption(String encryptionString, String settingsString) {
        char[] original = encryptionString.toCharArray();
        char[] setting = settingsString.toCharArray();
        encryptionString = "";
        int j = 0;
        for (int i = 0; i < original.length; i++) {
            char newchar;
            newchar = (char)('A' + ((original[i] - 'A') + (setting[j] - 'A')) % 26);
            encryptionString += newchar;
            j++;
            j %= settingsString.length();
        }
        return encryptionString;
    }



    /*
     * Reverse the scytale encryption by doing the same thing except using the columns
     * as settings.
     */
    public String scytaleDecryption(String decryptionString, int settings) {
        int columns = decryptionString.length()/settings;
        decryptionString = scytaleEncryption(decryptionString, columns);
        return decryptionString;
    }

    /*
     *  Take the shift and shift the first character "n" spaces over. The second one another "n"
     *  spaces over and so on, looping through the encrypted string until all spaces are filled.
     *  If all spaces cannot be filled, use character '@' to fill in the remaining spaces.
     */
    public String scytaleEncryption(String encryptionString, int settings) {
        encryptionString = encryptionString.toUpperCase();
        while (encryptionString.length() % settings != 0) {
            encryptionString += "@";
        }
        if(settings >= 0 && settings <= encryptionString.length()-1) {
            int columns = encryptionString.length()/settings;
            char[] original = encryptionString.toCharArray();
            encryptionString = "";
            for (int i = 0; i < columns; i++) {
                for (int j = i; j < original.length; j+= columns) {
                    encryptionString += original[j];
                }
            }
        }
        encryptionString = encryptionString.replaceAll(" ","");
        encryptionString = encryptionString.replaceAll("[^a-zA-Z]","");
        return encryptionString;
    }

}










