package alpha.proyectos.is2.fpuna.py.alpha;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import alpha.proyectos.is2.fpuna.py.alpha.activity.DashboardActivity;
import alpha.proyectos.is2.fpuna.py.alpha.activity.LoginActivity;
import alpha.proyectos.is2.fpuna.py.alpha.service.ServiceBuilder;
import alpha.proyectos.is2.fpuna.py.alpha.service.login.LoginService;
import alpha.proyectos.is2.fpuna.py.alpha.utils.PreferenceUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    private PreferenceUtils preferenceUtils;
    private LoginService service;

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onTokenRefresh() {

        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        preferenceUtils = new PreferenceUtils(MyFirebaseInstanceIDService.this);
        service = (LoginService) ServiceBuilder.create(LoginService.class);
        System.err.println("Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param tokenFirebase The new token.
     */
    private void sendRegistrationToServer(String tokenFirebase) {

        preferenceUtils.guardarTokenFirebase(tokenFirebase);
        String authToken = preferenceUtils.getAuthToken();

        if (authToken != null) {
            Call<ResponseBody> responseRegistrationId = service.registrationid(authToken, tokenFirebase);
            responseRegistrationId.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                }
            });
        }
    }
}
