package org.elsys.valiolucho.businessmonefy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import java.io.File;
import java.io.FileInputStream;

public class DropboxUpload extends AsyncTask<String, Void, String> {

    private Context context;
    private DropboxAPI<AndroidAuthSession> mDBApi;
    private String path;
    private boolean isConnected = false;

    public DropboxUpload(Context context, DropboxAPI<AndroidAuthSession> mDBApi, String path) {
        this.context = context;
        this.mDBApi = mDBApi;
        this.path = path;
    }

    @Override
    protected String doInBackground(String... params) {
        DropboxAPI.Entry response = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        try {
            File file = new File(path);
            FileInputStream inputStream = new FileInputStream(file);
            if (isConnected) {
                response = mDBApi.putFile(path.substring(path.lastIndexOf('/') - 1), inputStream,
                        file.length(), null, null);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        if (response != null)
            return response.rev;
        else
            return "";
    }

    @Override
    protected void onPostExecute(String result) {
        if(!result.isEmpty()){
            Toast.makeText(context, "File Successfully Uploaded!", Toast.LENGTH_SHORT).show();
            Log.e("DbLog", "The uploaded file's rev is: " + result);
        }else {
            if (!isConnected) {
                Toast.makeText(context, "No connection", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Error occured", Toast.LENGTH_SHORT).show();
            }
        }
        File file = new File(path);
        if(file.exists()) file.delete();
    }
}
