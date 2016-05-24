package techkids.mad3.learninternalexternalstorage;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by TrungNT on 5/24/2016.
 */
public class JsonService extends IntentService {

    public JsonService() {
        super("JsonService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        URL url = null;

        try{
            url = new URL("http://g-service.herokuapp.com/api/techkids/login?username=android%40hungdepzai.techkids.vn&password=123456");
            InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");
            char[] buff = new char[64];
            StringBuilder x = new StringBuilder();
            int numRead = 0;
            while ((numRead = reader.read(buff)) >= 0) {
                x.append(new String(buff, 0, numRead));
            }

            String result = x.toString();
            Log.d("Json cua Hung day ak? ", result);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
