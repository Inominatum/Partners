package niko.partners;

import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by adrian on 7/10/16.
 */
public class Global {
    public static ArrayList<String> clients = new ArrayList<>();
    public static TreeMap<String,HashMap<String,String>> paymentList = new TreeMap<>();
    public static SQLiteOpenHelper db = null;
    public static final String version = "1.01";

}
