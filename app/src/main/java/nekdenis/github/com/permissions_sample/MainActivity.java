package nekdenis.github.com.permissions_sample;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailTextView = (TextView) findViewById(R.id.main_email);
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
}
