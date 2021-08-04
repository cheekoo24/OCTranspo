package algonquin.cst2335.Application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OCFeedbackClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocfeedback_layout);

        EditText name = findViewById(R.id.feedback_name);
        EditText feedback = findViewById(R.id.feedback_message);
        Button send = findViewById(R.id.feedback_button);
        send.setOnClickListener(clck -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/html");
            i.putExtra(Intent.EXTRA_EMAIL, new String("redo0001@algonquinlive.com"));
            i.putExtra(Intent.EXTRA_SUBJECT, "Feedback From App");
            i.putExtra(Intent.EXTRA_TEXT, "Name: " + name.getText() +"\n Message: " + feedback.getText());
            try {
                startActivity(Intent.createChooser(i, "Please select Email"));
            } catch (android.content.ActivityNotFoundException e){
                Toast.makeText(OCFeedbackClass.this, "There are no Email Clients", Toast.LENGTH_SHORT).show();

            }
            finish();
        });
    }
}
