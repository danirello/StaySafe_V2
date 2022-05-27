package com.example.pruebafirebase;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.messaging.FirebaseMessaging;

public class login extends AppCompatActivity {

    String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_Staysafe);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button btn_ingresar = findViewById(R.id.btn_inicio);
        final EditText txt_nombre = findViewById(R.id.txt_nombre);
        final EditText txt_contra = findViewById(R.id.txt_pass);

        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String nombre = txt_nombre.getText().toString();
                String contra = txt_contra.getText().toString();

                try {
                    res = new Enlace(login.this).execute(nombre, contra).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                validar(res);
                finish();
            }
        });


    }

    protected void validar(String resultado){

        if (resultado.equals("1")) {
            Toast.makeText(this, "Bienvenido",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, barra_nav.class);
            startActivity(intent);
        }else if(resultado.equals("0")){
            Toast.makeText(this, "Usuario o contraseña incorrectos",Toast.LENGTH_LONG).show();
        }
    }


    public static class Enlace extends AsyncTask<String, Void, String> {

        private WeakReference<Context> context;

        public Enlace(Context context){
            this.context = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(String... params) {

            String iniciar_url = "https://staysafetdi.000webhostapp.com/ingresar.php";
            String resultado = "";

            try {
                URL url = new URL(iniciar_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod ("POST");
                httpURLConnection.setDoOutput (true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter (new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String usuario = params[0];
                String contraseña = params[1];

                String data = URLEncoder.encode("usuario", "UTF-8") + "=" + URLEncoder.encode (usuario, "UTF-8") +
                        "&" + URLEncoder.encode("contraseña", "UTF-8") + "=" + URLEncoder.encode (contraseña, "UTF-8") ;

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
