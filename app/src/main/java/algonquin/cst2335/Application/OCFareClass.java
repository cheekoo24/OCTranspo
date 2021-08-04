package algonquin.cst2335.Application;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OCFareClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocfares_layout);

        Button done = findViewById(R.id.button_fare);
        done.setOnClickListener( clck -> {
            finish();
        });
    }
}
