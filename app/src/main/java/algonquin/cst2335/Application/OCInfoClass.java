package algonquin.cst2335.Application;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class OCInfoClass extends AppCompatActivity {
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocinfo_layout);

        //Route Button
        Context context = getApplicationContext();
        ImageView route = findViewById(R.id.infoBusButton);
        route.setOnClickListener(clck -> {
            Intent nextPage = new Intent(OCInfoClass.this, OCTranspoMainClass.class);
            startActivity(nextPage);

            Toast toast = Toast.makeText(context, "Routes...", Toast.LENGTH_SHORT);
            toast.show();
        });

        //Favourites Button
        ImageView favourite = findViewById(R.id.infoFavButton);
        favourite.setOnClickListener( clck -> {
            Intent nextPage = new Intent(OCInfoClass.this, OCFavouriteClass.class);
            startActivity(nextPage);

            Toast toast = Toast.makeText(context, "Favourites...", Toast.LENGTH_SHORT);
            toast.show();
        });

        //Help Button/Text
        ImageView help = findViewById(R.id.helpimage);
        builder = new AlertDialog.Builder(this);
        String dialogMessage =
                "<br /><b>Search Bar</b> - Type the bus number in the search bar to get the information for the bus.<br /><br />" +
                "<b>Favourites</b> - Hold press the bus number and add to favourite to save your favourite bus number. <br /><br />" +
                "<b>Settings</b> - Click setting to choose your prefences within the app.<br /><br />" +
                "<b>Contact us/Feedback</b> - Click contact if you have a question or if you encounter any bug or issues within this app, " +
                        "please send us a message about this matter either from Contact Us button or Feedback.<br /><br />" +
                "<b>Fares</b> - Click fares to check the prices for ticket. This will update if there is any changes about the prices.<br /><br />";
        help.setOnClickListener( clck -> {
            //setting message and performing action on button click
            builder.setMessage(Html.fromHtml(dialogMessage))
                    .setCancelable(false)
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() { //if yes, will close the dialog
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Help info closed"
                            , Toast.LENGTH_SHORT).show();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.setTitle("Tips for use");
            alert.show();
        });
        TextView helpText = findViewById(R.id.helptext);
        helpText.setOnClickListener( clck -> {
            //setting message and performing action on button click
            builder.setMessage(Html.fromHtml(dialogMessage))
                    .setCancelable(false)
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() { //if yes, will close the dialog
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Help info closed"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.setTitle("Tips for use");
            alert.show();
        });

        //Feedback Button/Text
        ImageView feedback = findViewById(R.id.feedbackimage);
        feedback.setOnClickListener(clck -> {
            Intent nextPage = new Intent(OCInfoClass.this, OCFeedbackClass.class);
            startActivity(nextPage);
        });
        TextView feedbacktext = findViewById(R.id.feedbacktext);
        feedbacktext.setOnClickListener( clck -> {
            Intent nextPage = new Intent(OCInfoClass.this, OCFeedbackClass.class);
            startActivity(nextPage);
        });


        //Fare Button/Text
        ImageView fare = findViewById(R.id.fareimage);
        fare.setOnClickListener(clck -> {
            Intent nextPage = new Intent(OCInfoClass.this, OCFareClass.class);
            startActivity(nextPage);
        });
        TextView fareText = findViewById(R.id.faretext);
        fareText.setOnClickListener(clck -> {
            Intent nextPage = new Intent(OCInfoClass.this, OCFareClass.class);
            startActivity(nextPage);
        });

        //Contact Button/Text
        ImageView contact = findViewById(R.id.contactimage);
        contact.setOnClickListener(clck -> {
            Intent nextPage = new Intent(OCInfoClass.this, OCContactClass.class);
            startActivity(nextPage);
        });
        TextView contactText = findViewById(R.id.contacttext);
        contactText.setOnClickListener(click -> {
            Intent nextPage = new Intent(OCInfoClass.this, OCContactClass.class);
            startActivity(nextPage);
        });

        //Settings Button/Text
        ImageView setting = findViewById(R.id.settingimage);
        setting.setOnClickListener(clck -> {
            Intent nextPage = new Intent(OCInfoClass.this, OCSettingsClass.class);
            startActivity(nextPage);
        });
        TextView settingText = findViewById(R.id.settingtext);
        settingText.setOnClickListener(clck -> {
            Intent nextPage = new Intent(OCInfoClass.this, OCSettingsClass.class);
            startActivity(nextPage);
        });

        //About Button/Text
        String aboutString = "This app is built by <i>Herman Redona</i> for his Final Project in Android Studio Class (CST2335 Mobile Graphical Interface Programming)" +
                " in Algonquin College Ottawa Campus";
        ImageView about = findViewById(R.id.aboutImage);
        about.setOnClickListener(clck -> {
            //setting message and performing action on button click
            builder.setMessage(Html.fromHtml(aboutString))
                    .setCancelable(false)
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() { //if yes, will close the dialog
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "About info closed"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.setTitle("About this app");
            alert.show();
        });
        TextView aboutText = findViewById(R.id.abouttext);
        aboutText.setOnClickListener(clck -> {
            //setting message and performing action on button click
            builder.setMessage(Html.fromHtml(aboutString))
                    .setCancelable(false)
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() { //if yes, will close the dialog
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "About info closed"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.setTitle("About this app");
            alert.show();
        });

    }
}
