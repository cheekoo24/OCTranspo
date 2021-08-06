package algonquin.cst2335.Application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class OCTranspoMainClass extends AppCompatActivity {

    private String stringURL;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> routeNo = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    BusInfo data;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocmain_layout);

        Context context = getApplicationContext();
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String searchedItem = prefs.getString("SearchedItem", "");


        listView = findViewById(R.id.listView);
        EditText search = findViewById(R.id.search);


        search.setText(searchedItem);
        search.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("SearchedItem", search.getText().toString());
                edit.apply();



                Executor newThread = Executors.newSingleThreadExecutor();
                int busEntered = Integer.parseInt(search.getText().toString());
                newThread.execute(() -> {
                try {
                    stringURL = "https://api.octranspo1.com/v2.0/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo="
                            + busEntered;

                    //Connecting to Server
                    URL url = new URL(stringURL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    //Convert InputStream into String
                    String text = (new BufferedReader(
                            new InputStreamReader(in, StandardCharsets.UTF_8)))
                            .lines()
                            .collect(Collectors.joining("\n"));

                    //Convet String text to JSONObject
                    JSONObject theDocument = new JSONObject(text);
                    JSONObject routeSummary = theDocument.getJSONObject("GetRouteSummaryForStopResult");
                    JSONObject routes = routeSummary.getJSONObject("Routes");
                    JSONArray routeArray = routes.getJSONArray("Route");
                    for (int i = 0; i < routeArray.length(); i++) {
                        JSONObject object = routeArray.getJSONObject(i);
                        data = new BusInfo(object.getString("RouteNo"),object.getString("RouteHeading") );
                        arrayList.add("Route: " + data.getRouteNo() + "          Heading to: " + data.getRouteHeading());
                        routeNo.add(data.getRouteNo()); // getting the value of the route number for later task
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

                });

                //Initialize Array Adapter
                arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);

                //Set Array Adapater to RecyclerView
                listView.setAdapter(arrayAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Toast.makeText(getApplicationContext()
                        , arrayList.get(position), Toast.LENGTH_SHORT).show();
                        //routeNo.get(position) //to get the Route number
                    }
                });
                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                return true;
            }
            return false;
        });


        //Info Button
        ImageView route = findViewById(R.id.infoButton);
        route.setOnClickListener(clck -> {
            Intent nextPage = new Intent(OCTranspoMainClass.this, OCInfoClass.class);
            startActivity(nextPage);

            Toast toast = Toast.makeText(context, "Info...", Toast.LENGTH_SHORT);
            toast.show();
        });

        //Favourites Button
        ImageView favourite = findViewById(R.id.mainFavourite);
        favourite.setOnClickListener( clck -> {
            Intent nextPage = new Intent(OCTranspoMainClass.this, OCFavouriteClass.class);
            startActivity(nextPage);

            Toast toast = Toast.makeText(context, "Favourites...", Toast.LENGTH_SHORT);
            toast.show();
        });
    }

    private class BusInfo {
        public String routeNo;
        public String routeHeading;

        public BusInfo(String routeNo, String routeHeading) {
            this.routeNo = routeNo;
            this.routeHeading = routeHeading;
        }

        public String getRouteNo() {
            return routeNo;
        }

        public String getRouteHeading() {
            return routeHeading;
        }
    }
}
