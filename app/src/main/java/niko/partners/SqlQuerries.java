package niko.partners;

import android.content.ContentValues;

import java.util.Date;

/**
 * Created by adrian on 7/3/16.
 */
public class SqlQuerries {
    public static String query_clienti = "CREATE TABLE Clienti (ID int PRIMARY KEY," +
            "Name varchar(255)," +
            "Adress varchar(255));" ;
    public static String query_plati = "CREATE TABLE Plati(ID int PRIMARY KEY," +
            "Name varchar(255) ," +
            "Date varchar(255)," +
            "Sum varchar(255)," +
            "Exchange varchar(255)," +
            "Description varchar(255)," +
            "Explication varchar(255)," +
            "Document_nr varchar(255));";

    public static ContentValues InsertIntoClienti(int ID, String name, String address){
        ContentValues values = new ContentValues();
        values.put("ID",ID);
        values.put("Name",name);
        values.put("Adress",address);
        return values;
    }

    public static ContentValues InsertIntoPlati(int ID, String name, String date, double sum,
                                                String exchange,
                                                String description,
                                                String explication,
                                                String document){
        ContentValues values = new ContentValues();
        values.put("ID",ID);
        values.put("Name",name);
        values.put("Date",date.toString());
        values.put("Sum",Double.toString(sum));
        values.put("Exchange",exchange);
        values.put("Description",description);
        values.put("Explication",explication);
        values.put("Document_nr",document);
        return values;
    }
}
