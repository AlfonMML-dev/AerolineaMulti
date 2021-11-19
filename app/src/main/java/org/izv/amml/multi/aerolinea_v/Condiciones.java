package org.izv.amml.multi.aerolinea_v;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Condiciones extends AppCompatActivity {

    private Context contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.condiciones_activity);
        contexto = this;
        initializeDesign();
    }

    private void initializeDesign() {
        Bundle bundle = getIntent().getExtras();
        String texto = bundle.getString("condiciones");
        TextView tv_Condiciones = findViewById(R.id.tV_Condiciones);
        tv_Condiciones.setText(texto);

        Button bt_Cancelar = findViewById(R.id.bt_Cancelar);
        bt_Cancelar.setOnClickListener(view -> finish());

        CheckBox chBox_AceptarTerminos = findViewById(R.id.chBox_AceptarTerminos);
        chBox_AceptarTerminos.setOnClickListener(view -> finish());

    }
}
