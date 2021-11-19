package org.izv.amml.multi.aerolinea_v;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Condición para realizar una acción según el valor de la variable
    //Componente: Switch switch_Seguro
    boolean estadoSwicth_Seguro;

    //Condición para realizar una acción según el valor de la variable
    //Componente: Switch switch_Acceso
    boolean estadoSwicth_Acceso;

    /**
     * Si el usuario se ha dirigido a la interfaz condiciones, suponemos que el usuario ha leido
     * las condiciones de la empresa. Por tanto la variable pasará a ser true
     */
    boolean condicionesEmpresa;

    /**
     * Esta estructura almacena todos los componentes del tipo CheckBox y Switch, excepto
     * el Checkbox que identifica al usuario como Sr, Sra, Srto o Srta
     */
    private ArrayList<String> extrasSeleccionados = new ArrayList<>();
    private static String nombresExtras;

    //Declaración de los componentes de la aplicación que desarrollan acciones
    private Button bt_Comprar;
    private CheckBox[] chBoxes_Sr_Sra = new CheckBox[4];
    private CheckBox[] checkBoxes_Resto = new CheckBox[6];
    private EditText[] editTexts = new EditText[7];
    //    private RadioButton rBt_Si;
//    private RadioButton rBt_No;
    private Switch switch_Seguro;
    private Switch switch_Acceso;
    private TextView tv_LeerCondiciones;

    //Contexto de la aplicación
    private Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("estoy", "onCreate");
        contexto = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.tBar_MenuSettings);
        if (toolbar == null) {
            Log.v("notfound","Can't find tool bar, did you forget to add it in Activity layout file?");
        } else{
            Log.v("found","Successfully found");
        }
        setSupportActionBar(toolbar);
