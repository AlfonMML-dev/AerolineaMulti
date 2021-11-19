package org.izv.amml.multi.aerolinea_v;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResumenCompra extends AppCompatActivity {

    private Cliente cliente;
    private org.izv.amml.multi.aerolinea_v.PrecioBillete precioBillete;
    private Context contexto;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resumen_compra_activity);
        contexto = this;
        initializeComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v("estoy", "onCreateOptionsMenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initializeComponents() {
        Bundle bundle = getIntent().getExtras();
        precioBillete = bundle.getParcelable("billete");
        cliente = precioBillete.getCliente();
        TextView tv_NombreClienteDato = findViewById(R.id.tv_NombreClienteDato);
        Log.v("devuelveCliente", cliente.getNombre());
        tv_NombreClienteDato.setText(cliente.getNombre() + " " + cliente.getApellidos());

        TextView tv_Origen_Dato_Resumen = findViewById(R.id.tv_Origen_Dato_Resumen);
        Log.v("devuelveOrigen", precioBillete.getOrigen());
        tv_Origen_Dato_Resumen.setText(precioBillete.getOrigen());
        TextView tv_Destino_Dato_Resumen = findViewById(R.id.tv_Destino_Dato_Resumen);
        Log.v("devuelveDestino", precioBillete.getDestino());
        tv_Destino_Dato_Resumen.setText(precioBillete.getDestino());

        TextView tv_Extras = findViewById(R.id.tv_Extras);
        tv_Extras.setText(tv_Extras.getText().toString() + cliente.getNombre());

        TextView tv_Extras_Datos = findViewById(R.id.tv_Extras_Datos);
        tv_Extras_Datos.setText("" + rellenarExtrasDatos());

        TextView tv_PrecioExtras = findViewById(R.id.tv_Precio_Extras);
        tv_PrecioExtras.setText(tv_PrecioExtras.getText().toString() + " " + precioBillete.getPrecioExtrasTotal());

        TextView tv_PrecioTotal = findViewById(R.id.tv_Precio_Total);
        tv_PrecioTotal.setText(tv_PrecioTotal.getText().toString() + " " + precioBillete.getPrecioTotal());

        Button bt_CancelarCompra = findViewById(R.id.bt_Cancelar_Resumen);
        bt_CancelarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private String rellenarExtrasDatos() {
        String result = "";
        String[] extras = precioBillete.getExtrasSelecCadena().split(";");
        for (int i = 0; i < extras.length-1; i++) {
//            String[] aux = extras[i].split(",");
            result += extras[i].replace(",", ": ") + "\n";
        }
        result += extras[extras.length-1].replace(",", ": ");

        return result;
    }
}
