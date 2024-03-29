package com.cubic.appcubicados.Actividades;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cubic.appcubicados.Modelos.Arriendo;
import com.cubic.appcubicados.Modelos.Users;
import com.cubic.appcubicados.R;
import com.cubic.appcubicados.Retrofit.RetrofitBuilder;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil extends AppCompatActivity {

    private TextView nombrePerfil;
    private TextView apellidoP;
    private TextView apellidoM;
    private TextView email;
    private TextView fecha;
    private TextView celu;
    private TextView nombre_Servicio;
    private TextView precio_Servicio;
    private TextView diasPago;
    List<Arriendo> arriendoList = new ArrayList<>();

    Users users = new Users();

    /**
     * @param savedInstanceState
     * @Autor Pablo Rodriguez
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        nombrePerfil = findViewById(R.id.txtNomPerfil);
        apellidoP = findViewById(R.id.txtAP_Perfil);
        apellidoM = findViewById(R.id.txtAM_Perfil);
        email = findViewById(R.id.txtEmail_Perfil);
        fecha = findViewById(R.id.txtFecha_Perfil);
        celu = findViewById(R.id.txtCelu_Perfil);
        nombre_Servicio = findViewById(R.id.txtNombreServicio);
        precio_Servicio = findViewById(R.id.txtPrecio_Servicio);
        diasPago = findViewById(R.id.txtDiasPago);

        cargarUsuario();
    }

    /**
     * Metodo que llama al metodo IsFinish
     *
     * @param v
     */
    public void cerrarSesion(View v) {

        IsFinish("¿Deseas cerrar sesión?");

    }

    /**
     * Metodo que redirecciona a la edicion del perfil
     *
     * @param v
     */
    public void editarPerfil(View v) {
        String url = "http://www.google.com";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    /**
     * Metodo que crea una alerta de dialogo
     * switch.case.positivo = cierra la sesión
     * switch.case.negativo = cierra el cuadro de dialogo
     *
     * @param msjAlert
     */

    public void IsFinish(String msjAlert) {

        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {

            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    Intent i = new Intent(Perfil.this, MainActivity.class);
                    startActivity(i);
                    // This above line close correctly
                    //finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(Perfil.this);
        builder.setMessage(msjAlert)
                .setPositiveButton("Salir", dialogClickListener)
                .setNegativeButton("Quedarse", dialogClickListener).show();

    }

    /**
     * metodo que carga los dias restantes del servicio
     * segun la fecha donde se haya creado el arriendo
     *
     * @param usID
     */
    private void cargarDiasRestantes(int usID) {
        Call<Integer> integerCall = RetrofitBuilder.arriendoService.getDias(usID);
        integerCall.enqueue(new Callback<Integer>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    int diasRest = response.body();
                    if (diasRest == 31) {
                        Intent i = new Intent(Perfil.this, MainActivity.class);
                        startActivity(i);
                    }
                    System.out.println("Dias restantes" + response.body());
                    diasPago.setText(diasRest + " dias");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    /**
     * Cuando se presiona atras regresa al activity vista usuario
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent i = new Intent(Perfil.this, VistaUsuario.class);
        startActivity(i);
    }

    /**
     * Metodo para cargar usuarios
     * ocupando un shared preferences
     * que traera el id
     * para buscar el arriendo asociado al id de usuario
     */
    private void cargarUsuario() {
        //Datos Usuario
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("userP", MODE_PRIVATE);
        String userSave = prefs.getString("userperfil", null);
        Gson gson = new Gson();
        Users usr = gson.fromJson(userSave, Users.class);
        System.out.println("Nombre: " + users.getName());
        nombrePerfil.setText(usr.getName());
        email.setText(usr.getEmail());
        apellidoP.setText(usr.getApellidoP());
        apellidoM.setText(usr.getApellidoM());
        celu.setText(usr.getTelefono());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = usr.getFecha_nacimiento();
        fecha.setText(sdf.format(d));
        System.out.println("" + d);

        Call<List<Arriendo>> arriendoCall = RetrofitBuilder.arriendoService.getArriendo(usr.getId());
        arriendoCall.enqueue(new Callback<List<Arriendo>>() {
            @Override
            public void onResponse(Call<List<Arriendo>> call, Response<List<Arriendo>> response) {
                if (response.isSuccessful()) {
                    arriendoList = response.body();
                    Arriendo a = new Arriendo();
                    for (int i = 0; i < arriendoList.size(); i++) {
                        System.out.println("Nombre del servicio: " + arriendoList.get(i).getNombre());
                        nombre_Servicio.setText(arriendoList.get(i).getNombre());
                        precio_Servicio.setText("$ " + arriendoList.get(i).getPrecio() + " X MES");
                        a.setCreated_at(arriendoList.get(i).getCreated_at());
                        cargarDiasRestantes(usr.getId());
                    }
                    System.out.println(a.getCreated_at());
                }
            }

            @Override
            public void onFailure(Call<List<Arriendo>> call, Throwable t) {
                Log.d("Error: ", t.getMessage());
            }
        });

    }
}