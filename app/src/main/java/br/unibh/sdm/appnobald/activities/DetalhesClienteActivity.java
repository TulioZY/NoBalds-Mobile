package br.unibh.sdm.appnobald.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.unibh.sdm.appnobald.R;
import br.unibh.sdm.appnobald.api.NobaldService;
import br.unibh.sdm.appnobald.api.RestServiceGenerator;
import br.unibh.sdm.appnobald.entidades.Barbeiro;
import br.unibh.sdm.appnobald.entidades.BarbeiroAdapter;
import br.unibh.sdm.appnobald.entidades.Cliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesClienteActivity extends AppCompatActivity {

    private NobaldService service = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_cliente);
        service = RestServiceGenerator.createService(NobaldService.class, "http://lbnobald-1018607656.us-east-1.elb.amazonaws.com/nobald-cliente/");
        buscaClientes();
    }

    public void buscaClientes(){
        NobaldService service = RestServiceGenerator.createService(NobaldService.class, "http://lbnobald-1018607656.us-east-1.elb.amazonaws.com/nobald-cliente/");
        Call<List<Cliente>> call = service.getCliente();
        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                if (response.isSuccessful()) {
                    Log.i("Cliente", "Retornou " + response.body().size() + " Clientes!");
                    List<String> lista2 = new ArrayList<String>();
                    for (Cliente item : response.body()) {
                        if (item != null) {
                            if (item.getNome() != null) {
                                lista2.add(item.getNome());
                            }
                            if (item.getCpf() != null) {
                                lista2.add(item.getCpf());
                            }
                            if (item.getEmail() != null) {
                                lista2.add(item.getEmail());
                            }
                            if (item.getCelular() != null) {
                                lista2.add(item.getCelular());
                            }
                            if (item.getDataNascimento() != null) {
                                lista2.add(String.valueOf(item.getDataNascimento()));
                            }
                            if (item.getLogin() != null) {
                                lista2.add(item.getLogin());
                            }
                            if (item.getSenha() != null) {
                                lista2.add(item.getSenha());
                            }
                        }
                    }
                    Log.i("DetalhesClienteActivity", lista2.toArray().toString());
                    ListView listView = findViewById(R.id.listViewListaClientes);
                    listView.setAdapter(new ArrayAdapter<String>(DetalhesClienteActivity.this,
                            android.R.layout.simple_list_item_1,
                            lista2));
                } else {
                    Log.e("Cliente", "" + response.message());
                    Toast.makeText(getApplicationContext(), "Erro: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                Log.e("Error", "" + t.getMessage());
            }
        });
    }

}