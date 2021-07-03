package id.ghodel.cocbaselayout.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.UrlQuerySanitizer;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Muhammad Mayudin on 03-Jul-21.
 */
public class CocBaseTask extends AsyncTask<String, Integer, String> {

    private ProgressDialog pd;
    private Context context;
    private static final String TAG = CocBaseTask.class.getSimpleName();
    
    private ResultListener resultListener;
    
    public CocBaseTask(Context context, ResultListener resultListener){
        this.context = context;
        this.resultListener = resultListener;
    }
    

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(TAG, "PreExecute...");
        pd = new ProgressDialog(context);
        pd.setTitle("Progress...");
        pd.setIndeterminate(false);
        pd.setCancelable(false);
        pd.setMax(100);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        Log.i(TAG, "Do In Background.. ");

        HttpURLConnection connection=null;
        InputStream inputStream=null;
        BufferedReader reader=null;


        try {
            URL url=new URL(params[0]);
            connection=(HttpURLConnection)url.openConnection();
            connection.connect();
            int lenghtOfFile = connection.getContentLength();
            inputStream=connection.getInputStream();
            reader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer=new StringBuffer();
            String line="";
            long total = 0;

            while((line=reader.readLine())!=null)
            {
                total += line.length();
                publishProgress((int)((total*100)/lenghtOfFile));
                buffer.append(line);
                Log.d(TAG, buffer.toString());
            }
            String json=buffer.toString();

            return json;
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
//            resultListener.onError(e.getMessage());
        } catch (Exception e1){
            Log.e(TAG, e1.getMessage());
//            resultListener.onError(e1.getMessage());
        } finally {
            if(connection!=null)
            {
                connection.disconnect();
            }
            if(reader!=null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, e.getMessage());
                }
            }
        }


        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        Log.i(TAG, "On Progress Updates : " + values[0]);
        pd.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.i(TAG, "Post Execute result :: " + result);
        if(pd != null && pd.isShowing()){
            pd.dismiss();
            resultListener.onResponse(result);
        }

    }

    public static interface ResultListener {

        public void onResponse(String result);
        public void onError(String error);
    }

}


