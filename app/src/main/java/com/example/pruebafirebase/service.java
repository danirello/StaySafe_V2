package com.example.pruebafirebase;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

public class service extends Service {

    String al = "1";
    String res;

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int idProcess){


        new Thread(new Runnable(){
            public void run() {
                // TODO Auto-generated method stub
                loop();
            }
        }).start();


        return START_STICKY;
    }


    public void loop(){

        while(true)
        {
          //  try {
                //Thread.sleep(50000);

                try {
                    res = new Enlace(service.this).execute(al).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, res,Toast.LENGTH_LONG).show();

                validar(res);

           // } //catch (InterruptedException e) {
              //  e.printStackTrace();
           // }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void validar(String resultado){

        if (resultado.equals("1")) {
            Toast.makeText(this, "Alerta predida.",Toast.LENGTH_LONG).show();
        }else if(resultado.equals("0")){
            Toast.makeText(this, "Alerta apagada.",Toast.LENGTH_LONG).show();
        }
    }


    public static class Enlace extends AsyncTask<String, Void, String> {

        private WeakReference<Context> context;

        public Enlace(Context context){
            this.context = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(String... params) {



            String iniciar_url = "https://staysafetdi.000webhostapp.com/GetDataAlerta.php";
            String resultado = "";

            try {
                URL url = new URL(iniciar_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod ("POST");
                httpURLConnection.setDoOutput (true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter (new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String alert = params[0];

                String data = URLEncoder.encode("alerta", "UTF-8") + "=" + URLEncoder.encode (alert, "UTF-8") ;

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputstream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
                StringBuilder stringBuilder = new StringBuilder();

                String line;
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }

                resultado = stringBuilder.toString();

                bufferedReader.close();
                inputstream.close();
                httpURLConnection.disconnect();


            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resultado;
        }

    }

}
