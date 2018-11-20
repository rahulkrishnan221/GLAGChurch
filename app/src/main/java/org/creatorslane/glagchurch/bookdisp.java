package org.creatorslane.glagchurch;


import android.*;
import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import static org.creatorslane.glagchurch.language.preference;
import static org.creatorslane.glagchurch.language.saveit;

public class bookdisp extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Bitmap z;
    ArrayList<book> books=new ArrayList<>();
    ArrayList<String> myDataset=new ArrayList<>();
    ArrayList<String> myDataset1=new ArrayList<>();
    private int STORAGE_PERMISSION_CODE=1;
    String storagebabu="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new fetchbook().execute();
        setContentView(R.layout.activity_bookdisp);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView



        // specify an adapter (see also next example)

        // myDataset.add("https://firebasestorage.googleapis.com/v0/b/test-150af.appspot.com/o/temp6.jpeg?alt=media&token=ce44356f-26d9-4571-9669-febde42f4796");
        //   myDataset.add("https://firebasestorage.googleapis.com/v0/b/test-150af.appspot.com/o/temp7.jpeg?alt=media&token=fe7230ab-bd01-4847-8c0b-256851606e10");
        //  myDataset.add("https://firebasestorage.googleapis.com/v0/b/test-150af.appspot.com/o/temp8.jpeg?alt=media&token=46f0c2ef-2d6b-47cc-a885-e59ce7b630bf");
        // myDataset1.add("test1");
        // myDataset1.add("test2");
        // myDataset1.add("test3");







    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.bookdispmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.lang:
                startActivity(new Intent(bookdisp.this,language.class));
                return true;
            case R.id.mute:
                NotificationManager notificationManager =
                        (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && !notificationManager.isNotificationPolicyAccessGranted()) {

                    Intent intent = new Intent(
                            android.provider.Settings
                                    .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

                    startActivity(intent);
                }
                else
                {
                    AudioManager audioManager=(AudioManager)getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                    switch (audioManager.getRingerMode())
                    {
                        case AudioManager.RINGER_MODE_SILENT:
                            item.setIcon(R.drawable.speaker);
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            Toast.makeText(this, "Silent mode Deactivated", Toast.LENGTH_SHORT).show();
                            break;
                        case AudioManager.RINGER_MODE_NORMAL:
                            item.setIcon(R.drawable.mute);
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                            Toast.makeText(this, "Silent mode Activated", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            item.setIcon(R.drawable.mute);
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                            Toast.makeText(this, "Silent mode Activated", Toast.LENGTH_SHORT).show();
                    }



                }


                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    void last()
    {
        mRecyclerView=(RecyclerView)findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(myDataset,myDataset1);
        mRecyclerView.setAdapter(mAdapter);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private ArrayList<String> image;
        private ArrayList<String> name;


        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView mTextView;
            public ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                mTextView = itemView.findViewById(R.id.album_title);
                imageView = itemView.findViewById(R.id.album);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<String> image, ArrayList<String> name) {
            this.image = image;
            this.name = name;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.album_layout, parent, false);

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            //     new fetchlang().execute(image[position]);

            // holder.imageView.setImageBitmap(z);
            Picasso.with(getApplicationContext()).load(image.get(position)).into(holder.imageView);
            holder.mTextView.setText(name.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ContextCompat.checkSelfPermission(bookdisp.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)  ==PackageManager.PERMISSION_GRANTED)
                    {
                        book ob=books.get(position);
                        Intent i=new Intent(bookdisp.this,MainActivity.class);
                        i.putExtra("file",ob.getStorage());
                        startActivity(i);
                    }
                    else {
                        book ob=books.get(position);
                        storagebabu= ob.getStorage();
                        requestStoragePermission();

                    }



                }
            });
        }

        @Override
        public int getItemCount() {
            return image.size();
        }
    }
    class fetchbook extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                SharedPreferences sf4=getSharedPreferences(preference, Context.MODE_PRIVATE);
                String lang = sf4.getString(saveit,"");
                JSONArray json = jsonbookfinder.readJsonFromUrl("http://workshop.creatorslane.org/Wesley/Books/dir.php");
                for (int i=0;i<json.length();i++)
                {

                    JSONArray j1= (JSONArray) json.getJSONObject(i).get("Languages");
                    int count=0;
                    for (int j=0;j<j1.length();j++)
                    {

                        if (j1.get(j).toString().equals(lang)) {
                            count++;
                        }
                    }
                    if (count==1) {
                        urlHelper u=new urlHelper();
                        books.add(new book(json.getJSONObject(i).get("Name").toString(),lang,u.ToBook(json.getJSONObject(i).get("Name").toString(),lang,json.getJSONObject(i).get("Files").toString()),u.ToCoverArt(json.getJSONObject(i).get("Name").toString(),lang)));
                    }


                }


            }
            catch (Exception r)
            {
                System.err.println(r);

            }

            return null;


        }
        @Override
        protected void onPostExecute(Void result)
        {
            for(book x:books) {
                myDataset.add(x.getCover());
                myDataset1.add(x.getname());

            }

            last();

        }


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }
    private void requestStoragePermission()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {

            ActivityCompat.requestPermissions(bookdisp.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

        }
        else
        {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)
        {
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Intent i=new Intent(bookdisp.this,MainActivity.class);
                i.putExtra("file",storagebabu);
                startActivity(i);
            }
            else
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
        }
    }
}
