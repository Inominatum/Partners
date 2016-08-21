package niko.partners;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adrian on 7/3/16.
 */
public class Database extends SQLiteOpenHelper {

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlQuerries.query_clienti);
        db.execSQL(SqlQuerries.query_plati);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }


}
