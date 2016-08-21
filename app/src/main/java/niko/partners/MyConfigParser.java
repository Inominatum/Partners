package niko.partners;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by adrian on 8/21/16.
 */
public class MyConfigParser {
    private String file_name = "";
    private SharedPreferences config_file;
    private Context context;

    public MyConfigParser(Context context, String file_name){
        this.context = context;
        this.file_name = file_name;
    }

    public void check_configfile(){
        SharedPreferences preferences = context.getSharedPreferences(this.file_name,
                context.MODE_PRIVATE);
        if (preferences.contains("Initialized")){
            this.config_file = preferences;
        }
        else{
            init_config();
        }
    }

    public void init_config(){
        Defaults.populate_config();
        SharedPreferences preferences = context.getSharedPreferences(this.file_name,
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        for (String key : Defaults.config.keySet()){
            editor.putString(key, Defaults.config.get(key));
        }
        this.config_file = preferences;
    }

    public boolean check_if_up_to_date(){
        float cur_v = Float.parseFloat(this.config_file.getString("Version","0"));
        float v = Float.parseFloat(Global.version);
        if (cur_v != v){
            return false;
        }
        return true;

    }
}
