package com.example.pruebafirebase;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link principal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class principal extends Fragment {


    ImageButton button;
    boolean cambio = false;
    String link = "https://staysafetdi.000webhostapp.com/estado.php?a=0";
    String est;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public principal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment principal.
     */
    // TODO: Rename and change types and number of parameters
    public static principal newInstance(String param1, String param2) {
        principal fragment = new principal();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_principal, container, false);
        button = (ImageButton) v.findViewById(R.id.imageButton2);

        button.setOnClickListener(imgButtonHandler);

        if(cambio == false){
            button.setBackgroundResource(R.drawable.apagado);
        }else{
            button.setBackgroundResource(R.drawable.encendido);

        }

        // Inflate the layout for this fragment
        return v;
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    View.OnClickListener imgButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {

            inicializarFirebase();

            if(cambio == false){

                est = "1";

                databaseReference.child("estado").child("1").child("estadoBi").setValue(est);

                //Cambia la imagen del boton
                button.setBackgroundResource(R.drawable.encendido);
                cambio = true;
            }else{
                est = "0";

                databaseReference.child("estado").child("1").child("estadoBi").setValue(est);

                //Cambia la imagen del boton
                button.setBackgroundResource(R.drawable.apagado);
                cambio = false;
            }
        }
    };

}