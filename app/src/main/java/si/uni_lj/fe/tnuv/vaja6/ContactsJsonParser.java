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

    public ArrayList<HashMap<String, String>> parseToArrayList(String jsonStr){
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            // Getting JSON Array node
            JSONArray contacts = jsonObj.getJSONArray("dnevi");

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                HashMap<String, String> contact = new HashMap<>();

                JSONObject c = contacts.getJSONObject(i);

                String id = c.getString("datum");
                String naslov = "";
                String ura= "  ";

                JSONArray filmi = c.getJSONArray("filmi");
                for( int j = 0; j < filmi.length(); j++) {
                    JSONObject f = filmi.getJSONObject(j);

                    naslov += f.getString("naslov");

                    JSONArray ure = f.getJSONArray("ure");
                    int stevec = 0;
                    for(int k = 0; k < ure.length(); k++) {
                        JSONObject u = ure.getJSONObject(k);
                        stevec ++;
                        ura += u.getString("ura");
                        if(stevec < ure.length()) {
                            ura += "  |  ";
                        }
                    }
                    naslov += "\n";
                    naslov += ura;
                    naslov += "\n\n";
                    ura = "  ";

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
