package algonquin.cst2335.Application;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class OCTranspoMainClass extends AppCompatActivity {

    private String stringURL;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocmain_layout);

        Context context = getApplicationContext();

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String searchedItem = prefs.getString("SearchedItem", "");


        EditText editSearch = findViewById(R.id.editTextSearch);
        editSearch.setText(searchedItem);
        editSearch.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("SearchedItem", editSearch.getText().toString());
                edit.apply();

                AlertDialog dialog = new AlertDialog.Builder(OCTranspoMainClass.this)
                        .setTitle("Attention!")
                        .setMessage("\n\nPlease wait while trying to get the info")
                        .setView(new ProgressBar(OCTranspoMainClass.this))
                        .show();

                setContentView(R.layout.ocbusinfo_layout);

                TextView busInfo = findViewById(R.id.busNumInfo);
                TextView busNum = findViewById(R.id.getBusNum);
                //Show Visibiltiy for Bus Info


                busInfo.setVisibility(View.VISIBLE);
                busNum.setVisibility(View.VISIBLE);
                busNum.setText(editSearch.getText());


                //Connect To Web and Get the Information for GetRouteSummaryForStop
                Executor newThread = Executors.newSingleThreadExecutor();

                int busEntered = Integer.parseInt(editSearch.getText().toString());
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
                        JSONObject routeIndex = routeArray.getJSONObject(0);
                        String routeNo = routeIndex.getString("RouteNo"); //RouteNo value.


                        //Connecting to Server
                        URL urlNext = new URL("https://api.octranspo1.com/v2.0/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo="
                                + busEntered + "&RouteNo=" + routeNo);
                        HttpURLConnection urlConnectionNext = (HttpURLConnection) urlNext.openConnection();
                        InputStream inNext = new BufferedInputStream(urlConnectionNext.getInputStream());

                        //Convert in2 into String
                        String textNext = (new BufferedReader(
                                new InputStreamReader(inNext, StandardCharsets.UTF_8)))
                                .lines()
                                .collect(Collectors.joining("\n"));

                        //Convert text2 String into JSONObject
                        JSONObject theDocument2 = new JSONObject(textNext);
                        JSONObject nextTrip = theDocument2.getJSONObject("GetNextTripsForStopResult");
                        JSONObject routeNext = nextTrip.getJSONObject("Route");
                        JSONArray routeDirArray = routeNext.getJSONArray("RouteDirection");
                        JSONObject indexDir = routeDirArray.getJSONObject(0);
                        JSONObject trips = indexDir.getJSONObject("Trips");
                        JSONArray tripArray = trips.getJSONArray("Trip");
                        JSONObject indexTrip = tripArray.getJSONObject(0);
                        String tripDis = indexTrip.getString("TripDestination");
                        String latitude = indexTrip.getString("Latitude");
                        String longitude = indexTrip.getString("Longitude");
                        String gps = indexTrip.getString("GPSSpeed");
                        String startTime = indexTrip.getString("TripStartTime");
                        String adjustTime = indexTrip.getString("AdjustedScheduleTime");

                        TextView tripDest = findViewById(R.id.tripDest);
                        TextView lat = findViewById(R.id.latitude);
                        TextView longi = findViewById(R.id.longitude);
                        TextView gpsSpeed = findViewById(R.id.gps);
                        TextView startT = findViewById(R.id.startTime);
                        TextView adjustT = findViewById(R.id.adjustedTime);

                        runOnUiThread(() -> {

                            tripDest.setText(tripDis);
                            tripDest.setVisibility(View.VISIBLE);

                            lat.setText(latitude);
                            lat.setVisibility(View.VISIBLE);

                            longi.setText(longitude);
                            longi.setVisibility(View.VISIBLE);

                            gpsSpeed.setText(gps);
                            gpsSpeed.setVisibility(View.VISIBLE);

                            startT.setText(startTime);
                            startT.setVisibility(View.VISIBLE);

                            adjustT.setText(adjustTime);
                            adjustT.setVisibility(View.VISIBLE);
                        });

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                });



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
}
