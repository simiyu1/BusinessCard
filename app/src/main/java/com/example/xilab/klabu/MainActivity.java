package com.example.xilab.klabu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    TextView myloc, myhours, mycont;
    Button inviter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myloc= (TextView)findViewById(R.id.myloc);
        myhours= (TextView)findViewById(R.id.myhours);
        mycont= (TextView)findViewById(R.id.mycont);
        inviter=(Button) findViewById(R.id.inviter);

        myloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=-1.2061610,36.8944019");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        mycont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int REQUEST_CODE_ASK_PERMISSION=123;
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0700000000"));
                if(ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_PERMISSION);
                    }

                    return;
                }
                startActivity(callIntent);
            }
        });

        inviter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*final Intent shareIntent= new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                final File photoFile= new File(getFilesDir(),"findme.png");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
                startActivity(Intent.createChooser(shareIntent, "Nipate 3 cups Lounge.."));*/


                /*Uri image=Uri.parse(new StringBuilder().append("android.resource://com.example.xilab.klabu/res/drawable/").append(R.drawable.findme).toString());
                Intent intent =new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                //intent.putExtra(Intent.EXTRA_STREAM, image);
                intent.putExtra(Intent.EXTRA_TEXT, "Waiting for you");

                //Chooser chooser=Intent.createChooser(intent, "Send Image");
                startActivity(Intent.createChooser(intent, "Invite Friends"));*/

                final int REQUEST_CODE_ASK_PERMISSIONS =123;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    int hasWriteStorage = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if(hasWriteStorage != PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
                        return;
                    }
                }


                Bitmap bitmap;
                OutputStream output;
                bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.joinme);
                //InputStream inputStream= getApplicationContext().getResources().openRawResource(R.drawable.findme);
                File filepath = Environment.getExternalStorageDirectory();
                File dir = new File(filepath.getAbsolutePath()+"/Klabu/");
                dir.mkdirs();
                File file = new File(dir,"joinme.png");
                try{
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/*");
                    output=new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,output);
                    output.flush();
                    output.close();
                    Uri uri = Uri.fromFile(file);
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(share,"Invite Friends!!"));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                return;

                /*Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                String imagePath= Environment.getRootDirectory()+"findme.png";
                //etExternalStorageDirectory() + "/findme.png";
                File imageFileToShare = new File(imagePath);
                Uri uri= Uri.fromFile(imageFileToShare);
                share.putExtra(Intent.EXTRA_STREAM, uri);

                startActivity(Intent.createChooser(share,"Invite Friends!!"));*/
            }
        });
    }


}
