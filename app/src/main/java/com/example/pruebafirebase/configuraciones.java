package com.example.pruebafirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link configuraciones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class configuraciones extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //String de textos de configuración
    String[] configu = new String[]{"App", "Regístrate", "Ayuda", "Soporte", "Acerca de la versión 1.0"};

    //Recordar usuario y contraseña", "Activar notificaciones

    public configuraciones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment configuraciones.
     */
    // TODO: Rename and change types and number of parameters
    public static configuraciones newInstance(String param1, String param2) {
        configuraciones fragment = new configuraciones();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_configuraciones, container, false);
        ListView listVieww = v.findViewById(R.id.listview1);



        ArrayAdapter<String> adaptador = new ArrayAdapter<String> (getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, configu);
        listVieww.setAdapter(adaptador);

        listVieww.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    //clicked apple

                    Intent intent = new Intent(getActivity(), ejemplo.class);
                    startActivity(intent);

                }else if(position==1){
                    //clicked orange
                    Intent intent = new Intent(getActivity(), registro.class);
                    startActivity(intent);

                }else{

                }
            }
        });

        // Inflate the layout for this fragment
        return v;
    }
}