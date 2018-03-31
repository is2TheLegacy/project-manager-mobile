package alpha.proyectos.is2.fpuna.py.alpha.service.login;

import alpha.proyectos.is2.fpuna.py.alpha.Constantes;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by konecta on 04/03/18.
 */

public interface LoginService {

    @FormUrlEncoded
    @POST(Constantes.BASE_URL_API + "/sessions")
    Call<RespuestaLogin> login(@Field("user") String user, @Field("password") String password);

    @DELETE(Constantes.BASE_URL_API + "/sessions/{token}")
    Call<ResponseBody> logout(@Path("token") String token);
}
