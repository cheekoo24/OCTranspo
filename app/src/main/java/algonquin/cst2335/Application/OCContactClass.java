package algonquin.cst2335.Application;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OCContactClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.occontact_layout);

        Button done = findViewById(R.id.doneButton);
        done.setOnClickListener(clck -> {
            finish();
        });

        //make the links work
        TextView link = findViewById(R.id.linkedin);
        link.setMovementMethod(LinkMovementMethod.getInstance());

        TextView fb = findViewById(R.id.facebook);
        fb.setMovementMethod(LinkMovementMethod.getInstance());

        TextView insta = findViewById(R.id.instagram);
        insta.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
