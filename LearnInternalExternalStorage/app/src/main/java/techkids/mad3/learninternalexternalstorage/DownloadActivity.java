package techkids.mad3.learninternalexternalstorage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

/**
 * Created by TrungNT on 5/26/2016.
 */
public class DownloadActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnDownloadMusic, btnDownloadPicture;
    private EditText editTextDownloadMusic, editTextDownloadPicture;
    private SharedPreferences sharedPreferences;
    private String email, path;
    private TextView tvDisplayMyAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_download);
        initComponent();
        displayMyAccount();
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
                break;
            case R.id.btn_download_picture:
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
    private void displayMyAccount() {
        path = "/data/data/" + getPackageName() + "/shared_prefs/" + Helper.fileName + ".xml";
        if (isFileExist(path)) {
            sharedPreferences = getSharedPreferences(Helper.fileName, MODE_PRIVATE);
            email = sharedPreferences.getString("email", null);
            tvDisplayMyAccount.setText(email);
        }
    }
}
