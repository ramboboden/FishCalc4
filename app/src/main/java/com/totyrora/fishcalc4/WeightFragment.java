package com.totyrora.fishcalc4;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class WeightFragment extends Fragment {

    private Spinner spices;
//    private Button calc;
    private EditText length;
    private TextView lengthText;
    private TextView weight;
    private Fish fish;
    private boolean lengthUnit = false;
    private boolean weightUnit = false;


    /*
    public static final String ARG_PAGE = "ARG_PAGE";


    public static Fragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        WeightFragment fragment = new WeightFragment();
        fragment.setArguments(args);
        return fragment;
    }
*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Get app preference

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weight, container, false);

        setupSpinner(view);     // spices dropdown
        setupTextEdit(view);    // edit length

        // Create TextView and associate to layout
        weight = (TextView) view.findViewById(R.id.weight_view);

        // Create a fish and initialize kArray
        fish = new Fish(0, 0, getResources().getIntArray(R.array.k_array));

        // init result view
        displayWeight();

        // Init View
        //((TextView)view.findViewById(R.id.textView)).setText("Weight is 0.8");

        return view;
    }

    private void setupSpinner (View view) {
        // Spices dropdown menu

        // Create Array and populate from xml
        String[] speciesArray;
        speciesArray = getResources().getStringArray(R.array.spices_array);

        // create dropdown spinner as array adapter
        ArrayAdapter<String> spicesAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, speciesArray);
        spicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spices = (Spinner) view.findViewById(R.id.spices_spinner);
        spices.setAdapter(spicesAdapter);

        // create callback methods on drop down selection
        spices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // update fish state
                fish.setSpices(position);
                displayWeight();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Auto-generated method stub
            }
        });
    }

    private void setupTextEdit (View view) {
        // Length Text Edit that update weight while typing
        // Create EditText and associate to layout
        length = (EditText) view.findViewById(R.id.length_edit);
        lengthText = (TextView) view.findViewById(R.id.length_text);

        // Create a listener that update weight while typing
        length.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                getEditLength();
                displayWeight();
            }
        });
    }


    private void getEditLength() {
        String tmpStr = length.getText().toString();
        double convert;
        if (!tmpStr.isEmpty()) {
            //int tmpLen = Integer.parseInt(length.getText().toString());
            double tmpLenD = Double.parseDouble(length.getText().toString());
            if(lengthUnit) {
                convert = 2.54;
            } else {
                convert = 1.0;
            }
            if (tmpLenD*convert > 120) {tmpLenD=0.0;} // unrealistic
            fish.setLen(tmpLenD * convert);
        } else {
            fish.setLen(0);
        };
    }

    private void displayWeight() {

        if(lengthUnit) {
            lengthText.setText(getResources().getString(R.string.length_unit_inch));
        } else {
            lengthText.setText(getResources().getString(R.string.length_unit_cm));
        }

        String tmpUnit;
        double convert = 0;
        if (weightUnit){
            tmpUnit = getResources().getString(R.string.weight_unit_pound);
            convert = 2.2046;
        }else {
            tmpUnit = getResources().getString(R.string.weight_unit_kg);
            convert = 1.0;
        }

        String tmpWeight = String.format("%.2f", convert * fish.getWeight()/1000);
        String tmpWeightHi = String.format("%.2f", convert * fish.getWeightHi()/1000);
        String tmpWeightLo = String.format("%.2f", convert * fish.getWeightLo()/1000);

        String resultText = tmpWeightLo +" - <font color='#EE0000'>" + tmpWeight + "</font> - " +  tmpWeightHi + " " + tmpUnit;
        weight.setText(Html.fromHtml(resultText));
    }


}
