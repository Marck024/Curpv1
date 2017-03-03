package com.example.administrador.curpv1;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private EditText ape1,ape2,nom,fecha;
    private Spinner spEstados,spGenero;
    private TextView ver;
    //fecha
    private DatePickerDialog dpfecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //edittext
        ape1=(EditText)findViewById(R.id.apeP);
        ape2=(EditText)findViewById(R.id.apeM);
        nom=(EditText)findViewById(R.id.nom);
        ver=(TextView)findViewById(R.id.textView);

        //spinner
            //Genero
            spGenero=(Spinner) findViewById(R.id.spGenero);
            ArrayAdapter<CharSequence> ad=ArrayAdapter.createFromResource(this,R.array.Genero,android.R.layout.simple_spinner_dropdown_item);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spGenero.setAdapter(ad);
            //Estado
            spEstados=(Spinner) findViewById(R.id.spEstado);
            ArrayAdapter<CharSequence> ad2=ArrayAdapter.createFromResource(this,R.array.Estados,android.R.layout.simple_spinner_dropdown_item);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spEstados.setAdapter(ad2);
        //fecha
            fecha=(EditText)findViewById(R.id.fecha);
            /*fecha.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });*/

        //boton
            btn=(Button)findViewById(R.id.button);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hacerCurp();
                }
            });
    }

    private void hacerCurp() {
        String curp="";
        String letra="";
        if( !ape1.equals("") && !nom.equals("")){
            if(!ape2.equals("")){
                //obtener primer letra del primer apellido y obtener vocal interna

                //obtener primer letra segundo apellido (si no X)

                //obtener primer letra del primer nombre (en caso de mario jose se contar el segundo nombre).

                //validar fecha
                //formatear fecha

                //validar selecciono un genero
                //agregar primer letra de sexo

                //validar selecciono un estado
                //comparar estad y buscar identificador
                //agregar codigo de identificacion del estado

                //consonantes Internas del primer apellido, segundo apellido y nombre

                //magia (calcular homoclave)

            }else{
                Toast.makeText(this,"Poner apellido la letra X si no tiene segundo apellido.",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Ingrese Nombre y Apellido",Toast.LENGTH_SHORT).show();
        }
    }
}
