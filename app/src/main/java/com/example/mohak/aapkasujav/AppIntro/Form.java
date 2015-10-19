package com.example.mohak.aapkasujav.AppIntro;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohak.aapkasujav.AppIntro.RecyclerV.Recycler;
import com.example.mohak.aapkasujav.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Form extends AppCompatActivity {

    TextView t1, t2, t3;
    Person person;
    String name, uid, sex;
    File folder,myfile;
    CircleImageView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form2);
        t1 = (TextView) findViewById(R.id.textView4);
        t2 = (TextView) findViewById(R.id.textView5);
        t3 = (TextView) findViewById(R.id.textView6);
        button = (CircleImageView) findViewById(R.id.profile_image);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        uid = intent.getStringExtra("uid");
        sex = intent.getStringExtra("gender");
        t1.setText(name);
        t2.setText(sex);
        t3.setText(uid);
        Boolean isSDPresent = Environment.getExternalStorageState().
                equals(Environment.MEDIA_MOUNTED);
        if(isSDPresent)
        {    // WRITING TO SD CARD
            //Location of file in SD Card = com.example.mohak.aapkasujav/folder/uid.txt
            folder = getExternalFilesDir("AapkaSujav");
            myfile = new File(folder, "id.txt");
            writeTofile(myfile, uid);
        }else {

            writeTointernal(uid);
        }

        person = new Person();
        person.setSex(sex);
        person.setUid(uid);
        person.setName(name);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login(person.getName(), person.getSex(), person.getUid());
                takeme();
                //new HttpAsyncTask().execute("192.168.4.176:3000/logreg");

            }
        });
    }

    private void writeTointernal(String uid) {

        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput("id.txt", Context.MODE_PRIVATE);
            outputStream.write(uid.getBytes());
        } catch (IOException e) {
            e.printStackTrace();

        }

        finally {
            if(outputStream!=null)
            {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void writeTofile(File myfile, String uid) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(myfile);
            outputStream.write(uid.getBytes());
//            Log.d("Write",""+uid.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


    private void login(String name, String sex, String uid) {
        String url = "http://myrestapiish.meteor.com/logreg";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name", name);
            jsonObject.put("uid", uid);
            jsonObject.put("sex", sex);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestQueue queue = Volley.newRequestQueue(Form.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                com.android.volley.Request.Method.POST, url,
                jsonObject,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.v("reponse", "" + response);


                    }


                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (error != null) {


                    Log.d("ishaan", error.toString());


                }
            }
        });

        queue.add(jsonObjectRequest);


    }

    private void takeme() {
        Intent intent = new Intent(Form.this, Recycler.class);
        intent.putExtra("uid_recycler",uid);
        startActivity(intent);
    }
}


