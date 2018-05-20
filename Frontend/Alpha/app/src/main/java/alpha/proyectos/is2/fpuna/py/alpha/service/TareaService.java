package alpha.proyectos.is2.fpuna.py.alpha.service;

import alpha.proyectos.is2.fpuna.py.alpha.service.model.Tarea;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

import static alpha.proyectos.is2.fpuna.py.alpha.Constantes.BASE_URL_API;

public interface TareaService {

    @GET(BASE_URL_API + "/tareas")
    Call<List<Tarea>> listar();

    @POST(BASE_URL_API + "/tareas")
    Call<ResponseBody> crear(@Body CrearTareaData user);

}

