package techkids.mad3.learninternalexternalstorage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail, editTextPassword;
    private Button btnLogin;
    private Bundle getBundleData, bundleData;
    private Intent getIntentData, intentData;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
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
