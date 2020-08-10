package data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class ClubOlimpusContract {
    private ClubOlimpusContract(){}
    //Информмация о таблице членов клуба
    //Таким образом мы создаем клас,а этот класс является источником данных для отдельной таблицы
    //В этом классе мы можем создать еще внутренние статические классы,с данными о другой таблице
    public static final class MemberEntry implements BaseColumns {
        //For ContentProvider
        public static final String SCHEME = "content://";
        public static final String AUTHORITY = "com.kostya_zinoviev.clubolympus";
        public static final String PATH_MEMBER = "members";

        public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_MEMBER);

        public static final String TABLE_NAME = "members";
        public static final String DATABASE_NAME = "olympus";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_FIRST_NAME = "firstName";
        public static final String COLUMN_LAST_NAME = "lastName";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_SPORT = "sport";
        public static final int DB_VERSION = 1;

        public static final int KEY_UNKNOWN = 0;
        public static final int KEY_MALE = 1;
        public static final int KEY_FEMALE = 2;

        public static final String CONTENT_MULTIPLE_ITEMS = ContentResolver.CURSOR_DIR_BASE_TYPE +"/" + AUTHORITY + "/" + PATH_MEMBER;
        public static final String CONTENT_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + AUTHORITY + "/" +PATH_MEMBER;
    }
}
