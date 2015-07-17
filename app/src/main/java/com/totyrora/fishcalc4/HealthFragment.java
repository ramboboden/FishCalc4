package com.totyrora.fishcalc4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class HealthFragment extends Fragment {

    private TextView result;
    private TextView resultComment;
    private String[] resultState;
    private Spinner spices;
    private EditText length;
    private TextView lengthText;
    private EditText weight;
    private TextView weightText;
    private Button calc;
    private Fish fish;
    private boolean imperialUnit = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health, container, false);


        setupPreference ();     // load preferences

        // Create TextView and associate to layout
        result = (TextView) view.findViewById(R.id.result_view);
        resultComment = (TextView) view.findViewById(R.id.resultComment_view);
        resultState = getResources().getStringArray(R.array.health_array);

        // spices
        setupSpinner(view);     // spices dropdown

        // Create EditText and associate to layout
        length = (EditText) view.findViewById(R.id.length_edit);
        lengthText = (TextView) view.findViewById(R.id.length_text);

        // Create EditText and associate to layout
        weight = (EditText) view.findViewById(R.id.weight_edit);
        weightText = (TextView) view.findViewById(R.id.weight_text);


        // Create button and associate to layout
        setupButton(view);

        // Create a fish and initialize kArray
        fish = new Fish(0, 0,
                getResources().getIntArray(R.array.k_array),
                getResources().getIntArray(R.array.h_array));

        // init result view
        displayCondition();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();

        setupPreference ();     // load preferences
        displayCondition();        // update text
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
                displayCondition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Auto-generated method stub
            }
        });
    }

    private void setupButton (View view) {
        calc = (Button) view.findViewById(R.id.calc_button);
        calc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                //condition.setText(" TEST DEBUG ");

                // get length
                String tmpStr = length.getText().toString();
                if (!tmpStr.isEmpty()) {
                    double tmpLenD = Double.parseDouble(length.getText().toString());
                    fish.setLen(tmpLenD, imperialUnit);
                } else {
                    fish.setLen(0, imperialUnit);
                };

                // get weight
                tmpStr = weight.getText().toString();
                if (!tmpStr.isEmpty()) {
                    double tmpWeiD = Double.parseDouble(weight.getText().toString());
                    fish.setWeight(tmpWeiD*1000, imperialUnit);
                } else {
                    fish.setWeight(0, imperialUnit);
                };

                displayCondition();

            }
        });
    }


    private void displayCondition() {

        String tmpUnit;
        if(imperialUnit) {
            lengthText.setText(getResources().getString(R.string.length_unit_imperial));
            weightText.setText(getResources().getString(R.string.weight_unit_imperial));
        } else {
            lengthText.setText(getResources().getString(R.string.length_unit_metric));
            weightText.setText(getResources().getString(R.string.weight_unit_metric));
        }

        // Result
        String tmpCond = String.format("%.2f", fish.getCondition());
        double tmp = fish.getCondition();
        if (tmp == 0.0) {
            tmpCond = getResources().getString(R.string.w_result_non_valid);
        }
        String resultText =
                getResources().getString(R.string.h_result)
                + " "
                + tmpCond;
        result.setText(resultText);

        // Result comment
        String tmpEst = resultState[fish.calcConditionState()];
        String commentText = getResources().getString(R.string.h_result_comment)
                + " "
                + tmpEst;
        resultComment.setText(commentText);

    }


}
