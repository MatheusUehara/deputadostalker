package app.deputadostalker.deputado.gui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.deputadostalker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProposicaoDeputado extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_proposicao_deputado, container, false);
    }

}
