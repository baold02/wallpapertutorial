package com.example.wallpapertutorial.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wallpapertutorial.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class setwallpaper extends AppCompatActivity {
    private static final int REQUEST_PEMISSION_CODE = 10;
    Intent intent;
    ImageView imageView;
    AsyncTask asyncTask;
    Bitmap bitmap;

    private ProgressDialog progressDialog;
    BitmapDrawable bitmapDrawable;

    Button set ;
    Button down;
    URL url;
    String url1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setwallpaper);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        set = findViewById(R.id.set);
        down = findViewById(R.id.down);
        imageView = findViewById(R.id.finaliimage);
        intent = getIntent();

         url1 = intent.getStringExtra("image");
        Glide.with(getApplicationContext()).load(url1).into(imageView);


        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                  asyncTask = new  DownLoadTask().execute(StringToUrl());
                    checkPermission();
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //Cach 2

//    private class DownLoadTask extends AsyncTask<URL,Void,Bitmap>{
//        @Override
//        protected void onPreExecute() {
//            progressDialog = ProgressDialog.show(setwallpaper.this,"Download iamge","Please Wait...",false,false);
//        }
//
//        @Override
//        protected Bitmap doInBackground(URL... urls) {
//            URL url = urls[0];
//            HttpURLConnection connection = null ;
//            try{
//                connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//                InputStream inputStream = connection.getInputStream();
//                BufferedInputStream bufferedInputStream= new BufferedInputStream(inputStream);
//                return BitmapFactory.decodeStream(bufferedInputStream);
//
//            }catch (IOException e){
//                e.printStackTrace();
//
//            }
//
//            return null;
//        }
//
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            progressDialog.dismiss();
//            if(bitmap != null){
//                DownloadImage();
//
//            }else{
//                Toast.makeText(setwallpaper.this, "Error", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void DownloadImage() {
//        bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
//        bitmap  = bitmapDrawable.getBitmap();
//        FileOutputStream outputStream = null;
//
//        File sdCard = Environment.getExternalStorageDirectory();
//        File Directory = new File(sdCard.getAbsoluteFile() + "/Download");
//        Directory.mkdir();
//
//        String  fileName = String.format("%d.jpg",System.currentTimeMillis());
//        File outFile = new File(Directory,fileName);
//
//        Toast.makeText(this, "Image download successfully", Toast.LENGTH_SHORT).show();
//        try{
//            outputStream = new FileOutputStream(outFile);
//            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
//            outputStream.flush();
//            outputStream.close();
//            Intent intent1 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//            intent1.setData(Uri.fromFile(outFile));
//            sendBroadcast(intent1);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//
//    protected  URL StringToUrl(){
//        try{
//           url = new URL(url1);
//           return url;
//        }catch (MalformedURLException e){
//            e.printStackTrace();
//        }
//        return null;
//    }

    private void checkPermission(){
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                   String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                   requestPermissions(permission, REQUEST_PEMISSION_CODE);
            }else {
                startDownloadFile();
            }

        }else {
            startDownloadFile();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PEMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownloadFile();
            } else {
                Toast.makeText(this, "Thất bại không có quyền internet", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startDownloadFile() {
        DownloadManager.Request request =  new DownloadManager.Request(Uri.parse(url1));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Download Image");
        request.setDescription("DownLoad file...");

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, String.valueOf(System.currentTimeMillis()));


        DownloadManager  downloadManager =(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if(downloadManager != null){
            downloadManager.enqueue(request);
        }
    }
}