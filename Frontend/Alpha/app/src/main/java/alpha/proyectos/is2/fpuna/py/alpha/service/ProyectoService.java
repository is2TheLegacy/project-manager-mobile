package alpha.proyectos.is2.fpuna.py.alpha.service;

import alpha.proyectos.is2.fpuna.py.alpha.service.model.Proyecto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;
import java.util.UUID;

import static alpha.proyectos.is2.fpuna.py.alpha.Constantes.BASE_URL_API;

public interface ProyectoService {

    @GET(BASE_URL_API + "/proyectos")
    Call<List<Proyecto>> listar();

    @POST(BASE_URL_API + "/proyectos")
    Call<ResponseBody> crear(@Body Proyecto datos);

    @PUT(BASE_URL_API + "/proyectos/{id}")
    Call<ResponseBody> editar(@Path("id") UUID id, @Body Proyecto datos);

    @DELETE(BASE_URL_API + "/proyectos/{id}")
    Call<ResponseBody> eliminar(@Path("id") UUID id);

}

