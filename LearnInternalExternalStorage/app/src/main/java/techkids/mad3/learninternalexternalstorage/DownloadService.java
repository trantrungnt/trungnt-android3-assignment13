package techkids.mad3.learninternalexternalstorage;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by TrungNT on 5/27/2016.
 */
public class DownloadService extends IntentService {
    private static final int READ_BLOCK_SIZE = 100;
    private String linkDownload;
    private Bundle getBundleData, putBundleData;
    private String contentURL, urlMusic, urlPicture;
    private int endURLMusic;
    private Intent putIntentData;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getBundleData = intent.getExtras();
        linkDownload = getBundleData.getString("link");
        contentURL = readContentFileFromURL(linkDownload);
        endURLMusic = contentURL.indexOf(".mp3");
        urlMusic = contentURL.substring(0, endURLMusic + 4);
        urlPicture = contentURL.substring(endURLMusic +4);

        Log.d("URL Music", urlMusic);
        Log.d("URL Picture", urlPicture);
        Log.d("Receive link download", linkDownload);
        Log.d("text", readContentFileFromURL(linkDownload));

        putDataURL(urlMusic, urlPicture);
    }

    private void putDataURL(String urlMusic, String urlPicture)
    {
        putIntentData = new Intent();
        putBundleData = new Bundle();
        putBundleData.putString("URLMusic", urlMusic);
        putBundleData.putString("URLPicture", urlPicture);
        putIntentData.putExtra("link_result", putBundleData);
        putIntentData.setAction("FILTER_ACNTION_DOWLOAD");
        sendBroadcast(putIntentData);
    }

    private void readFile(String filename)
    {
        //reading text from file
        try {
            FileInputStream fileIn = openFileInput(filename);
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();

            Log.d("Read my file:", s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveTempFile(Context context, String url) {
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            File.createTempFile(fileName, null, context.getCacheDir());
        }
        catch(IOException e){
            Log.d("Error", e.toString());
        }
    }

    private String readContentFileFromURL(String urlPath) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String stringText = "";
        URL textUrl;
        try {
            textUrl = new URL(urlPath);
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(textUrl.openStream()));
            String StringBuffer;

            while ((StringBuffer = bufferReader.readLine()) != null) {
                stringText += StringBuffer;
            }
            bufferReader.close();

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return stringText;
    }
}
