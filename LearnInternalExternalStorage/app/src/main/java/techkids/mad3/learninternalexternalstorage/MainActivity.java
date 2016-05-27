package techkids.mad3.learninternalexternalstorage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;

/**
 * Created by TrungNT on 5/26/2016.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin, btnDownload;
    private Intent intentAction;
    private String path, email;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        displayMyAccount();
    }


    private void initComponent()
    {
        btnLogin = (Button) this.findViewById(R.id.btn_login);
        btnDownload = (Button) this.findViewById(R.id.btn_dơnnload);

        btnLogin.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id)
        {
            case R.id.btn_login:
                intentAction = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentAction);
                MainActivity.this.finish();
                break;

            case R.id.btn_dơnnload:
                intentAction = new Intent(MainActivity.this, DownloadActivity.class);
                startActivity(intentAction);
                MainActivity.this.finish();
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

    //load MyAccount: email in UI Main Activity
    private void displayMyAccount()
    {
        path = "/data/data/" + getPackageName() +  "/shared_prefs/" + Helper.fileName + ".xml";
        if(isFileExist(path))
        {
            sharedPreferences = getSharedPreferences(Helper.fileName, MODE_PRIVATE);
            email = sharedPreferences.getString("email", null);
        }
    }
}
