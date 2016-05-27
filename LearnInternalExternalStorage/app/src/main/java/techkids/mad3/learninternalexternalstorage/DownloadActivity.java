package techkids.mad3.learninternalexternalstorage;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by TrungNT on 5/26/2016.
 */
public class DownloadActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnDownloadMusic, btnDownloadPicture;
    private EditText editTextDownloadMusic, editTextDownloadPicture;
    private SharedPreferences sharedPreferences;
    private String email, password, path, login_status;
    private TextView tvDisplayMyAccount;
    private static final int READ_BLOCK_SIZE = 100;
    private BroadcastReceiver broadcastReceiver;
    private Bundle getBundleData, bundlePutData;
    private Intent intentPutData;

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
        downloadFromLinkFile();
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

    private void downloadFromLinkFile()
    {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                getBundleData = intent.getBundleExtra("Login_result");
                login_status = getBundleData.getString("login_status");
                Log.d("Download Activity", login_status);
            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter("FILTER_ACTION_LOGIN"));
    }

}
