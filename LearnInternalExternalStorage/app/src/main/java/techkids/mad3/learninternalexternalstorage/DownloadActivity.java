package techkids.mad3.learninternalexternalstorage;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by TrungNT on 5/26/2016.
 */
public class DownloadActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnDownloadMusic, btnDownloadPicture;
    private EditText editTextDownloadMusic, editTextDownloadPicture;
    private SharedPreferences sharedPreferences;
    private String email, password, path, login_status, link;
    private TextView tvDisplayMyAccount;
    private BroadcastReceiver broadcastReceiver, broadcastReceiverDownloadService;
    private Bundle getBundleData, bundlePutData;
    private Intent intentPutData;
    private String urlMusic, urlPicture;
    private String fileNameDownload = Helper.alertDownload + ".txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_download);
        initComponent();
        displayMyAccount();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        initJsonService();
        downloadFromLinkFileBroadReceive();
    }


    private void initComponent()
    {
        btnDownloadMusic = (Button) this.findViewById(R.id.btn_download_music);
        btnDownloadPicture = (Button) this.findViewById(R.id.btn_download_picture);
        btnDownloadMusic.setOnClickListener(this);
        btnDownloadPicture.setOnClickListener(this);
        editTextDownloadMusic = (EditText) this.findViewById(R.id.editDownloadMusic);
        editTextDownloadPicture = (EditText) this.findViewById(R.id.editDownloadPicture);
        tvDisplayMyAccount = (TextView) this.findViewById(R.id.tvDislplayMyAccount);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id)
        {
            case R.id.btn_download_music:
                showAlertDialog("INFORMATION ...", Helper.alertDownload);
                break;
            case R.id.btn_download_picture:
                showAlertDialog("INFORMATION ...", Helper.alertDownload);
                break;
        }
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

    //load MyAccount: email in UI Download Activity
    private void displayMyAccount() {
        path = "/data/data/" + getPackageName() + "/shared_prefs/" + Helper.fileName + ".xml";
        if (isFileExist(path)) {
            sharedPreferences = getSharedPreferences(Helper.fileName, MODE_PRIVATE);
            email = sharedPreferences.getString("email", null);
            tvDisplayMyAccount.setText(email);
        }
    }

    private void showAlertDialog (String titleDialog, String contentDialog)
    {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(titleDialog);
        builder.setMessage(contentDialog);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    //khoi tao bat su dung Json Service
    private void initJsonService()
    {
        path = "/data/data/" + getPackageName() + "/shared_prefs/" + Helper.fileName + ".xml";

        if (isFileExist(path)) {
            sharedPreferences = getSharedPreferences(Helper.fileName, MODE_PRIVATE);
            email = sharedPreferences.getString("email", null);
            password = sharedPreferences.getString("password", null);
            intentPutData = new Intent(DownloadActivity.this, JsonService.class);
            bundlePutData = new Bundle();
            bundlePutData.putString("Email", email);
            bundlePutData.putString("Password", password);
            intentPutData.putExtras(bundlePutData);
            startService(intentPutData);
        }
    }

    private void downloadFromLinkFileBroadReceive()
    {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                getBundleData = intent.getBundleExtra("Login_result");
                login_status = getBundleData.getString("login_status");
                link = getBundleData.getString("link");

                Log.d("Download Activity", login_status);
                Log.d("Link Download Activity", link);
                Log.d("path cache", context.getCacheDir().getAbsolutePath());

                initDownloadService(link);
                broadCastReceiveListenDownloadService();
            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter("FILTER_ACTION_LOGIN"));
    }

    private void initDownloadService(String link)
    {
        intentPutData = new Intent(DownloadActivity.this, DownloadService.class);
        bundlePutData = new Bundle();
        bundlePutData.putString("link", link);
        intentPutData.putExtras(bundlePutData);
        startService(intentPutData);
    }

    private void broadCastReceiveListenDownloadService()
    {
        broadcastReceiverDownloadService = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               getBundleData = intent.getBundleExtra("link_result");
               urlMusic = getBundleData.getString("URLMusic");
               urlPicture = getBundleData.getString("URLPicture");
               editTextDownloadMusic.setText(urlMusic);
               editTextDownloadPicture.setText(urlPicture);

                path = context.getCacheDir().getAbsolutePath() + "/" + fileNameDownload;

                if (!isFileExist(path))
                    saveTempFile(context, urlMusic, urlPicture);
            }
        };

        registerReceiver(broadcastReceiverDownloadService, new IntentFilter("FILTER_ACNTION_DOWLOAD"));
    }

    private void saveTempFile(Context context, String urlMusic, String urlPicture) {
        File file;
        FileOutputStream outputStream;

        try {
            file = new File(context.getCacheDir(), fileNameDownload);
            outputStream = openFileOutput(fileNameDownload, Context.MODE_PRIVATE);
            outputStream.write(urlMusic.getBytes());
            outputStream.write(urlPicture.getBytes());
            outputStream.close();
        }
        catch(IOException e){
            Log.d("Error", e.toString());
        }
    }

}
