package br.unibh.sdm.appnobald.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import br.unibh.sdm.appnobald.R;
import br.unibh.sdm.appnobald.api.NobaldService;
import br.unibh.sdm.appnobald.api.RestServiceGenerator;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import br.unibh.sdm.appnobald.entidades.Barbeiro;
import br.unibh.sdm.appnobald.entidades.BarbeiroAdapter;
import br.unibh.sdm.appnobald.entidades.Servico;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    private NobaldService service = null;
    private ListView listViewListaBarbeiros;
    private ListView listViewListaServicos;
    private Button buttonMostrarLista;
    private Button buttonMostrarLista2;
    private Button buttonMostrarClientes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        service = RestServiceGenerator.createService(NobaldService.class, "http://lbnobald-1018607656.us-east-1.elb.amazonaws.com/nobald-api/");

        listViewListaBarbeiros = findViewById(R.id.listViewListaBarbeiros);
        listViewListaServicos = findViewById(R.id.listViewListaServicos);
        buttonMostrarLista = findViewById(R.id.buttonMostrarLista);
        buttonMostrarLista2 = findViewById(R.id.buttonMostrarServicos);

        listViewListaServicos.setVisibility(View.GONE);
        buttonMostrarLista2.setVisibility(View.GONE);

        buttonMostrarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscaBarbeiros();
            }
        });

        buttonMostrarLista2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscaServicos();
            }
        });

        buttonMostrarClientes = findViewById(R.id.buttonMostrarClientes);
        buttonMostrarClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivityClientes();
            }
        });

    }

    public void abrirActivityClientes() {
        Intent intent = new Intent(MainActivity2.this, DetalhesClienteActivity.class);
        startActivity(intent);
    }


    public void buscaBarbeiros() {
        NobaldService service = RestServiceGenerator.createService(NobaldService.class, "http://lbnobald-1018607656.us-east-1.elb.amazonaws.com/nobald-api/");
        Call<List<Barbeiro>> call = service.getBarbeiro();
        call.enqueue(new Callback<List<Barbeiro>>() {
            @Override
            public void onResponse(Call<List<Barbeiro>> call, Response<List<Barbeiro>> response) {
                if (response.isSuccessful()) {
                    Log.i("Tabela Barbeiro", "Retornou " + response.body().size() + " Barbeiros!");
                    List<String> lista2 = new ArrayList<String>();
                    for (Barbeiro item : response.body()) {
                        lista2.add(item.getNome());
                        lista2.add(item.getHorario());
                    }
                    Log.i("MainActivity2", lista2.toArray().toString());
                    ListView listView = findViewById(R.id.listViewListaBarbeiros);
                    BarbeiroAdapter adapter = new BarbeiroAdapter(MainActivity2.this, response.body());
                    listView.setAdapter(adapter);
                    listViewListaBarbeiros.setVisibility(View.VISIBLE);
                    buttonMostrarLista.setVisibility(View.GONE);
                    listViewListaServicos.setVisibility(View.GONE);
                    buttonMostrarLista2.setVisibility(View.VISIBLE);
                } else {
                    Log.e("Tabela Barbeiro", "" + response.message());
                    Toast.makeText(getApplicationContext(), "Erro: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Barbeiro>> call, Throwable t) {
                Log.e("Error", "" + t.getMessage());
            }
        });
    }

    public void buscaServicos() {
        NobaldService service = RestServiceGenerator.createService(NobaldService.class, "http://lbnobald-1018607656.us-east-1.elb.amazonaws.com/nobald-api/");
        Call<List<Servico>> call = service.getServico();
        call.enqueue(new Callback<List<Servico>>() {
            @Override
            public void onResponse(Call<List<Servico>> call, Response<List<Servico>> response) {
                if (response.isSuccessful()) {
                    Log.i("Tabela Serviço", "Retornou " + response.body().size() + " Serviços!");
                    List<String> lista2 = new ArrayList<String>();
                    for (Servico item : response.body()) {
                        lista2.add(item.getDescricao());
                    }
                    Log.i("MainActivity2", lista2.toArray().toString());
                    listViewListaServicos.setAdapter(new ArrayAdapter<String>(MainActivity2.this,
                            android.R.layout.simple_list_item_1,
                            lista2));
                    listViewListaServicos.setVisibility(View.VISIBLE);
                    buttonMostrarLista2.setVisibility(View.GONE);
                    listViewListaBarbeiros.setVisibility(View.GONE);
                    buttonMostrarLista.setVisibility(View.VISIBLE);
                } else {
                    Log.e("Tabela Servico", "" + response.message());
                    Toast.makeText(getApplicationContext(), "Erro: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Servico>> call, Throwable t) {
                Log.e("Error", "" + t.getMessage());
            }
        });
    }
}
