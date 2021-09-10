package com.example.semana4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    private String[] numIp = new String[4];
    private EditText[] numTxt = new EditText[4];
    private Button ping,hosts;
    private TextView iptext;
    private String ip = "192.168.0.12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numTxt[0]= findViewById(R.id.num0);
        numTxt[1]= findViewById(R.id.num1);
        numTxt[2]= findViewById(R.id.num2);
        numTxt[3]= findViewById(R.id.num3);
        ping = findViewById(R.id.pingBtn);
        hosts = findViewById(R.id.buscarHBtn);
        iptext = findViewById(R.id.ipText);

        SharedPreferences preferences = getSharedPreferences("preferencias",MODE_PRIVATE);

        Thread hilo =new Thread(()->{
            try{
                //192.168.1.1 o 0
                //getHostAdress()
                InetAddress inetAddress = InetAddress.getLocalHost();
                ip = inetAddress.getHostAddress();
                Log.e("direccion",ip);
        } catch (UnknownHostException e) {
                e.printStackTrace();}
            runOnUiThread(()->{
              //  ip.replace("localhost/"," ");
                iptext.setText(ip);
            });
            });
        hilo.start();

        ping.setOnClickListener(
                (v)->{
        for(int i=0;i<numTxt.length;i++){
                numIp[i] = numTxt[i].getText().toString(); }

            String ipAdress = numIp[0] + "." + numIp[1] + "." + numIp[2] + "." + numIp[3];

                    preferences.edit().putString("direccion",ipAdress).apply();

                    Log.e("prueba",ipAdress);
            Intent i  = new Intent(this,PingActivity.class);
            if(!ipAdress.equals("...")){
            startActivity(i);}
            else{
                Toast.makeText(this, "La dirección no puede quedar vacia", Toast.LENGTH_SHORT).show();
            }
                }
        );

        hosts.setOnClickListener(
                (v)->{
                    for(int i=0;i<numTxt.length;i++){
                        numIp[i] = numTxt[i].getText().toString(); }

                    String ipAdress = numIp[0] + "." + numIp[1] + "." + numIp[2] + "." + numIp[3];
                    preferences.edit().putString("direccion",ipAdress).apply();
                    Log.e("prueba",ipAdress);
                    Intent iH  = new Intent(this,HostsActivity.class);
                    startActivity(iH);
                }
        );
    }
}