package algonquin.cst2335.Application;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OCFavouriteClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocfavourites_layout);
        Context context = getApplicationContext();

        //Bus/Route Button
        ImageView route = findViewById(R.id.favBusButton);
        route.setOnClickListener( clck -> {
            Intent nextPage = new Intent(OCFavouriteClass.this, OCTranspoMainClass.class);
            startActivity(nextPage);

            Toast toast = Toast.makeText(context, "Routes...", Toast.LENGTH_SHORT);
            toast.show();
        });

        //Info Button
        ImageView info = findViewById(R.id.favInfoButton);
        info.setOnClickListener(clck -> {
            Intent nextPage = new Intent(OCFavouriteClass.this, OCInfoClass.class);
            startActivity(nextPage);

            Toast toast = Toast.makeText(context, "Info...", Toast.LENGTH_SHORT);
            toast.show();
        });
    }
}
