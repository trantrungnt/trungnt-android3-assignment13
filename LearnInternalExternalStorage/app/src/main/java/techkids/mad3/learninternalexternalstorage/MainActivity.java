package techkids.mad3.learninternalexternalstorage;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//http://iliat.org/download.txt
//user: android%40hungdepzai.techkids.vn, pass: 123456

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail, editTextPassword;
    private Button btnLogin;
    private Bundle getBundleData, bundleData;
    private Intent getIntentData, intentData;
    private String email, password, login_status, login_message;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        listenBroadRececive();
    }

    private void listenBroadRececive()
    {
        getIntentData = new Intent();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                getBundleData = intent.getBundleExtra("Login_result");
                login_status = getBundleData.getString("login_status");
                login_message =  getBundleData.getString("login_message");

              switch (login_status) {
                  case "0":
                      showAlertDialog("WARNING ...", login_message);
                      break;

                  case "1":
                      showAlertDialog("INFORMATION ...", login_message);
                      break;
              }
            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter("FILTER_ACTION_LOGIN"));
    }

    private void initComponent() {
        editTextEmail = (EditText) this.findViewById(R.id.editTextMail);
        editTextPassword = (EditText) this.findViewById(R.id.editTextPassword);
        btnLogin = (Button) this.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();

        if (id == R.id.btn_login) {
            intentData = new Intent(MainActivity.this, JsonService.class);
            bundleData = new Bundle();
            bundleData.putString("Email", email);
            bundleData.putString("Password", password);
            intentData.putExtras(bundleData);
            startService(intentData);
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
}
