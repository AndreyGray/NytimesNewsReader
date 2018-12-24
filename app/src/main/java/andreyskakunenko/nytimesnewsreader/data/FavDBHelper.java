package andreyskakunenko.nytimesnewsreader.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import andreyskakunenko.nytimesnewsreader.data.FavContract.FavTable;

public class FavDBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "favBase.db";

    public FavDBHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + FavTable.TABLE_NAME+ "(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavTable.Cols.UUID + " TEXT, " +
                FavTable.Cols.ADDDATE + " TEXT, " +
                FavTable.Cols.TITLE+ " TEXT NOT NULL, "+
                FavTable.Cols.AUTHOR + " TEXT, " +
                FavTable.Cols.SECTION + " TEXT, " +
                FavTable.Cols.DATE + " TEXT, " +
                FavTable.Cols.PICURL + " TEXT, " +
                FavTable.Cols.WEBURL + " TEXT, " +
                FavTable.Cols.CONTENT + " TEXT" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
