package algonquin.cst2335.Application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InfoClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);

        Context context = getApplicationContext();
        ImageButton route = findViewById(R.id.busButton);
        route.setOnClickListener(clck -> {
            finish();

            Toast toast = Toast.makeText(context, "Routes...", Toast.LENGTH_SHORT);
            toast.show();
        });
    }
}
