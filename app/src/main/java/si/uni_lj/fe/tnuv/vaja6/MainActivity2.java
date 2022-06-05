package si.uni_lj.fe.tnuv.vaja6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity{
    private String url;
    ListView lv;
    TextView kinoDatum;
    int dolzina;

    Globals sharedData = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        url = getResources().getString(R.string.urlNaslov1);
        lv =  findViewById(R.id.list);
        //lv.setOnItemClickListener(((adapterView, view, i, l) ->{
        //    Toast.makeText(this, "Izbral si item st. "+i, Toast.LENGTH_LONG).show();

        //} ));
        sharedData.setValueIndeks2(0);

        configureKinotiButton();
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
    //neki drugi podatki za filme!!!!!!
    private void prikaziPodatke(String podatki) {
        ArrayList<HashMap<String, String>> seznamKontaktov = new ContactsJsonParser1().parseToArrayList(podatki);
        //Toast.makeText(this, podatki, Toast.LENGTH_LONG).show();
        SimpleAdapter adapter = new SimpleAdapter(this, seznamKontaktov, R.layout.list_item, new String[]{"naslov", "ureString"}, new int[]{R.id.id, R.id.naslov});


        lv.setAdapter(adapter);

        kinoDatum =  findViewById(R.id.datum);
        String m = sharedData.getValueDatum();
        kinoDatum.setText(m);
    }

    private void configureKinotiButton() {
        Button nextButtton = (Button) findViewById(R.id.kino);
        nextButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, MainActivity.class));
            }
        });
    }
    private void configureNaprejButton() {
        Button nextButtton = (Button) findViewById(R.id.naprej);
        nextButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = sharedData.getValueIndeks2();

                if(n < 31) {
                    n++;
                    sharedData.setValueIndeks2(n);
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
        Button nextButtton = (Button) findViewById(R.id.nazaj);
        nextButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = sharedData.getValueIndeks2();

                if(n > 0) {
                    n--;
                    sharedData.setValueIndeks2(n);
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
}