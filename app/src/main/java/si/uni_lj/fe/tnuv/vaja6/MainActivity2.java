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

public class MainActivity2 extends AppCompatActivity{
    private String url;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        url = getResources().getString(R.string.urlNaslov1);
        lv =  findViewById(R.id.list);
        //lv.setOnItemClickListener(((adapterView, view, i, l) ->{
        //    Toast.makeText(this, "Izbral si item st. "+i, Toast.LENGTH_LONG).show();

        //} ));
        configureKinotiButton();
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
}