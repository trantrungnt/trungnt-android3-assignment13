package techkids.mad3.learninternalexternalstorage;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by TrungNT on 5/24/2016.
 */
public class JsonService extends IntentService {
    private String login_status, login_message, link;
    private Bundle bundleGetData, bundlePutData;
    private String email, password;

    public JsonService() {
        super("JsonService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        URL url = null;
        bundleGetData = intent.getExtras();
        email = bundleGetData.getString("Email");
        password = bundleGetData.getString("Password");

        try{
            url = new URL("http://g-service.herokuapp.com/api/techkids/login?username="+ email + "&password=" + password);

            InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");
            char[] buff = new char[64];
            StringBuilder x = new StringBuilder();
            int numRead = 0;
            while ((numRead = reader.read(buff)) >= 0) {
                x.append(new String(buff, 0, numRead));
            }

            String result = x.toString();
            JSONObject resultObject = new JSONObject(result);
            login_status = resultObject.getString("login_status");
            login_message = resultObject.getString("login_message");

            intent = new Intent();
            bundlePutData = new Bundle();

            bundlePutData.putString("login_status", login_status);
            bundlePutData.putString("login_message", login_message);

            if (login_status == "1") {
                link = resultObject.getString("link");
                bundlePutData.putString("link", link);
            }

            intent.putExtra("Login_result", bundlePutData);

            intent.setAction("FILTER_ACTION_LOGIN");
            sendBroadcast(intent);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
