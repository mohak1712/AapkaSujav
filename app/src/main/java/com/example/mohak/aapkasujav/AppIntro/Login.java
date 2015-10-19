package com.example.mohak.aapkasujav.AppIntro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mohak.aapkasujav.AppIntro.RecyclerV.Recycler;
import com.example.mohak.aapkasujav.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class Login extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getSharedPreferences("Mytxt",MODE_PRIVATE);
        if (pref.getBoolean("Cool", true)) {

            setContentView(R.layout.activity_login);
            button = (Button) findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    IntentIntegrator integrator = new IntentIntegrator(Login.this);
                    integrator.initiateScan();


                }
            });
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("Cool", false);
            editor.apply();

        } else {
            Intent intent = new Intent(Login.this, Recycler.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("res",""+ result.getContents());
            } else {
                Log.d("res", result.getContents());
                String name, gender;
                String uid;
                JSONObject jsonObj = null;
                try {
                    jsonObj = XML.toJSONObject(result.getContents());
                    Log.d("ishaan", jsonObj.toString());
                    JSONObject object = jsonObj.getJSONObject("PrintLetterBarcodeData");
                    name = object.getString("name");
                    uid = object.getString("uid");
                    gender = object.getString("gender");
                    Log.d("ishaan", name);
                    Log.d("ishaan", gender);
                    Log.d("ishaan", uid);
                    Intent intent = new Intent(Login.this, Form.class);
                    intent.putExtra("name", name);
                    intent.putExtra("uid", uid);
                    intent.putExtra("gender", gender);
                    startActivity(intent);

//                        SharedPreferences.Editor editor = pref.edit();
//                        editor.putBoolean("k",false);
//                        editor.apply();
                    //JSONObject object = jsonObj.getJSONObject("balancecheck");
                    //a = object.getInt("balance");
                } catch (JSONException e) {
                    Log.e("JSON exception", e.getMessage());
                    e.printStackTrace();
                }


            }

                    /*
                    Log.d("XML", result);
                    String usingiam=jsonObj.toString();
                    Log.d("JSON", usingiam);*/


        }


    }
}