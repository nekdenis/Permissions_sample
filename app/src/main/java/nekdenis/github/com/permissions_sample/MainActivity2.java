package nekdenis.github.com.permissions_sample;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity2 extends AppCompatActivity {

    private static final int REQUEST_READ_CONTACTS = 10;

    private View mainContainer;
    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainContainer = findViewById(R.id.main_container);
        emailTextView = (TextView) findViewById(R.id.main_email);

        if (checkPermissions()) {
            onPermissoinsGranted();
        }
    }

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissions();
            return false;
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.GET_ACCOUNTS},
                REQUEST_READ_CONTACTS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onPermissoinsGranted();
                } else {
                    Snackbar.make(mainContainer, R.string.permissions_denied, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.ask_permissons, onPermissionSnackbarClickListener).show();
                }
            }
        }
    }

    private void onPermissoinsGranted() {
        readUserEmail();
    }

    private void readUserEmail() {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                emailTextView.setText(account.name);
                break;
            }
        }
    }

    private View.OnClickListener onPermissionSnackbarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            requestPermissions();
        }
    };
}
