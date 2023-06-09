package br.unibh.sdm.appnobald.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.unibh.sdm.appnobald.R;
import br.unibh.sdm.appnobald.api.NobaldService;
import br.unibh.sdm.appnobald.api.RestServiceGenerator;
import br.unibh.sdm.appnobald.entidades.Cliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesClienteActivity extends AppCompatActivity {

    private NobaldService service = null;
    private ArrayAdapter<String> adapter;
    private List<Cliente> clienteList;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_cliente);

        service = RestServiceGenerator.createService(NobaldService.class, "http://lbnobald-1018607656.us-east-1.elb.amazonaws.com/nobald-cliente/");

        ListView listView = findViewById(R.id.listViewListaClientes);
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, new ArrayList<>());
        listView.setAdapter(adapter);

        // Adiciona o ouvinte de clique curto aos itens da lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtém o cliente selecionado
                Cliente clienteSelecionado = clienteList.get(position);
                Log.i("DetalhesClienteActivity", "Cliente selecionado: " + clienteSelecionado);

                // Iniciar a Activity de edição de cliente, passando o objeto Cliente
                Intent intent = new Intent(DetalhesClienteActivity.this, EdicaoClienteActivity.class);
                intent.putExtra("clienteId", clienteSelecionado.getId());
                startActivity(intent);
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar a Activity de criação de cliente
                Intent intent = new Intent(DetalhesClienteActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });

        buscaClientes();
        criaAcaoCliqueLongo();
    }

    private void criaAcaoCliqueLongo() {
        ListView listView = findViewById(R.id.listViewListaClientes);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("DetalhesClienteActivity", "Clicou em clique longo na posição " + position);
                final Cliente clienteSelecionado = clienteList.get(position);
                Log.i("DetalhesClienteActivity", "Selecionou o cliente " + clienteSelecionado.getNome());
                new AlertDialog.Builder(parent.getContext())
                        .setTitle("Removendo Cliente")
                        .setMessage("Tem certeza que quer remover o cliente " + clienteSelecionado.getNome() + "?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeCliente(clienteSelecionado);
                            }
                        })
                        .setNegativeButton("Não", null)
                        .show();
                return true;
            }
        });
    }

    private void removeCliente(Cliente cliente) {
        Log.i("DetalhesClienteActivity", "Vai remover cliente " + cliente.getNome());

        // Excluir o cliente pelo ID
        Call<Void> deleteCall = service.excluiCliente(cliente.getId());
        deleteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("DetalhesClienteActivity", "Removeu o cliente " + cliente.getNome());
                    Toast.makeText(getApplicationContext(), "Removeu o cliente " + cliente.getNome(), Toast.LENGTH_LONG).show();
                    onResume();
                } else {
                    Log.e("DetalhesClienteActivity", "Erro (" + response.code() + "): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code() + "): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("DetalhesClienteActivity", "Erro: " + t.getMessage());
            }
        });
    }

    public void buscaClientes() {
        Call<List<Cliente>> call = service.getCliente();
        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                if (response.isSuccessful()) {
                    Log.i("DetalhesClienteActivity", "Retornou " + response.body().size() + " Clientes!");
                    clienteList = response.body();
                    adapter.clear();
                    for (Cliente item : clienteList) {
                        if (item != null && item.getNome() != null) {
                            adapter.add(item.getNome());
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("DetalhesClienteActivity", "" + response.message());
                    Toast.makeText(getApplicationContext(), "Erro: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                Log.e("DetalhesClienteActivity", "Error: " + t.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        buscaClientes();
    }
}
