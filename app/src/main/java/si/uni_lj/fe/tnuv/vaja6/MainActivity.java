package si.uni_lj.fe.tnuv.vaja6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity{

    private String url;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = getResources().getString(R.string.urlNaslov);
        lv =  findViewById(R.id.list);
        //lv.setOnItemClickListener(((adapterView, view, i, l) ->{
        //    Toast.makeText(this, "Izbral si item st. "+i, Toast.LENGTH_LONG).show();

        //} ));
        configureFilmiButton();
        configureMapsButton();
    }

    @Override
    protected void onStart() {
        super.onStart();

        PrenosPodatkov pp = new PrenosPodatkov(url, this);
        new Thread() {
            @Override
            public void run() {
                String rezultat = pp.prenesiPodatke();
                runOnUiThread(()-> prikaziPodatke(rezultat));
            }
        }.start();


    }
    private void prikaziPodatke(String podatki) {
        ArrayList<HashMap<String, String>> seznamKontaktov = new ContactsJsonParser().parseToArrayList(podatki);
        //Toast.makeText(this, podatki, Toast.LENGTH_LONG).show();
        SimpleAdapter adapter = new SimpleAdapter(this, seznamKontaktov, R.layout.list_item, new String[]{"datum", "naslov"}, new int[]{R.id.id, R.id.naslov});


        lv.setAdapter(adapter);
    }

    private void configureFilmiButton() {
        Button nextButtton = (Button) findViewById(R.id.film);
        nextButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });
    }

    private void configureMapsButton() {
        Button nextButton = (Button) findViewById(R.id.button2);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Maps.class));
            }
        });
    }
}