package algonquin.cst2335.Application;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SoccerMyOpenHelper extends SQLiteOpenHelper {

    public static final String name = "SoccerArticleDB";
    public static final int version = 1;
    public static final String TABLE_NAME = "Articles";
    public static final String col_title = "Title";
    public static final String col_pubDate = "DatePublished";
    public static final String col_thumbImage = "Image";
    public static final String col_articleUrl = "Url";
    public static final String col_articleDesc = "ArticleDesc";

    public SoccerMyOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( _id INTEGER PRIMARY KEY AUTOINCREMENT , "
        + col_title + "TEXT ,"
        + col_articleDesc + "TEXT ,"
        + col_pubDate + "TEXT ,"
        + col_thumbImage + "TEXT ,"
        + col_articleUrl +");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }


}
