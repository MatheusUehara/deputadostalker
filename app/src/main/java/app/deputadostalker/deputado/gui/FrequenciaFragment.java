package app.deputadostalker.deputado.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import app.deputadostalker.R;
import app.deputadostalker.deputado.dominio.Deputado;
import app.deputadostalker.deputado.service.DeputadoService;
import app.deputadostalker.frequencia.api.FrequenciaAPI;
import app.deputadostalker.frequencia.api.FrequenciaDes;
import app.deputadostalker.frequencia.dominio.Frequencia;
import app.deputadostalker.util.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FrequenciaFragment extends Fragment {

    DeputadoService deputadoService = DeputadoService.getInstance();


    CalendarView calendarView;
    TextView dateDisplay;
    List<Frequencia> listaFrequencia =null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_presenca, container, false);

        calendarView = (CalendarView) view.findViewById(R.id.calendarView);

        dateDisplay = (TextView) view.findViewById(R.id.date_display);
        dateDisplay.setText("Data: ");



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {

                Deputado deputado = deputadoService.getDeputado(Session.getIdeCadastroDeputado());

                int matricula = deputado.getMatricula();

                // Esse mês está sendo iterado por que por padrão ele retorna meses no intervalo de 0 a 11  disponível no javaDoc CalendarView.onSelectedDayChange
                month += 1;

                getFrequencia(day + "-" + month + "-" + year, day + "-" + month + "-" + year, matricula , listaFrequencia);
                dateDisplay.setText("Data: " + day + " / " + month + " / " + year );
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

    @Override
    public void onResume() {
        super.onResume();
        }

    private void getFrequencia(String dataInicial, String dataFinal, int matricula, final List<Frequencia> listaFrequencia){
        Gson gson = new GsonBuilder().registerTypeAdapter(Frequencia.class, new FrequenciaDes()).create();

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(getString(R.string.urlBase))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        final FrequenciaAPI frequenciaAPI = retrofit.create(FrequenciaAPI.class);

        final Call<List<Frequencia>> callFrequencia = frequenciaAPI.getFrequencia(dataInicial, dataFinal, matricula);
        callFrequencia.enqueue(new Callback<List<Frequencia>>() {
            @Override
            public void onResponse(Call<List<Frequencia>> call, Response<List<Frequencia>> response) {
                List<Frequencia> listFrequencias = response.body();
                if( listFrequencias != null ){
                    for( Frequencia f : listFrequencias ){
                        Log.i("justificativa", "justificativa: " + f.getJustificativa());
                        Log.i("Frequencia", "Frequencia: " + f.getFrequencia());
                        Log.i("qntsessões", "qntsessões: " + f.getQtdeSessoes());

                        dateDisplay.append("\n" + "Numero de Sessões: " + f.getQtdeSessoes() +
                                "\n" + "Justificativa: " + f.getJustificativa() +
                                "\n" + "Status: " + f.getFrequencia());
                    }
                }else{
                    dateDisplay.append("\n Não obtivemos nenhum registro de Frequência");
                    Log.i("ERRORRRRRRRR",String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<List<Frequencia>> call, Throwable t) {

            }
        });
    }

}
