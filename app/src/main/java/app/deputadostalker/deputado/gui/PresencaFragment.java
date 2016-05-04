package app.deputadostalker.deputado.gui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import app.deputadostalker.R;


//A simple {@link Fragment} subclass.

public class PresencaFragment extends Fragment {
    public PresencaFragment() {
        // Required empty public constructor
    }

    CalendarView calendarView;
    TextView dateDisplay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_presenca, container, false);

        View view = inflater.inflate(R.layout.fragment_presenca, container, false);
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        dateDisplay = (TextView) view.findViewById(R.id.date_display);
        dateDisplay.setText("Data: ");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                dateDisplay.setText("Data: " + day + " / " + month + " / " + year);

                //Toast.makeText(getApplicationContext(), "Selected Date:\n" + "Day = " + i2 + "\n" + "Month = " + i1 + "\n" + "Year = " + i, Toast.LENGTH_LONG).show();
            }
        });
        return view;

        //Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//caso precise criar um botão na fragment, o código é esse:
        /*Button calendarioB = (Button) view.findViewById(R.id.buttonCalendario);
        calendarioB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent calendario;
                calendario = new Intent (PresencaFragment.this.getContext(), CalendarioActivity.class);
                startActivity(calendario);

            }
        });
        return view;*/
    }


}
