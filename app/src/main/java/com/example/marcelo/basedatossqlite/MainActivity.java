package com.example.marcelo.basedatossqlite;

import android.database.Cursor;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    DBManager dbManager;
    public ListView lsvAprendices;
    public Cursor cursorAprendices;
    public Cursor cursorAprendicesBusqueda;
    public SimpleCursorAdapter simpleAdapter;

    public EditText edtCrearNombre, edtCrearApellido, edtCrearDireccion, edtCrearEdad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);

  //      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
   //     fab.setOnClickListener(new View.OnClickListener() {
    //        @Override
    //        public void onClick(View view) {
     //           Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
      //                  .setAction("Action", null).show();
       //     }
     //   });

        inicializarUi();
        dbManager = new DBManager(this);
 //       dbManager.insertar("Marck","Antony","Calle-67-2745","31");
        llenarLista();
    }

    public void llenarLista(){
        cursorAprendices = dbManager.consultaAprendices();
        String[] from = new String[]{dbManager.cnNombre,dbManager.cnEdad};
        int[] to = new int[]{ android.R.id.text1,android.R.id.text2 };
        simpleAdapter = new SimpleCursorAdapter(this,android.R.layout.two_line_list_item, cursorAprendices,from,to,0);
        lsvAprendices.setAdapter(simpleAdapter);
    }

    public void llenarListaBusqueda(){
        String[] from = new String[]{dbManager.cnNombre,dbManager.cnEdad};
        int[] to = new int[]{ android.R.id.text1,android.R.id.text2 };
        simpleAdapter = new SimpleCursorAdapter(this,android.R.layout.two_line_list_item, cursorAprendicesBusqueda,from,to,0);
        lsvAprendices.setAdapter(simpleAdapter);
    }

    public void inicializarUi(){
       lsvAprendices = (ListView) findViewById(R.id.lsvAprendices);
   //     edtCrearNombre = (EditText) findViewById(R.id.edtCrearNombre);
   //     edtCrearApellido = (EditText) findViewById(R.id.edtCrearApellido);
    //    edtCrearDireccion = (EditText) findViewById(R.id.edtCrearDireccion);
    //    edtCrearEdad = (EditText) findViewById(R.id.edtCrearEdad);
    }

    public void insertar(View v){
        EditText nombre = (EditText)findViewById(R.id.editTextNombreInsert);
        String apellido = "";
        String direccion = "";
        EditText edad = (EditText)findViewById(R.id.editTextEdadInsert);
        if(nombre.getText().toString().isEmpty() || edad.getText().toString().isEmpty()){
            Toast.makeText(this,"Campos vacios", Toast.LENGTH_SHORT).show();
        } else {
            dbManager.insertar(nombre.getText().toString(),apellido,direccion,edad.getText().toString());
           llenarLista();
        }
    }

    public void borrarRegistros(View v){
        dbManager.borrarRegistros();
        llenarLista();
    }

    public void buscar(View v){
        EditText nombre = (EditText)findViewById(R.id.editTextNombreBuscar);
        cursorAprendicesBusqueda=  dbManager.buscar(nombre.getText().toString());
            llenarListaBusqueda();
    }

    public void eliminar(View v){
        EditText nombre = (EditText)findViewById(R.id.editTextNombreEliminar);

        if(nombre.getText().toString().isEmpty()){
            Toast.makeText(this,"Campo vacio", Toast.LENGTH_SHORT).show();
        } else {
            dbManager.eliminar(nombre.getText().toString());
            llenarLista();
        }

    }

    public void actualizar(View v){
        EditText nombre = (EditText)findViewById(R.id.editTextNombreActualizar);
        EditText edad = (EditText)findViewById(R.id.editTextEdadActualizar);
        if(nombre.getText().toString().isEmpty() || edad.getText().toString().isEmpty()){
            Toast.makeText(this,"Campos vacios", Toast.LENGTH_SHORT).show();
        } else {
            dbManager.actualizar(nombre.getText().toString(),edad.getText().toString());
            llenarLista();
        }


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
}
