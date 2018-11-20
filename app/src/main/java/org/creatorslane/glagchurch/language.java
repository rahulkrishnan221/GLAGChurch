package org.creatorslane.glagchurch;

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.AsyncTask;
        import android.support.v4.widget.SwipeRefreshLayout;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.util.ArrayList;

        import static org.creatorslane.glagchurch.jsonhelper.readJsonFromUrl;

public class language extends AppCompatActivity {

    ListView listView;
    ProgressDialog progressDialog;
    String positionholder="";
    Button buttonlang;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<String> langkey = new ArrayList<>();
    public static final String preference="ref";
    public static final String saveit="savekey";
    SharedPreferences sf4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        sf4 = getSharedPreferences(preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3=sf4.edit();
        editor3.putString(saveit,"null");
        editor3.commit();
        listView=(ListView) findViewById(R.id.list);
        buttonlang=(Button)findViewById(R.id.langok);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                execute();

            }
        });
        buttonlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positionholder.equals(""))
                    Toast.makeText(language.this, "Please select", Toast.LENGTH_SHORT).show();
                else {
                    sf4 = getSharedPreferences(preference, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor4 = sf4.edit();
                    editor4.putString(saveit, langkey.get(Integer.parseInt(positionholder)));
                    editor4.commit();
                    //     Toast.makeText(language.this, sf4.getString(saveit,""), Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(language.this,bookdisp.class));
                }

            }
        });


        progressDialog=ProgressDialog.show(language.this,"Please wait","Hold on......",true,true);

        execute();



    }

    public void execute()
    {
        new fetchlang().execute();
        swipeRefreshLayout.setRefreshing(false);
    }
    void views(ArrayList<String> test)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, test);
        listView.setAdapter(adapter);
        progressDialog.dismiss();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionholder=Integer.toString(position);

            }
        });

    }
    class fetchlang extends AsyncTask<Void,Void,Void> {
        ArrayList<String> langname = new ArrayList<>();
        ArrayList<String> langx = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                JSONObject json = jsonhelper.readJsonFromUrl("http://workshop.creatorslane.org/Wesley/Books/LangServed.json");
                JSONArray jsonArray = (JSONArray) json.get("Languages");

                for (int i = 0; i < jsonArray.length(); i++) {
                    langx.add(jsonArray.get(i).toString());
                    if (jsonArray.get(i).toString().equals("Hindi"))
                        langname.add("हिंदी");
                    else if (jsonArray.get(i).toString().equals("Tamil"))
                        langname.add("தமிழ்");
                    else
                        langname.add(jsonArray.get(i).toString());
                }
                JSONObject jsonObject = (JSONObject) json.get("Loc");
                for (String str : langx) {
                    langkey.add(jsonObject.get(str).toString());
                }

            }
            catch (Exception e)
            {
                System.err.println(e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {

            views(langname);
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
}
