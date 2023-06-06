package br.unibh.sdm.appnobald.api;
import java.util.List;

import br.unibh.sdm.appnobald.entidades.Barbeiro;
import br.unibh.sdm.appnobald.entidades.Cliente;
import br.unibh.sdm.appnobald.entidades.Servico;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NobaldService {
    @Headers({
            "Accept: application/json",
            "User-Agent: AppNobald"
    })
    @GET("barbeiro")
    Call<List<Barbeiro>> getBarbeiro();

    @GET("barbeiro/{id}")
    Call<Barbeiro> getBarbeiro(@Path("id") String id);

    @POST("barbeiro")
    Call<Barbeiro> criaBarbeiro(@Body Barbeiro barbeiro);

    @PUT("barbeiro/{id}")
    Call<Barbeiro> atualizaBarbeiro(@Path("id") String id, @Body Barbeiro barbeiro);

    @DELETE("barbeiro/{id}")
    Call<Boolean> excluiBarbeiro(@Path("id") String id);



    @GET("servico")
    Call<List<Servico>> getServico();

    @GET("servico/{id}")
    Call<Servico> getServico(@Path("id") String id);

    @POST("servico")
    Call<Servico> criaServico(@Body Servico servico);

    @PUT("servico/{id}")
    Call<Servico> atualizaServico(@Path("id") String id, @Body Servico servico);

    @DELETE("servico/{id}")
    Call<Boolean> excluiServico(@Path("id") String id);



    @GET("cliente")
    Call<List<Cliente>> getCliente();

    @GET("cliente/{id}")
    Call<Cliente> getCliente(@Path("id") Long id);

    @POST("cliente")
    Call<Cliente> criaCliente(@Body Cliente cliente);

    @PUT("cliente/{id}")
    Call<Cliente> atualizaCliente(@Path("id") Long id, @Body Cliente cliente);

    @DELETE("cliente/{id}")
    Call<Boolean> excluiCliente(@Path("id") Long id);
}
