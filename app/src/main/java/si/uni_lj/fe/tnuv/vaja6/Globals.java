package si.uni_lj.fe.tnuv.vaja6;

import android.app.Application;


public class Globals {


    private static Globals instance = new Globals();

    // Getter-Setters
    public static Globals getInstance() {
        return instance;
    }

    public static void setInstance(Globals instance) {
        Globals.instance = instance;
    }

    private int notification_index;

    private String notification_name;
    private int dolzina1;


    private int notification_index2;

    private String notification_date;


    private int dolzina2;



    private Globals() {

    }


    public int getValueIndeks() {
        return notification_index;
    }


    public void setValueIndeks(int notification_index) {
        this.notification_index = notification_index;
    }


    public String getValueIme() {
        return notification_name;
    }


    public void setValueIme(String notification_name) {
        this.notification_name = notification_name;
    }

    public int getValueDolzonaKin() {
        return dolzina1;
    }


    public void setValueDolzinaKin(int dolzina1) {
        this.dolzina1 = dolzina1;
    }

    public String getValueDatum() {
        return notification_date;
    }


    public void setValueDatum(String notification_date) {
        this.notification_date = notification_date;
    }
    public int getValueIndeks2() {
        return notification_index2;
    }


    public void setValueIndeks2(int notification_index2) {
        this.notification_index2 = notification_index2;
    }
    public int getValueDolzonaDatumov() {
        return dolzina2;
    }


    public void setValueDolzinaDatumov(int dolzina2) {
        this.dolzina2 = dolzina2;
    }









}
