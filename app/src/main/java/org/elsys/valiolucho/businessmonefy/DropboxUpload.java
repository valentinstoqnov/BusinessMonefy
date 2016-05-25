package org.elsys.valiolucho.businessmonefy;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import java.io.File;
import java.io.FileInputStream;

public class DropboxUpload extends AsyncTask<String, Void, String> {

    Context context;
    DropboxAPI<AndroidAuthSession> mDBApi;
    String path;

    public DropboxUpload(Context context, DropboxAPI<AndroidAuthSession> mDBApi, String path) {
        this.context = context;
        this.mDBApi = mDBApi;
        this.path = path;
    }

    @Override
    protected String doInBackground(String... params) {
        DropboxAPI.Entry response = null;
        try {
            File file = new File(path);
            FileInputStream inputStream = new FileInputStream(file);
            response = mDBApi.putFile(path.substring(path.lastIndexOf('/') - 1), inputStream,
                    file.length(), null, null);
            Log.e("DropboxUpload", "The uploaded file's rev is: " + response.rev);
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
            File file = new File(path);
            file.delete();
        }
    }
}
