package org.elsys.valiolucho.businessmonefy;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CurrencyRates extends AsyncTask<Void, Void, BigDecimal>{

    private String from;
    private String to;
    private String link;
    private static final String LINK1 = "http://download.finance.yahoo.com/d/quotes.csv?s=";
    private static final String LINK2 =  "=X&f=sl1d1t1ba&e=.csvs";

    public CurrencyRates(String from, String to) {
        this.from = from;
        this.to = to;
        link = LINK1 + from + to + LINK2;
    }

    @Override
    protected BigDecimal doInBackground(Void... params) {
        try {
            URL url = new URL(link);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
