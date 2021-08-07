package algonquin.cst2335.Application;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class OCBusInfoClass extends OCTranspoMainClass {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocbusinfo_layout);
        TextView routeNumer = findViewById(R.id.routeNo);
        TextView label = findViewById(R.id.label);
        TextView tripDes = findViewById(R.id.tripDest);
        TextView longitude = findViewById(R.id.longitude);
        TextView latitude = findViewById(R.id.latitude);
        TextView gps = findViewById(R.id.gps);
        TextView start = findViewById(R.id.startTime);
        TextView adjust = findViewById(R.id.adjustedTime);

        String routeNo = "";
        String routeName = "";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            routeNo = extras.getString("routeNum");
            routeName = extras.getString("routeName");
        }

        //incomplete need for stopno
        String url = "https://api.octranspo1.com/v2.0/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=" + Integer.parseInt(getInput()) + "&RouteNo=" + routeNo;

        routeNumer.setText(routeNo);
        label.setText(routeName);

        Executor newThread = Executors.newSingleThreadScheduledExecutor();
        String finalRouteName = routeName;
        String finalRouteNo = routeNo;
        newThread.execute( () -> {
            try {
                URL urls = new URL(url);
                HttpURLConnection urlConnectionNext = (HttpURLConnection) urls.openConnection();
                InputStream inNext = new BufferedInputStream(urlConnectionNext.getInputStream());

                //Convert in2 into String
                String textNext = (new BufferedReader(
                        new InputStreamReader(inNext, StandardCharsets.UTF_8)))
                        .lines()
                        .collect(Collectors.joining("\n"));

                JSONObject theDocument2 = new JSONObject(textNext);
                JSONObject nextTrip = theDocument2.getJSONObject("GetNextTripsForStopResult");
                JSONObject routeNext = nextTrip.getJSONObject("Route");
                JSONArray routeDirArray = routeNext.getJSONArray("RouteDirection");
                int index = 0;
                for (int i = 0; i < routeDirArray.length(); i++) {
                    if (finalRouteName.contains(routeDirArray.getJSONObject(i).getString("RouteLabel"))
                            && finalRouteNo.contains(routeDirArray.getJSONObject(i).getString("RouteNo"))){
                        index = i;
                    }
                }
                JSONObject indexDir = routeDirArray.getJSONObject(index);
                JSONObject trips = indexDir.getJSONObject("Trips");
                JSONArray tripArray = trips.getJSONArray("Trip");
                JSONObject indexTrip = tripArray.getJSONObject(0);
                String tripDis = indexTrip.getString("TripDestination");
                String lat = indexTrip.getString("Latitude");
                String longi = indexTrip.getString("Longitude");
                String gpsSpeed = indexTrip.getString("GPSSpeed");
                String startTime = indexTrip.getString("TripStartTime");
                String adjustTime = indexTrip.getString("AdjustedScheduleTime");


                runOnUiThread(() -> {
                    tripDes.setText(tripDis);
                    tripDes.setVisibility(View.VISIBLE);
                    latitude.setText(lat);
                    latitude.setVisibility(View.VISIBLE);
                    longitude.setText(longi);
                    longitude.setVisibility(View.VISIBLE);
                    gps.setText(gpsSpeed);
                    gps.setVisibility(View.VISIBLE);
                    start.setText(startTime);
                    start.setVisibility(View.VISIBLE);
                    adjust.setText(adjustTime);
                    adjust.setVisibility(View.VISIBLE);
                });

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
                });

        Button exit = findViewById(R.id.exitButton);
        exit.setOnClickListener( clck -> {

            Intent nextPage = new Intent(OCBusInfoClass.this, OCTranspoMainClass.class);
            startActivity(nextPage);
        });
    }
}
