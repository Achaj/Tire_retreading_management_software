package org.main.Utils;

import org.main.Entity.TireIndex;
import org.main.Entity.Workers;
import org.main.Entity.WorkingTime;

import java.util.ArrayList;
import java.util.List;

public class Temporary {

    private static Workers workers;

    public static Workers getWorkers() {
        return workers;
    }

    public static void setWorkers(Workers workers) {
        Temporary.workers = workers;
    }

    private static List<TireIndex> speedIndexTire = new ArrayList<>();

    public static WorkingTime workingTime;

    public static List<TireIndex> getSpeedIndexTire() {
        speedIndexTire.add(new TireIndex("Y", "300"));
        speedIndexTire.add(new TireIndex("W", "270"));
        speedIndexTire.add(new TireIndex("W", "240"));
        speedIndexTire.add(new TireIndex("H", "210"));
        speedIndexTire.add(new TireIndex("W", "200"));
        speedIndexTire.add(new TireIndex("T", "190"));
        speedIndexTire.add(new TireIndex("S", "180"));
        speedIndexTire.add(new TireIndex("R", "170"));
        speedIndexTire.add(new TireIndex("Q", "160"));
        speedIndexTire.add(new TireIndex("P", "150"));
        speedIndexTire.add(new TireIndex("N", "140"));
        speedIndexTire.add(new TireIndex("M", "130"));
        speedIndexTire.add(new TireIndex("Ł", "120"));
        speedIndexTire.add(new TireIndex("k", "110"));
        speedIndexTire.add(new TireIndex("J", "100"));
        speedIndexTire.add(new TireIndex("G", "90"));
        speedIndexTire.add(new TireIndex("F", "80"));
        speedIndexTire.add(new TireIndex("E", "70"));
        speedIndexTire.add(new TireIndex("D", "65"));
        speedIndexTire.add(new TireIndex("C", "60"));
        speedIndexTire.add(new TireIndex("B", "50"));
        speedIndexTire.add(new TireIndex("A8", "40"));
        speedIndexTire.add(new TireIndex("A7", "35"));
        speedIndexTire.add(new TireIndex("A6", "30"));
        speedIndexTire.add(new TireIndex("A5", "25"));
        speedIndexTire.add(new TireIndex("A4", "20"));
        speedIndexTire.add(new TireIndex("A3", "15"));
        return speedIndexTire;
    }

    static private List<TireIndex> loadIndexTiere = new ArrayList<>();

