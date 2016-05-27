package techkids.mad3.learninternalexternalstorage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by TrungNT on 5/26/2016.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin, btnDownload;
    private Intent intentAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
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
}
