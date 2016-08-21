package niko.partners;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by adrian on 7/10/16.
 */
public class DetailedClient extends Fragment {
    private static int black = R.color.black;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myInflater = inflater.inflate(R.layout.detailed_client_fragment,container,false);
        Bundle extras = getArguments();
        String item = extras.get("item").toString();
        TextView text = (TextView) myInflater.findViewById(R.id.detailed_text);
        text.setText(item);
        Toast.makeText(this.getActivity(),item.toString(),Toast.LENGTH_LONG).show();
        get_from_db(item, myInflater);
        return myInflater;

    }

    public static void get_from_db(String item, View inflater){
        SQLiteOpenHelper db = Global.db;
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM Plati WHERE Name='" + item + "'",null);
        try {
            if (c.getCount() == 0) {
                create_empty_view(inflater);
            } else {
                create_view(c, inflater);
            }
        }
        catch (Exception e){
            Toast.makeText(inflater.getContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        }

    }

    public static void create_empty_view(View inflater){
        LinearLayout layout = (LinearLayout) inflater.findViewById(R.id.top);
        TextView empty_list = new TextView(inflater.getContext());
        empty_list.setText("Lista de plati este goala!");
        layout.addView(empty_list);
    }

    public static void create_view(Cursor c,View inflater){
        //Top view, the only child of scroll view
        LinearLayout layout = (LinearLayout) inflater.findViewById(R.id.top);
        c.moveToFirst();

        LinearLayout.LayoutParams lp = new LinearLayout.
                LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,0,5,0);

        //Date column
        LinearLayout date = new LinearLayout(inflater.getContext());
        date.setLayoutParams(lp);
        date.setOrientation(LinearLayout.VERTICAL);
        TextView column_date = new TextView(inflater.getContext());
        column_date.setTextColor(inflater.getContext().getResources().getColor(black));
        column_date.setText("Data");
        date.addView(column_date);

        //Sum collumn
        LinearLayout sum = new LinearLayout(inflater.getContext());
        sum.setLayoutParams(lp);
        sum.setOrientation(LinearLayout.VERTICAL);
        TextView column_sum = new TextView(inflater.getContext());
        column_sum.setText("Plati");
        column_sum.setTextColor(inflater.getContext().getResources().getColor(black));
        sum.addView(column_sum);

        //Debt collumn
        LinearLayout debt = new LinearLayout(inflater.getContext());
        debt.setLayoutParams(lp);
        debt.setOrientation(LinearLayout.VERTICAL);
        TextView column_debt = new TextView(inflater.getContext());
        column_debt.setText("Datorie");
        column_debt.setTextColor(inflater.getContext().getResources().getColor(black));
        debt.addView(column_debt);

        //Currency column
        LinearLayout currency = new LinearLayout(inflater.getContext());
        currency.setLayoutParams(lp);
        currency.setOrientation(LinearLayout.VERTICAL);
        TextView column_currency = new TextView(inflater.getContext());
        column_currency.setText("Moneda");
        column_currency.setTextColor(inflater.getContext().getResources().getColor(black));
        currency.addView(column_currency);

        //Description column
        LinearLayout description = new LinearLayout(inflater.getContext());
        description.setLayoutParams(lp);
        description.setOrientation(LinearLayout.VERTICAL);
        TextView column_description = new TextView(inflater.getContext());
        column_description.setText("Descriere");
        column_description.setTextColor(inflater.getContext().getResources().getColor(black));
        description.addView(column_description);

        //Explication column
        LinearLayout explication = new LinearLayout(inflater.getContext());
        explication.setLayoutParams(lp);
        explication.setOrientation(LinearLayout.VERTICAL);
        TextView column_explication = new TextView(inflater.getContext());
        column_explication.setText("Explicatie");
        column_explication.setTextColor(inflater.getContext().getResources().getColor(black));
        explication.addView(column_explication);

        //Document column
        LinearLayout document = new LinearLayout(inflater.getContext());
        document.setLayoutParams(lp);
        document.setOrientation(LinearLayout.VERTICAL);
        TextView column_document = new TextView(inflater.getContext());
        column_document.setText("Document");
        column_document.setTextColor(inflater.getContext().getResources().getColor(black));
        document.addView(column_document);
        float debt_value = 0;
        //Loop over db entries
        while (c.isAfterLast() == false) {
            String Date = c.getString(2);
            String suma = c.getString(3);
            String moneda = c.getString(4);
            String descriere = c.getString(5);
            String explicatie = c.getString(6);
            String str_document = c.getString(7);

            debt_value += Float.parseFloat(suma);

            //create date value & color
            TextView txt_date = new TextView(inflater.getContext());
            txt_date.setTextColor(inflater.getContext().getResources().getColor(black));
            //create sum value & color
            TextView txt_sum = new TextView(inflater.getContext());
            txt_sum.setTextColor(inflater.getContext().getResources().getColor(black));
            //create debt value
            TextView txt_debt = new TextView(inflater.getContext());
            txt_debt.setTextColor(inflater.getContext().getResources().getColor(black));
            //create currency value
            TextView txt_currency = new TextView(inflater.getContext());
            txt_currency.setTextColor(inflater.getContext().getResources().getColor(black));
            //create description value
            TextView txt_description = new TextView(inflater.getContext());
            txt_description.setTextColor(inflater.getContext().getResources().getColor(black));
            //create explication
            TextView txt_explication = new TextView(inflater.getContext());
            txt_explication.setTextColor(inflater.getContext().getResources().getColor(black));
            //create document
            TextView txt_documents = new TextView(inflater.getContext());
            txt_documents.setTextColor(inflater.getContext().getResources().getColor(black));

            //set date value
            txt_date.setText(Date);
            //set sum value
            txt_sum.setText(suma);
            //set debt value
            txt_debt.setText(Float.toString(debt_value));
            //set currency
            txt_currency.setText(moneda);
            //set description
            txt_description.setText(descriere);
            //set explication
            txt_explication.setText(explicatie);
            //set document
            txt_documents.setText(str_document);

            date.addView(txt_date);
            sum.addView(txt_sum);
            debt.addView(txt_debt);
            currency.addView(txt_currency);
            description.addView(txt_description);
            explication.addView(txt_explication);
            document.addView(txt_documents);

            c.moveToNext();
        }
        layout.addView(date);
        layout.addView(sum);
        layout.addView(debt);
        layout.addView(currency);
        layout.addView(description);
        layout.addView(explication);
        layout.addView(document);
    }
}
