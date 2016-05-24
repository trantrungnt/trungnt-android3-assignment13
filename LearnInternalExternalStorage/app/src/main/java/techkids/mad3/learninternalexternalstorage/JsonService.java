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
    private Bundle bundleGetData;
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

            switch (login_status)
            {
                case "0":
                        //Mo thong bao cho nguoi dung khong dang nhap duoc
                        //intent = new
                    break;
                case "1":
                    break;
            }

//            String link = resultObject.getString("link");

            Log.d("Json cua Hung day ak? ", result);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
