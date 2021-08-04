package algonquin.cst2335.Application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OCTranspoMainClass extends AppCompatActivity {

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
                EditText es = findViewById(R.id.editTextSearch);
                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("SearchedItem", es.getText().toString());
                edit.apply();

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
