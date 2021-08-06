package algonquin.cst2335.Application;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class OCBusInfoClass extends OCTranspoMainClass {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocbusinfo_layout);

        EditText editSearch = findViewById(R.id.editTextSearch);

        Button exit = findViewById(R.id.exitButton);
        exit.setOnClickListener( clck -> {

            Intent nextPage = new Intent(OCBusInfoClass.this, OCTranspoMainClass.class);
            startActivity(nextPage);
        });
    }
}
