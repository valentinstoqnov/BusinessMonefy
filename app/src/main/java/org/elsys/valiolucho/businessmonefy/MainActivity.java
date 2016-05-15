package org.elsys.valiolucho.businessmonefy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import net.rdrei.android.dirchooser.DirectoryChooserConfig;
import net.rdrei.android.dirchooser.DirectoryChooserFragment;

import org.elsys.valiolucho.calculator.CalcActivity;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        DirectoryChooserFragment.OnFragmentInteractionListener{

    private Button showGraphicsButton;
    private Button showLogsButton;

    private ImageButton plusImageButton;
    private ImageButton minusImageButton;

    private TextView incomeTV;
    private TextView outcomeTV;
    private TextView totalTV;

    @Override
    protected void onResume() {
        super.onResume();
        textViewsManager();
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
        navigationView.setNavigationItemSelectedListener(this);

        incomeTV = (TextView) findViewById(R.id.incomeTV);
        outcomeTV = (TextView) findViewById(R.id.outcomeTV);
        totalTV = (TextView) findViewById(R.id.totalTV);
        textViewsManager();
        onClickButtonListeners();
        onClickImageButtonsListeners();
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
        plusImageButton = (ImageButton) findViewById(R.id.imageButtonPlus);
        plusImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent plusActivityIntent = new Intent(MainActivity.this, CalcActivity.class);
                startActivity(plusActivityIntent);
            }
        });

        minusImageButton = (ImageButton) findViewById(R.id.imageButtonMinus);
        minusImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent minusActivityIntent = new Intent(MainActivity.this, CalcActivity.class);
                minusActivityIntent.putExtra("minus", true);
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
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
    }

    private ArrayList<Transaction> getDatabase() {
        DataBaseHelper myDbHelper = DataBaseHelper.getInstance(getApplicationContext());
        ArrayList<Transaction> data = myDbHelper.getAllData("DESC");
        myDbHelper.close();
        return data;
    }

    private String dir = getApplicationContext().getFilesDir().getAbsolutePath();
    private DirectoryChooserFragment mDialog;

    private String getDir() {
        DirectoryChooserConfig config = DirectoryChooserConfig.builder()
                .newDirectoryName("BusinessMonefy")
                .build();
        mDialog = DirectoryChooserFragment.newInstance(config);
        mDialog.show(getFragmentManager(), null);
        return dir;
    }

    @Override
    public void onSelectDirectory(@NonNull String path) {
        dir = path;
        mDialog.dismiss();
    }

    @Override
    public void onCancelChooser() {
        mDialog.dismiss();
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
                }
            });
            builder.create().show();
        } else if (id == R.id.nav_saveCSV) {
            CsvGenerator csvGenerator = new CsvGenerator("BusinessMonefyDB.csv", dir, getDatabase(), this);
            csvGenerator.generate();
        } else if (id == R.id.nav_saveXLS) {
            XlsGenerator xlsGenerator = new XlsGenerator("BusinessMonefyDB.xls", dir, getDatabase(), this);
            xlsGenerator.generate();
        } else if (id == R.id.nav_exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit ...");
            builder.setMessage("Are you sure ?");
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton("Ëxit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    System.exit(0);
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
