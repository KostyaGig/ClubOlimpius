package data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Measure;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;


public class OlympusContentProvider extends ContentProvider {
    OlimpusDbOpenHelper dbOpenHelper;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    //Создадим 2 константы,с значением кода,чтобы при обращении ко всей таблице, или к таблице и определенному столбцу не допустить ошибку
    public static final int MEMBERS = 111;
    public static final int MEMBER_ID = 222;
    static {
        uriMatcher.addURI(ClubOlimpusContract.MemberEntry.AUTHORITY,ClubOlimpusContract.MemberEntry.PATH_MEMBER,MEMBERS);
        uriMatcher.addURI(ClubOlimpusContract.MemberEntry.AUTHORITY,ClubOlimpusContract.MemberEntry.PATH_MEMBER + "/#",MEMBER_ID);
    }

    @Override
    public boolean onCreate() {
        dbOpenHelper = new OlimpusDbOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri,String[] projection,String selection,String[] selectionArgs,String sortOrder) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);
        switch (match){
            case MEMBERS:
                cursor = db.query(ClubOlimpusContract.MemberEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case MEMBER_ID:
                selection = ClubOlimpusContract.MemberEntry.COLUMN_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(ClubOlimpusContract.MemberEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Can t incorrect URI " + uri);
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri,ContentValues values) {

        String firstName = values.getAsString(ClubOlimpusContract.MemberEntry.COLUMN_FIRST_NAME);
        if(firstName == null){
            throw new IllegalArgumentException("You have to input first name");
        }

        String lastName = values.getAsString(ClubOlimpusContract.MemberEntry.COLUMN_LAST_NAME);
        if(lastName == null){
            throw new IllegalArgumentException("You have to input last name");
        }

        Integer gender = values.getAsInteger(ClubOlimpusContract.MemberEntry.COLUMN_GENDER);
        if(gender == null || !(gender == ClubOlimpusContract.MemberEntry.KEY_UNKNOWN ||
                gender == ClubOlimpusContract.MemberEntry.KEY_MALE ||
                gender == ClubOlimpusContract.MemberEntry.KEY_FEMALE)){
            throw new IllegalArgumentException("You have to input correct gender");
        }

        String sport = values.getAsString(ClubOlimpusContract.MemberEntry.COLUMN_SPORT);
        if (sport == null){
            throw new IllegalArgumentException("You have to input sport");
        }
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        switch (match){
            case MEMBERS:
                //Method insert возвращает id,поэтому мы можем присвоить этот метод  переменнойтипа long
                long id =  db.insert(ClubOlimpusContract.MemberEntry.TABLE_NAME,null,values);
                //Если вставка прошла успешно,то id вернет неизвестное число,но не -1 ,иначе id равно - 1
                //С помощью if можем проверить прошла ли вставка данных успешно или нет
                if(id == -1){
                    Log.e("insertMethod","Вставка данных прошла неудачно " + uri);
                    return null;
                }else return ContentUris.withAppendedId(uri,id);

            default:
                throw new IllegalArgumentException("Can t incorrect URI " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values,String selection,String[] selectionArgs) {
        if (values.containsKey(ClubOlimpusContract.MemberEntry.COLUMN_FIRST_NAME)) {
            String firstName = values.getAsString(ClubOlimpusContract.MemberEntry.COLUMN_FIRST_NAME);
            if (firstName == null) {
                throw new IllegalArgumentException("You have to input first name");
            }
        }
        if (values.containsKey(ClubOlimpusContract.MemberEntry.COLUMN_LAST_NAME)) {
            String lastName = values.getAsString(ClubOlimpusContract.MemberEntry.COLUMN_LAST_NAME);
            if (lastName == null) {
                throw new IllegalArgumentException("You have to input last name");
            }
        }

        if (values.containsKey(ClubOlimpusContract.MemberEntry.COLUMN_GENDER)) {
            Integer gender = values.getAsInteger(ClubOlimpusContract.MemberEntry.COLUMN_GENDER);
            if (gender == null || !(gender == ClubOlimpusContract.MemberEntry.KEY_UNKNOWN ||
                    gender == ClubOlimpusContract.MemberEntry.KEY_MALE ||
                    gender == ClubOlimpusContract.MemberEntry.KEY_FEMALE)) {
                throw new IllegalArgumentException("You have to input correct gender");
            }
        }

        if (values.containsKey(ClubOlimpusContract.MemberEntry.COLUMN_SPORT)) {
            String sport = values.getAsString(ClubOlimpusContract.MemberEntry.COLUMN_SPORT);
            if (sport == null) {
                throw new IllegalArgumentException("You have to input sport");
            }
        }

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        switch (match){
            case MEMBERS:
                return db.update(ClubOlimpusContract.MemberEntry.TABLE_NAME,values,selection,selectionArgs);

            case MEMBER_ID:
                selection = ClubOlimpusContract.MemberEntry.COLUMN_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return db.update(ClubOlimpusContract.MemberEntry.TABLE_NAME,values,selection,selectionArgs);

            default:
                throw new IllegalArgumentException("Can t incorrect URI " + uri);
        }
    }

    @Override
    public int delete(Uri uri,String selection,String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        switch (match){
            case MEMBERS:
                return db.delete(ClubOlimpusContract.MemberEntry.TABLE_NAME,selection,selectionArgs);

            case MEMBER_ID:
                selection = ClubOlimpusContract.MemberEntry.COLUMN_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return db.delete(ClubOlimpusContract.MemberEntry.TABLE_NAME,selection,selectionArgs);

            default:
                throw new IllegalArgumentException("Can t delete this URI " + uri);
        }
    }


    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match){
            case MEMBERS:
                return ClubOlimpusContract.MemberEntry.CONTENT_MULTIPLE_ITEMS;

            case MEMBER_ID:

                return ClubOlimpusContract.MemberEntry.CONTENT_SINGLE_ITEM;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

}
