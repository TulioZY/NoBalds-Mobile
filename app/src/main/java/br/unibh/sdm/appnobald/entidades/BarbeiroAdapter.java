package br.unibh.sdm.appnobald.entidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.unibh.sdm.appnobald.R;
import br.unibh.sdm.appnobald.entidades.Barbeiro;

public class BarbeiroAdapter extends ArrayAdapter<Barbeiro> {

    private LayoutInflater inflater;

    public BarbeiroAdapter(Context context, List<Barbeiro> barbeiros) {
        super(context, 0, barbeiros);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.list_item_barbeiro, parent, false);
        }

        TextView textViewNomeBarbeiro = itemView.findViewById(R.id.textViewNomeBarbeiro);
        TextView textViewHorarioBarbeiro = itemView.findViewById(R.id.textViewHorarioBarbeiro);

        Barbeiro barbeiro = getItem(position);

        textViewNomeBarbeiro.setText(barbeiro.getNome());
        textViewHorarioBarbeiro.setText(barbeiro.getHorario());

        return itemView;
    }
}
