package org.elsys.valiolucho.businessmonefy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session;
import java.math.BigDecimal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Button showGraphicsButton;
    private Button showLogsButton;

    private ImageButton plusImageButton;
    private ImageButton minusImageButton;

    private TextView incomeTV;
    private TextView outcomeTV;
    private TextView totalTV;

    private DropboxAPI<AndroidAuthSession> mDBApi;
    private final static String APP_KEY = "fha6o9qlsifojb3";
    private final static String APP_SECRET = "jhbwpo37ojnrxgr";
    private final static String DB_PREFS = "DropboxPreferences";
    private final static String TOKEN_NAME = "SharedPrefsTokenForDropbox";

    @Override
    protected void onResume() {
        super.onResume();
        if (mDBApi.getSession().authenticationSuccessful()) {
            try {
                mDBApi.getSession().finishAuthentication();
                String accessToken = mDBApi.getSession().getOAuth2AccessToken();
                storeDBToken(accessToken);
            } catch (IllegalStateException e) {
                Toast.makeText(getApplicationContext(), "Error during the authorization", Toast.LENGTH_SHORT).show();
            }
        }
        textViewsManager();
    }

    private void dropboxSession(){
        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);
        SharedPreferences prefs = getSharedPreferences(DB_PREFS, Context.MODE_PRIVATE);
        String token = prefs.getString(TOKEN_NAME, null);
        if(token != null) {
            session.setOAuth2AccessToken(token);
        } else {
            mDBApi.getSession().startOAuth2Authentication(MainActivity.this);
        }
    }

    private void storeDBToken(String token) {
        SharedPreferences prefs = getSharedPreferences(DB_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TOKEN_NAME, token);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setBackgroundColor(ContextCompat.getColor(this, R.color.NavDrBackGr));
        navigationView.setNavigationItemSelectedListener(this);

        incomeTV = (TextView) findViewById(R.id.incomeTV);
        outcomeTV = (TextView) findViewById(R.id.outcomeTV);
        totalTV = (TextView) findViewById(R.id.totalTV);
        textViewsManager();
        onClickButtonListeners();
        onClickImageButtonsListeners();
        dropboxSession();
    }

    private void textViewsManager() {
        DataBaseHelper myDb = DataBaseHelper.getInstance(getApplicationContext());
        DataProcess dataProcess = new DataProcess(myDb.getAllData("ASC"));
        myDb.close();
        BigDecimal incomings = dataProcess.getIncomings();
        BigDecimal outcomings = dataProcess.getOutcomings();
        incomeTV.setText(incomings.toPlainString());
        outcomeTV.setText(outcomings.toPlainString());
        BigDecimal total = (((incomings.subtract(outcomings.negate())).setScale(2, BigDecimal.ROUND_HALF_EVEN)).stripTrailingZeros());
        if (total.compareTo(BigDecimal.ZERO) == 1) {
            totalTV.setTextColor(ContextCompat.getColor(this, R.color.colorTrPlus));
        }else if(total.compareTo(BigDecimal.ZERO) == -1) {
            totalTV.setTextColor(ContextCompat.getColor(this, R.color.colorTrMinus));
        }
        totalTV.setText(total.toPlainString());
    }

    //make SHOW GRAPHICS and SHOW LOGS buttons, initialization and set onClickListener
    private void onClickButtonListeners() {
        showGraphicsButton = (Button) findViewById(R.id.buttonShowGraphics);
        showGraphicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent graphicsActivityIntent = new Intent(MainActivity.this, GraphicsActivity.class);
                startActivity(graphicsActivityIntent);
            }
        });

        showLogsButton = (Button) findViewById(R.id.buttonShowLogs);
        showLogsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logsActivityIntent = new Intent(MainActivity.this, LogsActivity.class);
                startActivity(logsActivityIntent);
            }
        });
    }

    private void onClickImageButtonsListeners() {
        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        plusImageButton = (ImageButton) findViewById(R.id.imageButtonPlus);
        plusImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent plusActivityIntent = new Intent(MainActivity.this, TransactionActivity.class);
                vibe.vibrate(30);
                startActivity(plusActivityIntent);
            }
        });

        minusImageButton = (ImageButton) findViewById(R.id.imageButtonMinus);
        minusImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent minusActivityIntent = new Intent(MainActivity.this, TransactionActivity.class);
                minusActivityIntent.putExtra("minus", true);
                vibe.vibrate(30);
                startActivity(minusActivityIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exitDialog();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //for future settings . . .
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    private ArrayList<Transaction> getDatabase() {
        DataBaseHelper myDbHelper = DataBaseHelper.getInstance(getApplicationContext());
        ArrayList<Transaction> data = myDbHelper.getAllData("DESC");
        myDbHelper.close();
        return data;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_delete_data) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete database");
            builder.setMessage("Are you sure ?");
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DataBaseHelper myDbHelper = DataBaseHelper.getInstance(getApplicationContext());
                    myDbHelper.deleteTable();
                    myDbHelper.close();
                    textViewsManager();
                    Toast.makeText(getApplicationContext(), "Database deleted", Toast.LENGTH_SHORT).show();
                }
            });
            builder.create().show();
        } else if (id == R.id.nav_saveCSV) {
            CsvGenerator csvGenerator = new CsvGenerator("BusinessMonefyDB.csv", getDatabase(), this);
            csvGenerator.generate();
            Toast.makeText(getApplicationContext(), "Database is exported to CSV", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_saveXLS) {
            XlsGenerator xlsGenerator = new XlsGenerator(getDatabase(), "businessMonefy.xls");
            xlsGenerator.generate();
            Toast.makeText(getApplicationContext(), "Database is exported to Excel file", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_exit) {
            exitDialog();
        }else if(id == R.id.nav_upload_db) {
            XlsGenerator xlsGenerator = new XlsGenerator(getDatabase(), "businessMonefyDb.xls");
            xlsGenerator.generate();
            new DropboxUpload(getApplicationContext(), mDBApi, "/storage/emulated/0/BusinessMonefy/businessMonefyDb.xls").execute();
        }/*else if(id == R.id.nav_settings) {
            Intent settingsActivityIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsActivityIntent);
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void exitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit ...");
        builder.setMessage("Are you sure ?");
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        });
        builder.create().show();
    }
}
