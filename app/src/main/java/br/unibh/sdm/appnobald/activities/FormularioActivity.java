package br.unibh.sdm.appnobald.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.unibh.sdm.appnobald.R;
import br.unibh.sdm.appnobald.api.NobaldService;
import br.unibh.sdm.appnobald.api.RestServiceGenerator;
import br.unibh.sdm.appnobald.entidades.Cliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioActivity extends AppCompatActivity {

    private NobaldService service = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        setTitle("Adicionar Novo Cliente");

        service = RestServiceGenerator.createService(NobaldService.class, "http://lbnobald-1018607656.us-east-1.elb.amazonaws.com/nobald-cliente/");
        configuraBotaoSalvar();
        inicializaObjeto();
    }

    private void inicializaObjeto() {
        Intent intent = getIntent();
        if (intent.getSerializableExtra("objeto") != null) {
            Cliente objeto = (Cliente) intent.getSerializableExtra("objeto");
            EditText nome = findViewById(R.id.nome);
            EditText cpf = findViewById(R.id.cpf);
            EditText email = findViewById(R.id.email);
            EditText celular = findViewById(R.id.celular);
            EditText dataNascimento = findViewById(R.id.dataNasc);
            EditText login = findViewById(R.id.login);
            EditText senha = findViewById(R.id.senha);
            // Outros campos de entrada

            nome.setText(objeto.getNome());
            cpf.setText(objeto.getCpf());
            email.setText(objeto.getEmail());
            celular.setText(objeto.getCelular());
            dataNascimento.setText((CharSequence) objeto.getDataNascimento());
            login.setText(objeto.getLogin());
            senha.setText(objeto.getSenha());
            // Defina outros campos de entrada com os valores do objeto

            Button botaoSalvar = findViewById(R.id.buttonSalvar);
            botaoSalvar.setText("Atualizar");
        }
    }

    private void configuraBotaoSalvar() {
        Button buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cliente cliente = recuperaInformacoesFormulario();
                Intent intent = getIntent();
                if (intent.getSerializableExtra("objeto") != null) {
                    Cliente objeto = (Cliente) intent.getSerializableExtra("objeto");
                    cliente.setId(objeto.getId());
                    if (validaFormulario(cliente)) {
                        atualizaCliente(cliente);
                    }
                } else {
                    if (validaFormulario(cliente)) {
                        salvaCliente(cliente);
                    }
                }
            }
        });
    }

    private boolean validaFormulario(Cliente cliente) {
        String nome = cliente.getNome();
        String cpf = cliente.getCpf();
        String email = cliente.getEmail();
        String celular = cliente.getCelular();
        String dataNascimento = String.valueOf(cliente.getDataNascimento());
        String login = cliente.getLogin();
        String senha = cliente.getSenha();

        if (nome.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Nome é obrigatório", Toast.LENGTH_LONG).show();
            return false;
        }

        if (cpf.isEmpty()) {
            Toast.makeText(getApplicationContext(), "CPF é obrigatório", Toast.LENGTH_LONG).show();
            return false;
        }

        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Email é obrigatório", Toast.LENGTH_LONG).show();
            return false;
        }

        if (celular.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Celular é obrigatório", Toast.LENGTH_LONG).show();
            return false;
        }

        if (dataNascimento.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Data de Nascimento é obrigatória", Toast.LENGTH_LONG).show();
            return false;
        }

        if (login.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Login é obrigatório", Toast.LENGTH_LONG).show();
            return false;
        }

        if (senha.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Senha é obrigatória", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void salvaCliente(Cliente cliente) {
        Call<Cliente> call = service.criaCliente(cliente);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful()) {
                    Log.i("FormularioCliente", "Salvou o Cliente " + cliente.getId());
                    Toast.makeText(getApplicationContext(), "Salvou o Cliente " + cliente.getId(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("FormularioCliente", "Erro (" + response.code() + "): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code() + "): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Log.e("FormularioCliente", "Erro: " + t.getMessage());
            }
        });
    }

    private void atualizaCliente(Cliente cliente) {
        Call<Cliente> call = service.atualizaCliente(cliente.getId(), cliente);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful()) {
                    Log.i("FormularioCliente", "Atualizou o Cliente " + cliente.getId());
                    Toast.makeText(getApplicationContext(), "Atualizou o Cliente " + cliente.getId(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("FormularioCliente", "Erro (" + response.code() + "): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code() + "): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Log.e("FormularioCliente", "Erro: " + t.getMessage());
            }
        });
    }

    private Cliente recuperaInformacoesFormulario() {
        EditText nomeEditText = findViewById(R.id.nome);
        EditText cpfEditText = findViewById(R.id.cpf);
        EditText emailEditText = findViewById(R.id.email);
        EditText celularEditText = findViewById(R.id.celular);
        EditText dataNascimentoEditText = findViewById(R.id.dataNasc);
        EditText loginEditText = findViewById(R.id.login);
        EditText senhaEditText = findViewById(R.id.senha);

        String nome = nomeEditText.getText().toString();
        String cpf = cpfEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String celular = celularEditText.getText().toString();
        String dataNascimentoStr = dataNascimentoEditText.getText().toString();
        String login = loginEditText.getText().toString();
        String senha = senhaEditText.getText().toString();

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setEmail(email);
        cliente.setCelular(celular);

        // Conversão da String de data de nascimento para um objeto Date
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dataNascimento = dateFormat.parse(dataNascimentoStr);
            cliente.setDataNascimento(dataNascimento);
        } catch (ParseException e) {
            // Trate o erro de conversão da data aqui
            e.printStackTrace();
        }

        cliente.setLogin(login);
        cliente.setSenha(senha);

        return cliente;
    }
}
