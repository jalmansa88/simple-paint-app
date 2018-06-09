package com.lordneru.pruebas.paint;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.AlertDialog;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton ibNegro, ibRojo, ibVerde, ibAmarillo, ibAzul;
    private Lienzo lienzo;
    Float pincelPeq, pincelMed, pincelGran;
    Float pincelDefecto;
    ImageButton trazo, nuevo, borrar, guardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ImageButtons de colores
        ibNegro = (ImageButton) findViewById(R.id.botonNegro);
        ibRojo = (ImageButton) findViewById(R.id.botonRojo);
        ibVerde = (ImageButton) findViewById(R.id.botonVerde);
        ibAmarillo = (ImageButton) findViewById(R.id.botonAmarillo);
        ibAzul = (ImageButton) findViewById(R.id.botonAzul);
        //ImageButtos de los botones de utilidades
        trazo = (ImageButton) findViewById(R.id.ibTrazo);
        nuevo = (ImageButton) findViewById(R.id.ibNuevo);
        borrar = (ImageButton) findViewById(R.id.ibBorrar);
        guardar = (ImageButton) findViewById(R.id.ibGuardar);

        lienzo = (Lienzo) findViewById(R.id.lienzo);

        /*OnClickListeners*/
        //SetOnClickListeners Colores
        ibNegro.setOnClickListener(this);
        ibRojo.setOnClickListener(this);
        ibVerde.setOnClickListener(this);
        ibAmarillo.setOnClickListener(this);
        ibAzul.setOnClickListener(this);

        trazo.setOnClickListener(this);
        nuevo.setOnClickListener(this);
        borrar.setOnClickListener(this);
        guardar.setOnClickListener(this);


        pincelPeq = Constantes.TAM_PINCEL_PEQUENIO;
        pincelMed = Constantes.TAM_PINCEL_MEDIANO;
        pincelGran = Constantes.TAM_PINCEL_GRANDE;

        pincelDefecto = pincelMed;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        String color = "";

        switch (view.getId()) {
            case R.id.botonNegro:
                color = view.getTag().toString();
                lienzo.setColor(color);
                break;

            case R.id.botonRojo:
                color = view.getTag().toString();
                lienzo.setColor(color);
                break;

            case R.id.botonVerde:
                color = view.getTag().toString();
                lienzo.setColor(color);
                break;

            case R.id.botonAmarillo:
                color = view.getTag().toString();
                lienzo.setColor(color);
                break;

            case R.id.botonAzul:
                color = view.getTag().toString();
                lienzo.setColor(color);
                break;

            case R.id.ibTrazo:
                final Dialog dialogoTrazo = new Dialog(this);
                dialogoTrazo.setTitle("Tamaño del punto: ");
                dialogoTrazo.setContentView(R.layout.tamanio_punto);
                //Tamaños de los botones
                TextView grosorPequenio = (TextView) dialogoTrazo.findViewById(R.id.bSelPequenio);
                TextView grosorMediano = (TextView) dialogoTrazo.findViewById(R.id.bSelMediano);
                TextView grosorGrueso = (TextView) dialogoTrazo.findViewById(R.id.bSelGrueso);

                setListener(grosorPequenio, dialogoTrazo, Constantes.TAM_PINCEL_PEQUENIO, false);
                setListener(grosorMediano, dialogoTrazo, Constantes.TAM_PINCEL_MEDIANO, false);
                setListener(grosorGrueso, dialogoTrazo, Constantes.TAM_PINCEL_GRANDE, false);
                dialogoTrazo.show();
                break;

            case R.id.ibNuevo:
                AlertDialog.Builder nuevoDibDialog = new AlertDialog.Builder(this);
                nuevoDibDialog.setTitle("Nuevo Dibujo");
                nuevoDibDialog.setMessage("¿Comenzar nuevo dibujo? (Perderas el dibujo no guardado");
                nuevoDibDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        lienzo.nuevoDibujo();
                        dialogInterface.dismiss();
                    }
                });

                nuevoDibDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                nuevoDibDialog.show();
                break;

            case R.id.ibBorrar:

                final Dialog dialogoBorrar = new Dialog(this);
                dialogoBorrar.setTitle("Tamaño de la Goma: ");
                dialogoBorrar.setContentView(R.layout.tamanio_punto);
                //Tamaños de los botones
                TextView grosorPequenioBorrar = (TextView) dialogoBorrar.findViewById(R.id.bSelPequenio);
                TextView grosorMedianoBorrar = (TextView) dialogoBorrar.findViewById(R.id.bSelMediano);
                TextView grosorGruesoBorrar = (TextView) dialogoBorrar.findViewById(R.id.bSelGrueso);

                setListener(grosorPequenioBorrar, dialogoBorrar, Constantes.TAM_PINCEL_PEQUENIO, true);
                setListener(grosorMedianoBorrar, dialogoBorrar, Constantes.TAM_PINCEL_MEDIANO, true);
                setListener(grosorGruesoBorrar, dialogoBorrar, Constantes.TAM_PINCEL_GRANDE, true);
                dialogoBorrar.show();
                break;

            case R.id.ibGuardar:
                AlertDialog.Builder dialogoGuardar = new AlertDialog.Builder(this);
                dialogoGuardar.setTitle("Guardar Dibujo");
                dialogoGuardar.setMessage("¿Desea guardar su dibujo?");
                dialogoGuardar.setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //salvar
                        lienzo.setDrawingCacheEnabled(true);
                        String imgGuardada = MediaStore.Images.Media.insertImage(
                                getContentResolver(), lienzo.getDrawingCache(),
                                UUID.randomUUID().toString()+".png", "drawing");

                        if(imgGuardada != null){
                            Toast savedToast = Toast.makeText(getApplicationContext(),
                                    "Dibujo guardado en la galeria", Toast.LENGTH_SHORT);
                            savedToast.show();
                        }else{
                            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                    "Error guardando el dibujo", Toast.LENGTH_SHORT);
                            unsavedToast.show();
                        }
                        lienzo.destroyDrawingCache();
                    }
                });

                dialogoGuardar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialogoGuardar.show();

                break;
        }
    }

    private void setListener(TextView boton, final Dialog dialog, final float grosor, final boolean borrado){
        boton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                lienzo.setBorrado(borrado);
                lienzo.setTamanioPunto(grosor);
                dialog.dismiss();
            }
        });
    }

}

