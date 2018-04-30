package alpha.proyectos.is2.fpuna.py.alpha.service;

import java.util.List;

import alpha.proyectos.is2.fpuna.py.alpha.service.usuarios.Usuario;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static alpha.proyectos.is2.fpuna.py.alpha.Constantes.BASE_URL_API;

/**
 * @author federico.torres
 */

public interface UsuarioService {

    @POST(BASE_URL_API + "/usuarios")
    Call<ResponseBody> create(@Body Usuario user);

    @POST(BASE_URL_API + "/usuarios/{uuid}/credential")
    @FormUrlEncoded
    Call<ResponseBody> credential(@Path("uuid") String uuid, @Field("new_password") String new_password);

    @DELETE(BASE_URL_API + "/usuarios/{uuid}")
    Call<ResponseBody> delete(@Path("uuid") String uuid);

}
