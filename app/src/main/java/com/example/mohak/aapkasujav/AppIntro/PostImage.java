package com.example.mohak.aapkasujav.AppIntro;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.*;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohak.aapkasujav.AppIntro.City.CleanCity;
import com.example.mohak.aapkasujav.AppIntro.City.Dialog;
import com.example.mohak.aapkasujav.AppIntro.RecyclerV.Recycler;
import com.example.mohak.aapkasujav.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PostImage extends AppCompatActivity {

    static final int PICK_CONTACT_REQUEST = 1;
    ImageView imageView;
    Button button2, button3;
    private boolean isSDPresent;
    static final int ACTIVITY_SELECT_IMAGE = 2;
    Toolbar toolbar;
    Uri fileUri;
    EditText editText;
    public static final int MEDIA_TYPE_IMAGE = 1;
    String uid2;
    private static final String IMAGE_DIRECTORY_NAME = "AapKaSujav";
    File folder, myfile;
    private String imageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_image);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        // setting the navigation drawer for swipe
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        isSDPresent = Environment.getExternalStorageState().
                equals(Environment.MEDIA_MOUNTED);
        Intent intent = getIntent();
        uid2 = intent.getStringExtra("uid_cleancity");
        if (uid2 == null && isSDPresent) {
            folder = getExternalFilesDir("AapkaSujav");
            myfile = new File(folder, "id.txt");
            uid2 = new Recycler().readFromfile(myfile);
        } else if (uid2 == null && !isSDPresent) {
            uid2 = new Recycler().readFrominternal();
            Log.d("Boolean", String.valueOf(!isSDPresent));
        }
        imageView = (ImageView) findViewById(R.id.imageView2);
        editText = (EditText) findViewById(R.id.et);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        imageView.setVisibility(View.INVISIBLE);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent2, PICK_CONTACT_REQUEST);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
            }
        });

    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }


    public void clean(String comment, String uid2, String imageString) {

        String url = "http://myrestapiish.meteor.com/ishaan/addproblem2";
        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("problem", comment);
            jsonObject.put("uid", uid2);
            jsonObject.put("image", imageString);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(PostImage.this);

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


                    Log.d("mohak", error.toString());

                }
            }
        });

        queue.add(jsonObjectRequest);


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PostImage.this, CleanCity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT_REQUEST && resultCode == RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(bitmap);
            imageString = base64(bitmap);
//            Bitmap bitmap = BitmapFactory.decode
//            imageView.setVisibility(View.VISIBLE);
//
//            imageView.setImageBitmap(bitmap);
            //       quality(photo);
            //Log.d("internal","saveto");
            // imageView.setImageBitmap(photo);
            // saveto(photo);
            // imageView.setImageBitmap(photo); gives you the thumbnail for complete image u need to
            // save it in Mohak storage or sd card and then extract it with picasso

        }
    }

    private String base64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return android.util.Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


    private void saveto(Bitmap photo) {

        Toast.makeText(this, "fgf", Toast.LENGTH_SHORT).show();
        internal(photo);
    }

    private void external(Bitmap photo) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("image.jpg");
            photo.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } catch (FileNotFoundException e) {
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

    private void internal(Bitmap photo) {
        FileOutputStream outputStream = null;
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("DCIM", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile.jpg");
        try {
            outputStream = new FileOutputStream(mypath);
            photo.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            Log.d("Mohak", "No sd");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.Post) {
            String ab = editText.getText().toString();

            if (!ab.equals("") && isSDPresent) {
                clean(ab, uid2, imageString);
                new Dialog().show(getSupportFragmentManager(), "Blah");

            } else if (ab.equals("")) {
                Toast.makeText(this, "Cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Cannot internet connection", Toast.LENGTH_SHORT).show();

            }
        }

        return super.onOptionsItemSelected(item);
    }
}
