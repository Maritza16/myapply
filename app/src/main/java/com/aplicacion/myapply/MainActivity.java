package com.aplicacion.myapply;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    static final int MY_PERMISSIONS_REQUEST_CAMARA=1;
    private Uri photoURI;
    Button btSalvados, btnFotos, btnSalvar;
    Bitmap imgBitmap;
    EditText nombre, telefono, latitud,longitud;
    String nombre1, telefono1,latitud1,longitud1, img_str, url, imag;
    ImageView imageView;
    Image ima;
    private LocationManager locManager;
    private Location loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView);
        btSalvados=findViewById(R.id.btnSalvados);
        btSalvados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), TablaActivity2.class);
                startActivity(intent);
            }
        });
        btnFotos=findViewById(R.id.btnFotos);
        btnFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                abrirCamara();

            }
        });

        btnSalvar=findViewById(R.id.btSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearEmpleado();
            }
        });
    }

    private void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(intent, 1);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imgBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imgBitmap);
        }

    }

    private void CrearEmpleado()
    {
        nombre=findViewById(R.id.editNombre);
        telefono=findViewById(R.id.ediTelefono);
        latitud=findViewById(R.id.editLatitud);
        longitud=findViewById(R.id.ediLongitud);


        nombre1=nombre.getText().toString();
        telefono1=telefono.getText().toString();
        latitud1=latitud.getText().toString();
        longitud1=longitud.getText().toString();
        imag=img_str;
        url = RestApiMethods.ApiPostUrl;

        String image = GetStringImage(imgBitmap);
        HashMap<String, String> parametros = new HashMap<String, String>();
    /*    parametros.put("nombre","Ernesto");
        parametros.put("apellidos","Vallecillo");
        parametros.put("edad","33");
*/

        parametros.put("nombre",nombre1);
        parametros.put("telefono", telefono1);
        parametros.put("latitud",latitud1);
        parametros.put("longitud",longitud1);
        parametros.put("imagen",image);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(parametros),
                new Response.Listener<JSONObject>() {
                    @Override
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
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
                 Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    private String GetStringImage(Bitmap imagen)
    {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        imagen.compress(Bitmap.CompressFormat.JPEG,100, ba);
        byte[] imagebyte = ba.toByteArray();
        String encode = Base64.encodeToString(imagebyte, Base64.DEFAULT);
        return encode;
    }


}