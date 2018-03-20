package alpha.proyectos.is2.fpuna.py.alpha.service;

import java.util.List;

import alpha.proyectos.is2.fpuna.py.alpha.Constantes;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by konecta on 04/03/18.
 */

public interface LoginService {

    @FormUrlEncoded
    @POST(Constantes.BASE_URL_API + "/sessions")
    Call<RespuestaLogin> login(@Field("user") String user, @Field("password") String password);
}
