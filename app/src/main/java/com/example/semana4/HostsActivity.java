package com.example.semana4;

import android.content.SharedPreferences;
import android.net.InetAddresses;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

public class HostsActivity extends AppCompatActivity {

    private Button regresarbt;
    private TextView texto;
    private String[] direcciones;
    String ips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        regresarbt = findViewById(R.id.regresar);
        texto = findViewById(R.id.texto);

        SharedPreferences preferences = getSharedPreferences("preferencias",MODE_PRIVATE);

        Thread hilo = new Thread(()->{

            try {
                InetAddress[] AdressesIp = InetAddress.getAllByName(preferences.getString("direccion", null));
                direcciones = new String[AdressesIp.length];
                for (int i = 0; i< direcciones.length;i++){
                    direcciones[i] = "" + AdressesIp[i];
                    ips = ips + direcciones[i] + '\n';
                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

          runOnUiThread(()->{
              texto.setText(ips);

          });
        });
        hilo.start();
        regresarbt.setOnClickListener(
                (v)->{
                    finish();
                }
        );
    }
    public static String formatIpAdress (int ip){
        return String.format(Locale.US,"%d.%d.%d.%d",(ip & 0xff),(ip >> 8 & 0xff),(ip >> 16 & 0xff),(ip >> 24 & 0xff));
    }
}