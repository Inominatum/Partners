package niko.partners;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.HashMap;

/**
 * Created by adrian on 8/21/16.
 */
public class Defaults {
    public static HashMap<String, String> config = new HashMap<String, String>();
    public static void populate_config(){
        config.put("Initialized","True");
        config.put("Version",Global.version);
    }



}
