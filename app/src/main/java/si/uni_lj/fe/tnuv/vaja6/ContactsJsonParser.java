package si.uni_lj.fe.tnuv.vaja6;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ContactsJsonParser {


    private static final String TAG = ContactsJsonParser.class.getSimpleName();
    private ArrayList<HashMap<String, String>> contactList = new ArrayList<>();
    private int indeks = Global.indeksiranje;
    Globals sharedData = Globals.getInstance();
    public ArrayList<HashMap<String, String>> parseToArrayList(String jsonStr){
        try {

            JSONObject jsonObj = new JSONObject(jsonStr);

            // Getting JSON Array node
            JSONArray contacts = jsonObj.getJSONArray("kino");

            int n = sharedData.getValueIndeks();
            JSONObject kino = contacts.getJSONObject(n);

            String kinoIme = kino.getString("imeKina");
            sharedData.setValueIme(kinoIme);
            //System.out.println(Global.imeKina);
            JSONArray dnevi = kino.getJSONArray("dnevi");
            // looping through All Contacts
            for (int i = 0; i < dnevi.length(); i++) {
                HashMap<String, String> contact = new HashMap<>();

                JSONObject c = dnevi.getJSONObject(i);

                String id = c.getString("datum");
                String naslov = "";
                String ura= "";

                JSONArray filmi = c.getJSONArray("filmi");
                int st = 0;
                for( int j = 0; j < filmi.length(); j++) {
                    JSONObject f = filmi.getJSONObject(j);
                    st++;
                    naslov += f.getString("naslov");

                    JSONArray ure = f.getJSONArray("ure");
                    int stevec = 0;
                    for(int k = 0; k < ure.length(); k++) {
                        JSONObject u = ure.getJSONObject(k);
                        stevec ++;
                        ura += u.getString("ura");
                        if(stevec < ure.length()) {
                            ura += " | ";
                        }
                    }
                    naslov += "\n";
                    naslov += ura;
                    if (st < filmi.length()) {
                        naslov += "\n\n";
                    } else {
                        naslov += "";
                    }

                    ura = " ";

                }

                contact.put("naslov", naslov);

                // adding each child node to HashMap key => value
                contact.put("datum", id);


                // adding contact to contact list
                contactList.add(contact);
            }
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
        return contactList;
    }
}
