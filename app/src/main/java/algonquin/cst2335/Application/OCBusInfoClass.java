package algonquin.cst2335.Application;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

public class OCBusInfoClass extends OCTranspoMainClass {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocbusinfo_layout);

        EditText editSearch = findViewById(R.id.search);

        Button exit = findViewById(R.id.exitButton);
        exit.setOnClickListener( clck -> {

            Intent nextPage = new Intent(OCBusInfoClass.this, OCTranspoMainClass.class);
            startActivity(nextPage);
        });
    }
}
