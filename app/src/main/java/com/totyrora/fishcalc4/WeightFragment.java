package com.totyrora.fishcalc4;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class WeightFragment extends Fragment {

    private TextView result;
    private TextView resultComment;
    private Spinner spices;
    private EditText length;
    private TextView lengthText;
    private Fish fish;
    private boolean imperialUnit = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weight, container, false);

        setupPreference ();     // load preferences

        // Create TextView and associate to layout
        result = (TextView) view.findViewById(R.id.result_view);
        resultComment = (TextView) view.findViewById(R.id.resultComment_view);

        setupSpinner(view);     // spices dropdown
        setupTextEdit(view);    // edit length

        // Create a fish and initialize kArray
        fish = new Fish(0, 0, getResources().getIntArray(R.array.k_array));

        // init result view
        displayWeight();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();

        setupPreference ();     // load preferences
        displayWeight();        // update text
    }

    private void setupPreference () {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        imperialUnit = prefs.getBoolean("imperial_unit",false);
    };

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
        if (!tmpStr.isEmpty()) {
            double tmpLenD = Double.parseDouble(length.getText().toString());
            fish.setLen(tmpLenD, imperialUnit);
        } else {
            fish.setLen(0, imperialUnit);
        };
    }

    private void displayWeight() {

        String tmpUnit;
        if(imperialUnit) {
            lengthText.setText(getResources().getString(R.string.length_unit_imperial));
            tmpUnit = getResources().getString(R.string.weight_unit_imperial);
        } else {
            lengthText.setText(getResources().getString(R.string.length_unit_metric));
            tmpUnit = getResources().getString(R.string.weight_unit_metric);
        }


        String tmpWeightHi = String.format("%.2f", fish.getWeightHi(imperialUnit)/1000);
        String tmpWeightLo = String.format("%.2f", fish.getWeightLo(imperialUnit)/1000);
        String resultText =
                tmpWeightLo
                + " - "
                + tmpWeightHi
                + " " + tmpUnit;
        result.setText(resultText);

        String tmpWeight = String.format("%.2f", fish.getWeight(imperialUnit)/1000);
        String commentText =
                getResources().getString(R.string.result_comment)
                + " " + tmpWeight
                + " " + tmpUnit;
        resultComment.setText(commentText);

/*        String resultText = tmpWeightLo
                + " - <font color='#EE0000'>" + tmpWeight + "</font> - "
                +  tmpWeightHi
                + " " + tmpUnit;
        weight.setText(Html.fromHtml(resultText));
*/

    }


}
