package com.aplicacion.myapply;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TablaActivity2 extends AppCompatActivity {
    ListView listview1;
    Button btnAtas, btnUbicar, btnElimi;
    List<Persona> empleList;
    String num, nom, not1, not;
    ArrayList<Persona> lista =new ArrayList<Persona>();;
    ArrayList<String> arrayEmple;
    String posicion;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla2);
        listview1=findViewById(R.id.txtrecycle);
        empleList = new ArrayList<>();
        arrayEmple = new ArrayList<String>();
        SendRequest();
        btnAtas=findViewById(R.id.btnAtras);
        btnAtas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), TablaActivity2.class);
                startActivity(intent);
            }
        });

        btnUbicar=findViewById(R.id.btnUbicacion);
        btnUbicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        btnElimi=findViewById(R.id.btnEliminar);
        btnElimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           // Delete();
            }
        }
        );

        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String info=" ID " + lista.get(position).getId()+"\n";
                info+= "Nombre : " +lista.get(position).getNombre();
                posicion=lista.get(position).getId()+"";
                nom=lista.get(position).getNombre ()+"";
                num=lista.get(position).getTelefono ()+"";
                not=lista.get(position).getLatitud ()+"";
                not1=lista.get(position).getLongitud ()+"";
                String listChoice=(listview1.getItemAtPosition(position).toString());
                Toast.makeText(getApplicationContext(),listChoice, Toast.LENGTH_LONG).show();
              //  openDialog();
            }
        });

    }
    private void SendRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = RestApiMethods.ApiGetUrl;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        // Log.i("Respuesta", "onResponse: " + response.substring(0,500) );


                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray EmpleArray = obj.getJSONArray("empleado");

                            for (int i = 0; i < EmpleArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject empleObject = EmpleArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                Persona emple = new Persona(Integer.parseInt(empleObject.getString("id")),
                                        empleObject.getString("nombre"),
                                        empleObject.getString("telefono"),
                                        empleObject.getString("latitud"),
                                        empleObject.getString("longitud"));

                                //adding the hero to herolist
                                empleList.add(emple);
                                arrayEmple.add(emple.getNombre() + ' ' + emple.getTelefono()+ ' ' + emple.getLatitud()
                                        + ' ' + emple.getLongitud());
                            }

                            ArrayAdapter adp = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrayEmple);
                            listview1.setAdapter(adp);


                        } catch (JSONException ex) {

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error en Response", "onResponse: " + error.getMessage().toString());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "String Response : "+ response.toString(),Toast.LENGTH_LONG).show();

        try {
            Log.d("JSON", String.valueOf(response));

            String Error = response.getString("httpStatus");
            if (Error.equals("")||Error.equals(null)){

            }else if(Error.equals("OK")){
                JSONObject body = response.getJSONObject("body");

            }else {

            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
//                        resultTextView.setText("String Response : "+ response.toString());
    }

    }



