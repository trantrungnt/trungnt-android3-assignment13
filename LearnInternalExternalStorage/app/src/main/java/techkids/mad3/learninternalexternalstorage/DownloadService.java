package techkids.mad3.learninternalexternalstorage;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
    private String fileNameDownload = Helper.fileNameDownload + ".txt";
    private String pathFileCache;
    private String contentFile;
    private int indexNull;

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
        urlPicture = contentURL.substring(endURLMusic + 4);

        Log.d("URL Music", urlMusic);
        Log.d("URL Picture", urlPicture);
        Log.d("Receive link download", linkDownload);
        Log.d("text", readContentFileFromURL(linkDownload));
        Log.d("PathCacheDS", getCacheDir().getAbsolutePath() + "/" + fileNameDownload);

        putDataURL(urlMusic, urlPicture);

        pathFileCache = getCacheDir().getAbsolutePath() + "/" + fileNameDownload;
        getDataFromFileCache(pathFileCache);
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

    private String readFile(String path)
    {
        File tempFile=null;
        tempFile = new File(path);
        String strLine="";

        try {
            FileReader fReader = new FileReader(tempFile);
            BufferedReader bReader = new BufferedReader(fReader);

            /** Reading the contents of the file: line by line */
            while( (strLine=bReader.readLine()) != null  ){
                contentFile += strLine;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return contentFile;
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

    //check file Exist
    private boolean isFileExist(String path)
    {
        boolean check=false;
        File file = new File(path);
        if (file.exists())
            check = true;

        return check;
    }

    private void getDataFromFileCache(String path)
    {
        if (isFileExist(path)) {
            contentFile = readFile(path);
            endURLMusic = contentFile.indexOf(".mp3");
            urlPicture = contentFile.substring(endURLMusic + 4);
            urlMusic = contentFile.substring(4, endURLMusic);
            downloadToExternalStorage(urlMusic);
            downloadToInternalStorage(urlPicture);
            Log.d("ContentRFURL", urlMusic);
            Log.d("ContentRFURL", urlPicture);
            Log.d("ContentRF", readFile(path));
            Log.d("FileStatus", "File Exist");
        }
        else {
            Log.d("FileStatus", "File not Exist");
        }
    }


    private void downloadToInternalStorage(String link) {
        HttpURLConnection httpURLConnection;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        File file = null;
        try {
            file = new File(getFilesDir(), Helper.INTERNAL_STORAGE_FILE_NAME);

            outputStream = new FileOutputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL(link);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            int statusCode = httpURLConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                byte data[] = new byte[1024];

                int count;
                while ((count = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, count);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void downloadToExternalStorage(String downloadLink) {
        downloadLink = "http://nhac.vui.vn/download.php?id=4337";

        HttpURLConnection httpURLConnection;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        File file = null;
        try {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), Helper.EXTERNAL_STORAGE_FILE_NAME);
            outputStream = new FileOutputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            URL url = new URL(downloadLink);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            int statusCode = httpURLConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                byte data[] = new byte[1024];

                int count;
                while ((count = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, count);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
