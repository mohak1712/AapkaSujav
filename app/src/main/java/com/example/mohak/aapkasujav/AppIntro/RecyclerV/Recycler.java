package com.example.mohak.aapkasujav.AppIntro.RecyclerV;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mohak.aapkasujav.AppIntro.City.CleanCity;
import com.example.mohak.aapkasujav.AppIntro.Login;
import com.example.mohak.aapkasujav.R;

public class Recycler extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    String uid;
    File folder;
    File myfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar2);
        setSupportActionBar(toolbar);
        // setting the navigation drawer for swipe
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        Boolean isSDPresent = Environment.getExternalStorageState().
                equals(Environment.MEDIA_MOUNTED);
        uid = intent.getStringExtra("uid_recycler");
//        if(isSDPresent)
//        {    // WRITING TO SD CARD
//            //Location of file in SD Card = com.example.mohak.aapkasujav/folder/uid.txt
//            folder = getExternalFilesDir("AapkaSujav");
//            myfile = new File(folder, "id.txt");
//            writeTofile(myfile, uid);
//        }else {
//
//            writeTointernal(uid);
//        }

        if (uid == null && isSDPresent) {
            folder = getExternalFilesDir("AapkaSujav");
            myfile = new File(folder, "id.txt");
            uid = readFromfile(myfile);
        } else if (uid == null && !isSDPresent) {
            uid = readFrominternal();
            Log.d("Boolean", String.valueOf(!isSDPresent));
        }

        recyclerView = (RecyclerView) findViewById(R.id.recylerview);
        Adapter adapter = new Adapter(this, getdata());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(Recycler.this, CleanCity.class);
                        intent.putExtra("uid_cleancity", uid);
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }


            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }


    public String readFrominternal() {

        FileInputStream inputStream = null;
        try {
            inputStream = openFileInput("uid.txt");
            int read = -1;
            StringBuffer buffer = new StringBuffer();
            while ((read = inputStream.read()) != -1) {
                buffer.append((char) read);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


    public String readFromfile(File myfile) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(myfile);
            StringBuffer buffer = new StringBuffer();
            int read = -1;
            while ((read = inputStream.read()) != -1) {
                buffer.append((char) read);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if ((inputStream != null)) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


    private ArrayList<Single> getdata() {
        ArrayList<Single> data = new ArrayList<>();
        int[] icons = {R.mipmap.circular_bharat, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
        String[] title = {"Clean City", "Police Station", "Metro", "Roads"};
        for (int i = 0; i < title.length; i++) {
            Single current = new Single();
            current.icon = icons[i];
            current.title = title[i];
            data.add(current);
        }
        return data;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recycler, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {

            SharedPreferences pref = getSharedPreferences("Mytxt",MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.remove("Cool");
            editor.apply();
          //  preferences.edit().remove("lol").apply();
//            File folder = getExternalFilesDir("AapkaSujav");
//            File myfile= new File(folder,"uid.txt");
//            boolean b = myfile.delete();
//            Log.d("Delete", String.valueOf(b));
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
