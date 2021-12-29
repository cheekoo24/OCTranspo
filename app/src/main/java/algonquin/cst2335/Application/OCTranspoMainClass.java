package algonquin.cst2335.Application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

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
    private ArrayList<OCBusData> busList;
    private RecyclerView recyclerView;
    private OCRecyclerAdapter.RecyclerViewClickListener listener;
    private String stringURL;
    private EditText search;
    private static String input;
    private String description;


    public String getInput() {
        return input;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocmain_layout);
        TextView desc = findViewById(R.id.description);
        Context context = getApplicationContext();
        recyclerView = findViewById(R.id.recyclerView);
        busList = new ArrayList<>();
        search = findViewById(R.id.searchEdit);
        Button enter = findViewById(R.id.enter);
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String pref = prefs.getString("newItem", "");
        search.setText(pref);

        enter.setOnClickListener(clck -> {
            EditText et = findViewById(R.id.searchEdit);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString("newItem", et.getText().toString());
            edit.apply();

            input = search.getText().toString();
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
            Executor newThread = Executors.newSingleThreadExecutor();
            newThread.execute(() -> {
                        setBusData();
                        runOnUiThread(() -> {
                            setAdapter();
                        });
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Bus Stops Information", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.darker_gray ))
                        .show();
                    });
            desc.setText(description);
            desc.setVisibility(View.VISIBLE);
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

    /**
     * This function is used to set an adapter to display items on recycler views
     * @author Herman Redona
     * @version 1.0
     */
    private void setAdapter() {
        setOnClickListener();
        OCRecyclerAdapter adapter = new OCRecyclerAdapter(busList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new OCRecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), OCBusInfoClass.class);
                intent.putExtra("routeNum", busList.get(position).getRouteNum());
                intent.putExtra("routeName", busList.get(position).getRouteName());
                startActivity(intent);
            }
        };
    }

    /**
     * This function is used to add data that will be posted on the recyclerview
     * @author Herman Redona
     * @version 1.0
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setBusData() {
            try {
                stringURL = "https://api.octranspo1.com/v2.0/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo="
            + input;
                //Convet String text to JSONObject
                JSONObject theDocument = new JSONObject(connectTo(stringURL));
                JSONObject routeSummary = theDocument.getJSONObject("GetRouteSummaryForStopResult");
                description = routeSummary.getString("StopDescription");
                JSONObject routes = routeSummary.getJSONObject("Routes");
                JSONArray routeArray = routes.getJSONArray("Route");
                for (int i = 0; i < routeArray.length(); i++) {
                    JSONObject object = routeArray.getJSONObject(i);
                    busList.add(new OCBusData(object.getString("RouteNo"), object.getString("RouteHeading")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

       /* for (int i = 0; i < 10; i++) {
            busList.add(new OCBusData("Hi", "Hello"));
        }*/
    }

    /**
     * This function is used to connect to the web server
     * @author Herman Redona
     * @version 1.0
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String connectTo(String stringUrl) {
        InputStream in = null;
        try {

            //Connecting to Server
            URL url = new URL(stringUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
             in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Convert InputStream into String
        return (new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8)))
                .lines()
                .collect(Collectors.joining("\n"));
    }
}