    public static List<TireIndex> getLoadIndexTiere() {
        loadIndexTiere.add(new TireIndex("45", "165"));
        loadIndexTiere.add(new TireIndex("46", "170"));
        loadIndexTiere.add(new TireIndex("47", "175"));
        loadIndexTiere.add(new TireIndex("48", "180"));
        loadIndexTiere.add(new TireIndex("49", "185"));
        loadIndexTiere.add(new TireIndex("50", "190"));
        loadIndexTiere.add(new TireIndex("51", "195"));
        loadIndexTiere.add(new TireIndex("52", "200"));
        loadIndexTiere.add(new TireIndex("53", "206"));
        loadIndexTiere.add(new TireIndex("54", "212"));
        loadIndexTiere.add(new TireIndex("55", "218"));
        loadIndexTiere.add(new TireIndex("56", "224"));
        loadIndexTiere.add(new TireIndex("57", "230"));
        loadIndexTiere.add(new TireIndex("58", "236"));
        loadIndexTiere.add(new TireIndex("59", "234"));
        loadIndexTiere.add(new TireIndex("60", "250"));
        loadIndexTiere.add(new TireIndex("61", "257"));
        loadIndexTiere.add(new TireIndex("62", "265"));
        loadIndexTiere.add(new TireIndex("63", "272"));
        loadIndexTiere.add(new TireIndex("64", "280"));
        loadIndexTiere.add(new TireIndex("65", "290"));
        loadIndexTiere.add(new TireIndex("66", "300"));
        loadIndexTiere.add(new TireIndex("67", "307"));
        loadIndexTiere.add(new TireIndex("68", "315"));
        loadIndexTiere.add(new TireIndex("69", "325"));
        loadIndexTiere.add(new TireIndex("70", "335"));
        loadIndexTiere.add(new TireIndex("71", "345"));
        loadIndexTiere.add(new TireIndex("72", "355"));
        loadIndexTiere.add(new TireIndex("73", "365"));
        loadIndexTiere.add(new TireIndex("74", "375"));
        loadIndexTiere.add(new TireIndex("75", "387"));
        loadIndexTiere.add(new TireIndex("76", "400"));
        loadIndexTiere.add(new TireIndex("77", "412"));
        loadIndexTiere.add(new TireIndex("78", "425"));
        loadIndexTiere.add(new TireIndex("79", "437"));
        loadIndexTiere.add(new TireIndex("80", "450"));
        loadIndexTiere.add(new TireIndex("81", "462"));
        loadIndexTiere.add(new TireIndex("82", "475"));
        loadIndexTiere.add(new TireIndex("83", "487"));
        loadIndexTiere.add(new TireIndex("84", "500"));
        loadIndexTiere.add(new TireIndex("85", "515"));
        loadIndexTiere.add(new TireIndex("86", "530"));
        loadIndexTiere.add(new TireIndex("87", "545"));
        loadIndexTiere.add(new TireIndex("88", "569"));
        loadIndexTiere.add(new TireIndex("89", "580"));
        loadIndexTiere.add(new TireIndex("90", "600"));
        loadIndexTiere.add(new TireIndex("91", "615"));
        loadIndexTiere.add(new TireIndex("92", "630"));
        loadIndexTiere.add(new TireIndex("93", "650"));
        loadIndexTiere.add(new TireIndex("94", "670"));
        loadIndexTiere.add(new TireIndex("95", "690"));
        loadIndexTiere.add(new TireIndex("96", "710"));
        loadIndexTiere.add(new TireIndex("97", "730"));
        loadIndexTiere.add(new TireIndex("98", "750"));
        loadIndexTiere.add(new TireIndex("99", "775"));
        loadIndexTiere.add(new TireIndex("100", "800"));
        loadIndexTiere.add(new TireIndex("101", "825"));
        loadIndexTiere.add(new TireIndex("102", "850"));
        loadIndexTiere.add(new TireIndex("103", "875"));
        loadIndexTiere.add(new TireIndex("104", "900"));
        loadIndexTiere.add(new TireIndex("105", "925"));
        loadIndexTiere.add(new TireIndex("106", "950"));
        loadIndexTiere.add(new TireIndex("107", "975"));
        loadIndexTiere.add(new TireIndex("108", "1000"));
        loadIndexTiere.add(new TireIndex("109", "1030"));
        loadIndexTiere.add(new TireIndex("110", "1060"));
        loadIndexTiere.add(new TireIndex("111", "1090"));
        loadIndexTiere.add(new TireIndex("112", "1120"));
        loadIndexTiere.add(new TireIndex("113", "1150"));
        loadIndexTiere.add(new TireIndex("114", "1180"));
        loadIndexTiere.add(new TireIndex("115", "1215"));
        loadIndexTiere.add(new TireIndex("116", "1250"));
        loadIndexTiere.add(new TireIndex("117", "1285"));
        loadIndexTiere.add(new TireIndex("118", "1320"));
        loadIndexTiere.add(new TireIndex("119", "1360"));
        loadIndexTiere.add(new TireIndex("120", "1400"));
        loadIndexTiere.add(new TireIndex("121", "1450"));
        loadIndexTiere.add(new TireIndex("122", "1500"));
        loadIndexTiere.add(new TireIndex("123", "1550"));
        loadIndexTiere.add(new TireIndex("124", "1600"));
        loadIndexTiere.add(new TireIndex("125", "1650"));
        loadIndexTiere.add(new TireIndex("126", "1700"));
        loadIndexTiere.add(new TireIndex("127", "1750"));
        loadIndexTiere.add(new TireIndex("128", "1800"));
        loadIndexTiere.add(new TireIndex("129", "1850"));
        loadIndexTiere.add(new TireIndex("130", "1900"));
        loadIndexTiere.add(new TireIndex("131", "1950"));
        loadIndexTiere.add(new TireIndex("132", "2000"));
        loadIndexTiere.add(new TireIndex("133", "2060"));
        loadIndexTiere.add(new TireIndex("134", "2120"));
        loadIndexTiere.add(new TireIndex("135", "2180"));
        loadIndexTiere.add(new TireIndex("136", "2240"));
        loadIndexTiere.add(new TireIndex("137", "2300"));
        loadIndexTiere.add(new TireIndex("138", "2360"));
        loadIndexTiere.add(new TireIndex("139", "2430"));
        loadIndexTiere.add(new TireIndex("140", "2500"));
        return loadIndexTiere;
    }

    private static List<String> status = new ArrayList<>();

    public static List<String> getStatus() {
        status.add("Zaczęto");
        status.add("Odrzucono");
        status.add("Zakończono");
        status.add("Anulowano");
        return status;
    }
    private static List<String> categorySemi=new ArrayList<>();

    public static List<String> getCategorySemi() {
        //TRUCK TIRES
        categorySemi.add("Bieżnik płaskie CT");
        categorySemi.add("Bieżnik Mini-Wing CT");
        categorySemi.add("Bieżnik ECL CT");
        categorySemi.add("Bieżnik płaskie CZ");
        categorySemi.add("Bieżnik Mini-Wing CZ");
        categorySemi.add("Bieżnik ECL CZ");
        categorySemi.add("Bieżnik płaskie CD");
        categorySemi.add("Bieżnik Mini-Wing CD");
        categorySemi.add("Bieżnik ECL CD");
        categorySemi.add("Bieżnik płaskie CF");
        categorySemi.add("Bieżnik Mini-Wing CF");
        categorySemi.add("Bieżnik ECL CF");
        //CAR TIRES
        categorySemi.add("Bieżnik Asymetryczny SL");
        categorySemi.add("Bieżnik Symetryczny SL");
        categorySemi.add("Bieżnik Asymetryczny SL");
        categorySemi.add("Bieżnik Asymetryczny SZ");
        categorySemi.add("Bieżnik Symetryczny SZ");
        categorySemi.add("Bieżnik Asymetryczny SZ");

        categorySemi.add("Klej");
        categorySemi.add("Łaty");
        categorySemi.add("Rozpuszczalnik");
        categorySemi.add("Wypełniacz Chemiczny");
        return categorySemi;
    }
    static List<String> workName=new ArrayList<>();

    public static List<String> getWorkName() {
        workName.add("Przyjęcie na stan");
        workName.add("Wstępna kontrola");
        workName.add("Kontrola maszynowa");
        workName.add("Szorstkowanie krasu");
        workName.add("Szlifowanie opony");
        workName.add("Wypełnienie ubytków");
        workName.add("Nakładanie bieżnika");
        workName.add("Wulkanizacja opony");
        workName.add("Kontrola jakości wyrobu");
        return workName;
    }
}