//        toolbar.showOverflowMenu();
        initializeDesign();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v("estoy", "onCreateOptionsMenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /*Este método guarda los valores de los campos y aquellas casillas y componentes
    que han sido marcados
    */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("estoy", "onSaveInstanceState");
    }

    //Obtención los valores de de los campos y aquellas casillas y componentes que han sido marcados
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v("estoy", "onRestoreInstanceState");
    }

    private void setNombresExtras(){
        String[] aux;
        nombresExtras = "";
        for (int i = 0; i < checkBoxes_Resto.length; i++) {
            aux = getResources().getResourceEntryName(checkBoxes_Resto[i].getId()).split("_");
            nombresExtras += aux[1] + ";";
        }
        aux = getResources().getResourceEntryName(switch_Seguro.getId()).split("_");
        nombresExtras += aux[1] + ";" ;

        aux = getResources().getResourceEntryName(switch_Acceso.getId()).split("_");
        nombresExtras += aux[1];
    }

    public static String getNombresExtras(){
        return nombresExtras.toLowerCase();
    }

    //Inicialización de los componentes de la aplicación que desarrollan acciones
    private void initializeDesign() {

        bt_Comprar = findViewById(R.id.bt_Comprar);
        bt_Comprar.setOnClickListener(view -> {
            if(condicionesEmpresa){
                String nombre = editTexts[2].getText().toString();
                String apellidos = editTexts[3].getText().toString();
                String direccion = editTexts[4].getText().toString();
                String telefono = editTexts[5].getText().toString();
                String email = editTexts[6].getText().toString();
                Cliente cliente = new Cliente(nombre, apellidos, direccion, telefono, email);

                String origen = editTexts[0].getText().toString();
                String destino = editTexts[1].getText().toString();
                rellenarExtrasSeleccionados();
                PrecioBillete precioBillete = new PrecioBillete(origen, destino, extrasSeleccionados, cliente);

                Intent intent = new Intent(contexto, ResumenCompra.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("billete", precioBillete);
                intent.putExtras(bundle);
                startActivity(intent);
            } else{
//                DialogFragment df = new DialogFragment();
//                df.show(getSupportFragmentManager(), "Es necesario leer las condiciones de la empresa");
            }
        });

        chBoxes_Sr_Sra[0] = findViewById(R.id.chBox_Sr);
        chBoxes_Sr_Sra[1] = findViewById(R.id.chBox_Sra);
        chBoxes_Sr_Sra[2] = findViewById(R.id.chBox_Srto);
        chBoxes_Sr_Sra[3] = findViewById(R.id.chBox_Srta);
        for (int i = 0; i < chBoxes_Sr_Sra.length; i++) {
            int aux = i;
            chBoxes_Sr_Sra[i].setOnClickListener(view -> checkBoxSelect(aux));
        }

        checkBoxes_Resto[0] = findViewById(R.id.chBox_PrimeraClase);
        checkBoxes_Resto[1] = findViewById(R.id.chBox_AsientoVentanilla);
        checkBoxes_Resto[2] = findViewById(R.id.chBox_Mascota);
        checkBoxes_Resto[3] = findViewById(R.id.chBox_Desayuno);
        checkBoxes_Resto[4] = findViewById(R.id.chBox_Almuerzo);
        checkBoxes_Resto[5] = findViewById(R.id.chBox_Cena);

        editTexts[0] = findViewById(R.id.eT_Origen);
        editTexts[1] = findViewById(R.id.eT_Destino);
        editTexts[2] = findViewById(R.id.eT_Nombre);
        editTexts[3] = findViewById(R.id.eT_Apellidos);
        editTexts[4] = findViewById(R.id.eT_Direccion);
        editTexts[5] = findViewById(R.id.eT_Telefono);
        editTexts[6] = findViewById(R.id.eT_Correo);

//        rBt_Si = findViewById(R.id.rBt_Si);
//        rBt_No = findViewById(R.id.rBt_No);

        switch_Seguro = findViewById(R.id.switch_SeguroAdicional);
        switch_Seguro.setOnClickListener(view -> {
            if(!estadoSwicth_Seguro){
                goToCondiciones(getString(R.string.tv_CondicionesSeguro));
                estadoSwicth_Seguro = true;
            } else{
                estadoSwicth_Seguro = false;
            }
        });
        switch_Acceso = findViewById(R.id.switch_AccesoPreferente);
        switch_Acceso.setOnClickListener(view -> {
            if(!estadoSwicth_Acceso){
                estadoSwicth_Acceso = true;
            } else{
                estadoSwicth_Acceso = false;
            }
        });
        tv_LeerCondiciones = findViewById(R.id.tv_LeerCondiciones);
        tv_LeerCondiciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCondiciones(getString(R.string.tv_CondicionesEmpresa));
                condicionesEmpresa = true;
            }
        });
        setNombresExtras();
    }

    //Al marcar una casilla, cualquier otra casilla que haya sido marcada deja de estarlo.
    public void checkBoxSelect(int index){
        for (int i = 0; i < chBoxes_Sr_Sra.length; i++) {
            if(i != index){
                chBoxes_Sr_Sra[i].setChecked(false);
            }
        }
    }

    //Crea un intent que lleva al usuario a una nueva interfaz
    private void goToCondiciones(String texto){
        Intent intent = new Intent(contexto, Condiciones.class);
        Bundle bundle = new Bundle();
        bundle.putString("condiciones", texto);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //Inserto valores con sus respectivas claves en una estructura del tipo HashMap
    private void rellenarExtrasSeleccionados(){
        extrasSeleccionados.removeAll(extrasSeleccionados);
        String[] aux;
        String nombre;
        for (int i = 0; i < checkBoxes_Resto.length; i++) {
            if(checkBoxes_Resto[i].isChecked()){
                aux = getResources().getResourceEntryName(checkBoxes_Resto[i].getId()).split("_");
                nombre = aux[1].toLowerCase();
                extrasSeleccionados.add(nombre);
            }
        }
        if(switch_Seguro.isChecked()){
            aux = getResources().getResourceEntryName(switch_Seguro.getId()).split("_");
            nombre = aux[1].toLowerCase();
            extrasSeleccionados.add(nombre);
        }

        if(switch_Acceso.isChecked()){
            aux = getResources().getResourceEntryName(switch_Acceso.getId()).split("_");
            nombre = aux[1].toLowerCase();
            extrasSeleccionados.add(nombre);
        }

    }
}