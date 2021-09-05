package com.example.semana4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PingActivity extends AppCompatActivity {

    private Button regresarbt;
    private TextView texto;
    private String direccion,textoFinal;
    private String[] pings = new String[5];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping);


        regresarbt = findViewById(R.id.regresar);
        texto = findViewById(R.id.texto);

        SharedPreferences preferences = getSharedPreferences("preferencias",MODE_PRIVATE);
        direccion = preferences.getString("direccion",null);

        Thread hilo = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for(int i=0;i<5;i++){
                                try{
                                    InetAddress inetAddress = InetAddress.getByName(direccion);
                                    boolean conectado = inetAddress.isReachable(500);
                                    if(conectado==true){
                                    pings[i]="Recibido";}
                                else {
                                        pings[i] = "Perdido";
                                    }}

                                catch (UnknownHostException e){
                                    e.printStackTrace();
                                } catch (IOException e) {

                                    e.printStackTrace();
                                }
                            }
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                        runOnUiThread(()->{
                            textoFinal = pings[0]+ '\n'  + pings[1] + '\n' + pings[2]
                                    + '\n'  + pings[3] + '\n' + pings[4] ;
                            texto.setText(textoFinal);


                    });

                    }
                }
        );
        hilo.start();






        regresarbt.setOnClickListener(
                (v)->{
                    finish();
                }
        );
    }


}