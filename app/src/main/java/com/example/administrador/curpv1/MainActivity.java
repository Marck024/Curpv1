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
            //datepicker

        //boton
            btn=(Button)findViewById(R.id.button);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String res=hacerCurp();

                    if(!res.isEmpty()){
                        ver.setText(res);
                    }
                }
            });
    }

    private String hacerCurp() {
        String curp="";
        String palabra="";

        //validando Apellido Paterno
        palabra=ape1.getText().toString().toUpperCase();
        if(palabra.isEmpty()) {
            ape1.setError("Ingrese Apellido Paterno");
            return "";
        }else{
            ape1.setError(null);
            if(palabra.indexOf("DE LA")>=0) {
                curp=palabra.substring(palabra.indexOf("DE LA")+5,palabra.length())+"X";
            }else if(palabra.indexOf("DE LOS")>=0){
                curp=palabra.substring(palabra.indexOf("DE LOS")+6,palabra.length())+"X";
            }else{
                curp=palabra.substring(0,1)+palabra.substring(1,2);
            }
        }
        //validando Apellido Materno
        palabra= ape2.getText().toString().toUpperCase();
        if(palabra.isEmpty()){
            ape2.setError("Ingrese Apellido Materno o Letra X, en caso de no tener");
            return "";
        }else {
            ape2.setError(null);
            if(palabra.indexOf("DE LA")>=0) {
                curp=curp+palabra.substring(palabra.indexOf("DE LA")+5,palabra.length());
            }else if(palabra.indexOf("DE LOS")>=0){
                curp=curp+palabra.substring(palabra.indexOf("DE LOS")+6,palabra.length());
            }else{
                curp=curp+palabra.substring(0,1);
            }
        }

        //Validando nombre
        palabra=nom.getText().toString().toUpperCase();
        if(palabra.isEmpty()){
            nom.setError("Ingrese Nombre(s)");
            return "";
        }else{
            nom.setError(null);
            if(palabra.indexOf("MARIA")>=0 || palabra.indexOf("MARÍA")>=0 ||
                palabra.indexOf("JOSE")>=0 || palabra.indexOf("JOSË")>=0)
            {
                if(palabra.indexOf(" ")>=0){
                    curp=curp+palabra.substring(palabra.indexOf(" ")+1,palabra.length()).substring(0,1);
                }else{
                    curp=curp+palabra.substring(0,1);
                }
            }else{
                curp=curp+palabra.substring(0,1);
            }
        }

        //validar palabras antisonantes
        for(int i=0;i<getResources().getStringArray(R.array.groserias).length;i++){
            if(curp.equals(getResources().getStringArray(R.array.groserias)[i].toString())){
                //curp=curp.replace(curp.substring(1,2),"X");
                curp=curp.replaceFirst(curp.substring(1,2),"X");
                break;
            }
        }
        //fecha
        palabra=fecha.getText().toString();
        if(palabra.isEmpty()){
            fecha.setError("Ingrese una fecha");
            return "";
        }else{
            int res=fechaValida(palabra);
            if(res!=0){
                fecha.setError("Ingrese una fecha Valida");
                return "";
            }else{
                fecha.setError(null);
                curp=curp+palabra.substring(6,8)+palabra.substring(3,5)+palabra.substring(0,2);
            }
        }

        //genero
        if(spGenero.getSelectedItem().toString().equals("Genero:")){
            Toast.makeText(this,"Selecciones un Genero",Toast.LENGTH_SHORT).show();
            return "";
        }else{
            if(spGenero.getSelectedItem().toString().equals("Hombre")){
                curp=curp+"H";
            }else{
                curp=curp+"M";
            }
        }

        //validar estado
        if(spEstados.getSelectedItem().toString().equals("Nacimiento:")){
            Toast.makeText(this,"Selecciones un Estado",Toast.LENGTH_SHORT).show();
            return "";
        }else{
            curp=curp+getResources().getStringArray(R.array.estados)[spEstados.getSelectedItemPosition()];
        }

        //consonantes Apellido Paterno
        palabra=consonantes(ape1.getText().toString().toUpperCase());
        if(palabra.isEmpty()){
            Toast.makeText(this,"Ocurrio un detalle en apellido paterno",Toast.LENGTH_SHORT).show();
        }else{
            curp=curp+palabra;
        }

        //consonante Apellido Materno
        palabra=ape2.getText().toString().toUpperCase();
        if(palabra.equals("X")){
            curp=curp+"X";
        }else{
            palabra=consonantes(palabra);
            if(palabra.isEmpty()){
                Toast.makeText(this,"Ocurrio un detalle en Apellido Materno",Toast.LENGTH_SHORT).show();
            }else{
                curp=curp+palabra;
            }
        }

        //consonante Nombre
        palabra=nom.getText().toString().toUpperCase();
        if(palabra.indexOf("MARIA")>=0 || palabra.indexOf("MARÍA")>=0 ||
                palabra.indexOf("JOSE")>=0 || palabra.indexOf("JOSË")>=0)
        {
            if(palabra.indexOf(" ")>=0){
                palabra=consonantes(palabra.substring(palabra.indexOf(" ")+1,palabra.length()));
                if(palabra.isEmpty()){
                    Toast.makeText(this,"Ocurrio un detalle con el nombre (1)",Toast.LENGTH_SHORT).show();
                    return "";
                }else{
                    curp=curp+palabra;
                }
            }else{
                palabra=consonantes(palabra);
                if(palabra.isEmpty()){
                    Toast.makeText(this,"Ocurrio un detalle con el nombre (2)",Toast.LENGTH_SHORT).show();
                    return "";
                }else{
                    curp=curp+palabra;
                }
            }
        }else{
            if(palabra.equals("X")){
                curp=curp+"X";
            }else{
                palabra=consonantes(palabra);
                if(palabra.isEmpty()){
                    Toast.makeText(this,"Ocurrio un problema con el nombre (3)",Toast.LENGTH_SHORT).show();
                    return "";
                }else{
                    curp=curp+palabra;
                }
            }
        }

        //magia
        curp=curp+"0";
        palabra=magia(curp);
        if(palabra.isEmpty()){
            Toast.makeText(this,"Ocurrio un detalle al calcular la Homoclave",Toast.LENGTH_SHORT).show();
            return "";
        }else{
            curp=curp+palabra;
        }

        return curp;
    }

    private String magia(String curp) {
        int a,b,i=0,j=18,sum=0,resultado=0;
        String letra;
        String[] orden={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","Ñ","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        for(b=1;b<=curp.length();b++){
            a=b-1;
            letra=curp.substring(a,b);
            for(i=0;i<orden.length;i++){
               if(letra.equals(orden[i])){
                   //Toast.makeText(this,String.valueOf(i)+"*"+String.valueOf(j),Toast.LENGTH_SHORT).show();
                   sum=sum+(i*j);
                   j--;
                   break;
               }
            }

        }

        if(sum>0){
            resultado=(sum%10)-10;
            if(resultado<=-10){
                resultado=0;
            }else{
                resultado=resultado*-1;
            }
            return String.valueOf(resultado);
        }
        return "";
    }

    private String consonantes(String s) {
        int a,b;
        String regresa="";
        for(b=2;b<=s.length();b++){
            a=b-1;
            if( !s.substring(a,b).equals("A") &&
                !s.substring(a,b).equals("E") &&
                !s.substring(a,b).equals("I") &&
                !s.substring(a,b).equals("O") &&
                !s.substring(a,b).equals("U")
                ) {
                regresa=s.substring(a,b);
                    if(regresa.equals("Ñ")){
                        regresa="X";
                    }
                return regresa;
                }
            }

            return regresa;
        }

    private int fechaValida(String s) {
        int dd=0,mm=0, yy=0, res=0;

        return res;
    }



}
