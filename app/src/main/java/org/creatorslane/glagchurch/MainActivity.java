package org.creatorslane.glagchurch;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.folioreader.Config;
import com.folioreader.FolioReader;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    Long refid;
    File fx1;
    File fx2;
    String tempx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Hold on......", true, true);
        setContentView(R.layout.activity_main);
        String value = getIntent().getStringExtra("file");

        System.err.println(value);
        String temp = "";
        int pos = 0;
        String language = "";
        String language1 = "";
        String bookname = "";
        String bookname1 = "";
        String temp1 = "";
        for (int i = value.length() - 1; i >= 0; i--) {
            if (value.charAt(i) == '/') {
                pos = i;
                break;
            } else
                temp = temp + value.charAt(i);

        }
        temp1 = reverser(temp);

        for (int i = pos - 1; i >= 0; i--) {
            if (value.charAt(i) == '/') {
                pos = i;
                break;
            } else
                language = language + value.charAt(i);
        }
        language1 = reverser(language);

        for (int i = pos - 1; i >= 0; i--) {
            if (value.charAt(i) == '/') {
                pos = i;
                break;
            } else
                bookname = bookname + value.charAt(i);
        }
        bookname1 = reverser(bookname);
        temp1 = bookname1 + "_" + temp1;
        File storageDir = new File(
                String.valueOf(Environment.getExternalStoragePublicDirectory("Ebooks" + "/" + language1 + "/" + bookname1))
        );
        if (storageDir.exists())
            System.err.println("Do Nothing");
        if (!storageDir.exists())
            storageDir.mkdirs();

        System.err.println(storageDir.getPath());


        File fx = new File(String.valueOf(Environment.getExternalStoragePublicDirectory("Ebooks" + "/" + language1 + "/" + bookname1 + "/" + temp1)));
        if (!fx.exists()) {

            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(value);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            //  request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir("Ebooks" + "/" + language1 + "/" + bookname1, temp1);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            refid = downloadManager.enqueue(request);
            DownloadManager.Query quer = new DownloadManager.Query();
            quer.setFilterById(refid);
            Cursor cursor = downloadManager.query(quer);
            int d = 0;
            if (cursor.moveToFirst()) {
                d = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);

            }

            fx1=fx;
            fx2=fx;
            tempx=temp1;

            IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            registerReceiver(new Download(), intentFilter);



        }
        if (fx.exists())
        {
            test(fx,fx,temp1);
        }



    }



    public String reverser(String z)
    {
        String z1="";
        for (int i=z.length()-1;i>=0;i--)
            z1=z1+z.charAt(i);
        return z1;
    }
    void test(File fx,File f,String temp1)
    {
        if (fx.exists()) {
            progressDialog.dismiss();
            System.err.println("Kaam set");
            if(fx.getPath().contains(".epub")) {

                Config config = new Config().setThemeColor(R.color.colorPrimaryDark);
                FolioReader folioReader = FolioReader.getInstance(getApplicationContext());
                System.out.println(fx.getPath());
                folioReader.setConfig(config, true).openBook(fx.getPath());
                finish();
            }
            else
            {
                Intent i=new Intent(MainActivity.this,pdfreader.class);
                i.putExtra("pdf",fx.getPath());
                finish();
                startActivity(i);

            }
        }

    }
    private class Download extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long ref=intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
            if(ref == MainActivity.this.refid)
            {
                Toast toast=Toast.makeText(getApplicationContext(),"Download complete",Toast.LENGTH_LONG);
                toast.show();
                if (fx1.exists())

                    test(fx1,fx2,tempx);

            }
            //MainActivity.this.updateButton();
        }
    }




}


