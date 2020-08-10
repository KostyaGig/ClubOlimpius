package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaMetadata;

import data.ClubOlimpusContract.MemberEntry;

public class OlimpusDbOpenHelper extends SQLiteOpenHelper {

    public OlimpusDbOpenHelper(Context context) {
        super(context,MemberEntry.DATABASE_NAME, null, MemberEntry.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEMBERS = "CREATE TABLE "+ MemberEntry.TABLE_NAME + "("
                + MemberEntry.COLUMN_ID + " INTEGER PRIMARY KEY,"
                + MemberEntry.COLUMN_FIRST_NAME + "TEXT,"
                + MemberEntry.COLUMN_LAST_NAME + "TEXT,"
                + MemberEntry.COLUMN_GENDER +"INTEGER NOT NULL,"
                + MemberEntry.COLUMN_SPORT + "TEXT" + ")";
        db.execSQL(CREATE_MEMBERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MemberEntry.DATABASE_NAME);
        onCreate(db);
    }
}
