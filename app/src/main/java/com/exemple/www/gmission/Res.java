package com.exemple.www.gmission;

import models.Employe;

public class Res {

    //public static final String URL ="http://192.168.203.2:8084/g_mission_employe/webresources";
    //public static final String URL ="http://192.168.1.8:8084/g_mission_employe/webresources";
    //public static final String URL ="http://192.168.1.11/gmission";
    public static final String URL ="http://172.20.10.2/gmission";
    public static int id=1;
    public  static Employe employe=null;
    public static void setClient(Employe employe)
    {
        Res.employe=employe;
    }
    public static Employe getEmploye()
    {
        return  employe;
    }
}
