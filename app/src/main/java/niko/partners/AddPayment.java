package niko.partners;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by adrian on 7/10/16.
 */
public class AddPayment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myInflater = inflater.inflate(R.layout.add_payment_fragment,container,false);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item,Global.clients);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) myInflater.findViewById(R.id.client_spinner);
        spinner.setAdapter(adapter);
        ArrayList<String> currency = new ArrayList<>();
        currency.add("LEI");
        ArrayAdapter<String> currency_adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item,currency);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner currency_spinner = (Spinner) myInflater.findViewById(R.id.currency_spinner);
        currency_spinner.setAdapter(currency_adapter);
        return myInflater;
    }
}
