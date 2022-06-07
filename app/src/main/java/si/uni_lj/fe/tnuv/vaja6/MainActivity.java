package si.uni_lj.fe.tnuv.vaja6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity{

    private String url;
    private String imeKino = Global.imeKina;
    int indeks = Global.indeksiranje;
    ListView lv;
    TextView kinoIme;

    Globals sharedData = Globals.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = getResources().getString(R.string.urlNaslov);
        lv =  findViewById(R.id.list);

        //lv.setOnItemClickListener(((adapterView, view, i, l) ->{
        //    Toast.makeText(this, "Izbral si item st. "+i, Toast.LENGTH_LONG).show();

        //} ));
        //sharedData.setValueIndeks(0);

        configureFilmiButton();
        configureMapsButton();
        configureNaprejButton();
        configureNazajButton();
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

        kinoIme =  findViewById(R.id.imeKina);
        String m = sharedData.getValueIme();
        kinoIme.setText(m);
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
    private void configureNaprejButton() {
        ImageButton nextButtton = (ImageButton) findViewById(R.id.naprej);
        nextButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = sharedData.getValueIndeks();

                if(n < 4) {
                    n++;
                    sharedData.setValueIndeks(n);
                    onStart();
                } else{
                    Context context = getApplicationContext();
                    String text = "Ni več možno iti naprej.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }




            }
        });
    }
    private void configureNazajButton() {
        ImageButton nextButtton = (ImageButton) findViewById(R.id.nazaj);
        nextButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = sharedData.getValueIndeks();

                if(n > 0) {
                    n--;
                    sharedData.setValueIndeks(n);
                    onStart();
                } else{
                    Context context = getApplicationContext();
                    String text = "Ni več možno iti nazaj.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }



            }
        });
    }

    private void configureMapsButton() {
        Button mapsButton = (Button) findViewById(R.id.button2);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MapsActivity2.class));
/*                Context context = getApplicationContext();
                Global.indeksiranje = 10;
                indeks = Global.indeksiranje;
                String text = String.valueOf(indeks);
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();*/
            }
        });
    }
}