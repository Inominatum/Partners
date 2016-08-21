package niko.partners;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    private Fragment client = new AddClient();
    private Fragment detailedClient = new DetailedClient();
    private Fragment payment = new AddPayment();
    private int Client_ID;
    private int Payment_ID;
    private ArrayList<String> clientsList = Global.clients;
    private TreeMap<String,HashMap<String,String>> paymentList = Global.paymentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        check_config();
        create_list_listener();
        intitDB();
        checkDB();
    }

    private void check_config(){
        MyConfigParser configParser = new MyConfigParser(this.getApplicationContext(), "config");
        configParser.check_configfile();
        if (!configParser.check_if_up_to_date()){
            SQLiteOpenHelper db = new Database(this,"partners.db",null,1);
            if(db.getReadableDatabase() == null){
                try {
                    throw new Exception("Database error!");
                } catch (Exception e) {
                    Log.d("myapp","database error");
                }
            }
            else{
                SQLiteDatabase data = db.getWritableDatabase();
                data.rawQuery(SqlQuerries.add_pay_to_plati,null);
                data.close();
                db.close();
            }


        }

        /*
        SharedPreferences preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        if (!preferences.contains("Initialized")) {
            if (!preferences.contains("Version")){
                Log.d("myapp","need update cuz no version in config");
                return;
            }
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Initialized", "True");
            editor.putString("Version", Global.version);
        }
        else{
            float current = Float.parseFloat(Global.version);
            float old = Float.parseFloat(preferences.getString("Version",""));
            if (current != old){
                Log.d("myapp","need update");
            }
        }
        */
    }

    public void intitDB(){
        try{
            SQLiteOpenHelper db = new Database(this,"partners.db",null,1);
            if(db.getReadableDatabase() == null){
                throw new Exception("Database error!");
            }
            else{
                SQLiteDatabase data = db.getWritableDatabase();
                try{
                    data.rawQuery("SELECT * FROM Clienti",null);
                }
                catch (Exception e){
                    data.execSQL(SqlQuerries.query_clienti);
                }
                try{
                    data.rawQuery("SELECT * FROM Plati",null);
                }
                catch (Exception e){
                    data.execSQL(SqlQuerries.query_plati);
                }
            }
            Global.db = db;
        } catch (Exception e) {
            Log.d("Testing",e.getLocalizedMessage());
        }
    }

    public void checkDB(){
        try{
            SQLiteOpenHelper db = new Database(this,"partners.db",null,1);
            SQLiteDatabase database = db.getReadableDatabase();
            Cursor c = database.rawQuery("SELECT * FROM Clienti",null);
            Log.d("Testing",Integer.toString(c.getCount()));
            ListView list = (ListView) findViewById(R.id.listView);
            if(c.getCount() == 0){
                String[] listItems = {"List is empty!"};
                Client_ID = 0;
                list.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,listItems));
            }
            else{

                Client_ID = c.getCount();
                c.moveToFirst();
                clientsList.clear();
                while(c.isAfterLast() == false){
                    String name = c.getString(1);
                    clientsList.add(name);
                    c.moveToNext();
                }
                Global.clients = clientsList;
                list.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,clientsList));
            }
            //Toast.makeText(this.getApplicationContext(),"before second table",Toast.LENGTH_LONG).show();
            try {
                c = database.rawQuery("SELECT * FROM Plati", null);
            }
            catch (SQLException e){
                Toast.makeText(this.getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
            //Toast.makeText(this.getApplicationContext(),"im here",Toast.LENGTH_LONG).show();
            if(c.getCount() != 0){
                Payment_ID = c.getCount();
                c.moveToFirst();
                paymentList.clear();
                while (c.isAfterLast() == false){
                    String name = c.getString(1);
                    String message = name;
                    Toast.makeText(this.getApplicationContext(),message,Toast.LENGTH_LONG).show();
                    c.moveToNext();
                }
            }


        }
        catch (Exception e){
            Log.d("Testing",e.getLocalizedMessage());
        }
    }
    public void add_client(View v){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.top,client,"tag");
        ft.addToBackStack("client");
        ft.commit();
    }

    public void goBack(View v){
        //Toast.makeText(this,"changing the fragment",Toast.LENGTH_LONG).show();
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();

    }

    public void save(View v){
        EditText TxtName = (EditText) findViewById(R.id.editText);
        String name = TxtName.getText().toString();
        Log.d("Testing",name);

        EditText TxtAdress = (EditText) findViewById(R.id.editText2);
        String adress = TxtAdress.getText().toString();
        Log.d("Testing",adress);
        SQLiteOpenHelper db = new Database(this,"partners.db",null,1);
        SQLiteDatabase database = db.getWritableDatabase();
        Client_ID +=1;
        ContentValues values = SqlQuerries.InsertIntoClienti(Client_ID,name,adress);
        database.insert("Clienti",null,values);
        clientsList.add(name);
        updateView();
        Toast.makeText(v.getContext(),"Client adaugat cu succes!",Toast.LENGTH_LONG).show();
        goBack(v);
        TxtName.setText("");
        TxtAdress.setText("");
    }

    public void updateView(){
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,clientsList));
    }

    public void create_list_listener(){
        Log.d("Testing","listener");
        ListView list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                detailes(view,i);
            }
        });
        Log.d("Testing","listener created");
    }

    public void detailes(View v,int i){
        ListView list = (ListView)findViewById(R.id.listView);
        Adapter adapter= list.getAdapter();
        Object item = adapter.getItem(i);
        Bundle data = new Bundle();
        data.putString("item",item.toString());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        detailedClient.setArguments(data);
        ft.replace(R.id.top,detailedClient);
        ft.addToBackStack("detailed");
        ft.commit();
    }

    public void add_payment(View v){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.top,payment,"tag");
        ft.addToBackStack("payment");
        ft.commit();
    }

    public void save_payment(View v){
        Spinner _client = (Spinner) findViewById(R.id.client_spinner);
        String client = _client.getSelectedItem().toString();

        Spinner _currency = (Spinner) findViewById(R.id.currency_spinner);
        String currency = _currency.getSelectedItem().toString();

        EditText _sum = (EditText) findViewById(R.id.sum);
        String sum = _sum.getText().toString();

        EditText _description = (EditText) findViewById(R.id.description);
        String description = _description.getText().toString();

        EditText _explication = (EditText) findViewById(R.id.explication);
        String explication = _explication.getText().toString();

        EditText _document = (EditText)findViewById(R.id.document);
        String document = _document.getText().toString();

        DatePicker _date = (DatePicker) findViewById(R.id.datePicker);
        Date date = new Date(_date.getYear(),_date.getMonth(),_date.getDayOfMonth());
        String str_date = new SimpleDateFormat("dd-MM-yy").format(date);
        Payment_ID += 1;
        if (sum.isEmpty()){
            _sum.setText("");
            Toast.makeText(v.getContext(),"Campul cu suma este obligatoriu!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        try {
            ContentValues values = SqlQuerries.InsertIntoPlati(Payment_ID, client, str_date,
                    Double.parseDouble(sum), currency,
                    description, explication, document);
            SQLiteDatabase database = Global.db.getWritableDatabase();
            database.insert("Plati",null,values);
        }catch (Exception e){
            Log.d("Testing",e.getLocalizedMessage());
        }
        try{
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
        catch (Exception e){

        }
        _sum.setText("");
        _description.setText("");
        _explication.setText("");
        _document.setText("");
        Toast.makeText(v.getContext(),"Plata salvata cu succes!",Toast.LENGTH_LONG).show();
        goBack(v);

    }
}
