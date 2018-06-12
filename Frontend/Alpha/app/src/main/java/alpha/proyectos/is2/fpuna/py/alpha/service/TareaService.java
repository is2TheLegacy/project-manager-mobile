package alpha.proyectos.is2.fpuna.py.alpha.service;

import alpha.proyectos.is2.fpuna.py.alpha.service.model.Comentario;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Proyecto;
import alpha.proyectos.is2.fpuna.py.alpha.service.model.Tarea;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;
import java.util.UUID;

import static alpha.proyectos.is2.fpuna.py.alpha.Constantes.BASE_URL_API;

public interface TareaService {

    @GET(BASE_URL_API + "/tareas")
    Call<List<Tarea>> listar();

    @POST(BASE_URL_API + "/tareas")
    Call<ResponseBody> crear(@Body CrearTareaData tarea);

    @PUT(BASE_URL_API + "/tareas/{id}")
    Call<ResponseBody> editar(@Path("id") UUID id, @Body Tarea datos);

    @DELETE(BASE_URL_API + "/tareas/{id}")
    Call<ResponseBody> eliminar(@Path("id") UUID id);

    @GET(BASE_URL_API + "/tareas/{idTarea}/comentarios")
    Call<List<Comentario>> getComentarios(@Path("idTarea") String idTarea);

    @POST(BASE_URL_API + "/tareas/{idTarea}/comentarios")
    Call<ResponseBody> crearComentario(@Path("idTarea") String idTarea, @Body Comentario comentario);

}

