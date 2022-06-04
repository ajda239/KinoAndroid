package si.uni_lj.fe.tnuv.vaja6;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactsJsonParser1 {


    private static final String TAG = ContactsJsonParser1.class.getSimpleName();
    private ArrayList<HashMap<String, String>> contactList = new ArrayList<>();

    public ArrayList<HashMap<String, String>> parseToArrayList(String jsonStr){
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            // Getting JSON Array node
            JSONArray contacts = jsonObj.getJSONArray("dnevi");
            JSONObject dan = contacts.getJSONObject(0);
            String datum = dan.getString("datum");
            JSONArray filmi = dan.getJSONArray("filmi");
            // looping through All Contacts
            for (int i = 0; i < filmi.length(); i++) {
                HashMap<String, String> contact = new HashMap<>();

                JSONObject c = filmi.getJSONObject(i);

                String id = c.getString("naslov");
                String naslov = c.getString("ureString");



                    //naslov += "\n";
                    //naslov += ura;
                    //if (st < filmi.length()) {
                    //    naslov += "\n\n";
                    //} else {
                    //    naslov += "\n";
                    //}

                    //ura = "  ";



                contact.put("naslov", id);

                // adding each child node to HashMap key => value
                contact.put("ureString", naslov);


                // adding contact to contact list
                contactList.add(contact);
            }
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
        return contactList;
    }
}
