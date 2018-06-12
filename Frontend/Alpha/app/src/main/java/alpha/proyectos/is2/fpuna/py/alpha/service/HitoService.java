package alpha.proyectos.is2.fpuna.py.alpha.service;

import alpha.proyectos.is2.fpuna.py.alpha.service.model.Hito;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;
import java.util.UUID;

import static alpha.proyectos.is2.fpuna.py.alpha.Constantes.BASE_URL_API;

public interface HitoService {

    @GET(BASE_URL_API + "/proyectos/hitos")
    Call<List<Hito>> listar();

    @POST(BASE_URL_API + "/proyectos/hitos")
    Call<ResponseBody> crear(@Body Hito hito);

    @DELETE(BASE_URL_API + "/proyectos/hitos/{id}")
    Call<ResponseBody> eliminar(@Path("id") UUID id);

}

