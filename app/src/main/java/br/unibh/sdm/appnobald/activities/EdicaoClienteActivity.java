package br.unibh.sdm.appnobald.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.unibh.sdm.appnobald.R;
import br.unibh.sdm.appnobald.api.NobaldService;
import br.unibh.sdm.appnobald.api.RestServiceGenerator;
import br.unibh.sdm.appnobald.entidades.Cliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EdicaoClienteActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextEmail;
    private Button buttonSalvar;
    private NobaldService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao_cliente);

        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonSalvar = findViewById(R.id.buttonSalvar);

        service = RestServiceGenerator.createService(NobaldService.class, "http://lbnobald-1018607656.us-east-1.elb.amazonaws.com/nobald-cliente/");

        // Obtém o ID do cliente da intent
        long clienteId = getIntent().getLongExtra("clienteId", -1);
        if (clienteId != -1) {
            // Busca o objeto Cliente com base no ID
            buscaCliente(clienteId);
        } else {
            Log.e("EdicaoClienteActivity", "ID do cliente inválido");
            Toast.makeText(getApplicationContext(), "ID do cliente inválido", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void buscaCliente(long clienteId) {
        Call<Cliente> call = service.getCliente(clienteId);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful()) {
                    Cliente cliente = response.body();
                    if (cliente != null) {
                        // Preenche os campos com os dados do cliente
                        editTextNome.setText(cliente.getNome());
                        editTextEmail.setText(cliente.getEmail());

                        buttonSalvar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Atualiza os dados do cliente
                                cliente.setNome(editTextNome.getText().toString());
                                cliente.setEmail(editTextEmail.getText().toString());

                                salvarAlteracoesCliente(cliente);
                            }
                        });
                    } else {
                        Log.e("EdicaoClienteActivity", "Cliente não encontrado");
                        Toast.makeText(getApplicationContext(), "Cliente não encontrado", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    Log.e("EdicaoClienteActivity", "Erro ao buscar cliente: " + response.message());
                    Toast.makeText(getApplicationContext(), "Erro ao buscar cliente: " + response.message(), Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Log.e("EdicaoClienteActivity", "Erro ao buscar cliente: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Erro ao buscar cliente: " + t.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void salvarAlteracoesCliente(Cliente cliente) {
        Call<Cliente> call = service.atualizaCliente(cliente.getId(), cliente);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Alterações salvas com sucesso", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("EdicaoClienteActivity", "Erro ao salvar alterações: " + response.message());
                    Toast.makeText(getApplicationContext(), "Erro ao salvar alterações: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Log.e("EdicaoClienteActivity", "Erro ao salvar alterações: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Erro ao salvar alterações: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
