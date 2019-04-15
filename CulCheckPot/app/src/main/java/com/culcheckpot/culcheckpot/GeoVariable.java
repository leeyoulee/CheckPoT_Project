package com.culcheckpot.culcheckpot;

public class GeoVariable {

    public static double latitude; // static 클래스 변수 위도
    public static double longitube; // static 클래스 변수 경도
    public static String searchedGu; // static 클래스 변수 역지오해서 자른 구

    public static String getEcoment() {
        return ecoment;
    }

    public static void setEcoment(String ecoment) {
        GeoVariable.ecoment = ecoment;
    }

    public static String ecoment;

    public static String getAirEco() {
        return airEco;
    }

    public static void setAirEco(String airEco) {
        GeoVariable.airEco = airEco;
    }

    public static String getMise() {
        return mise;
    }

    public static void setMise(String mise) {
        GeoVariable.mise = mise;
    }

    public static String getChomise() {
        return chomise;
    }

    public static void setChomise(String chomise) {
        GeoVariable.chomise = chomise;
    }

    public static String airEco; // static 환경상태
    public static String mise; // static 미세먼지
    public static String chomise; // static 초미세먼지

    public static String getSearchedGu() {
        return searchedGu;
    }

    public static void setSearchedGu(String searchedGu) {
        GeoVariable.searchedGu = searchedGu;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        GeoVariable.latitude = latitude;
    }

    public static double getLongitube() {
        return longitube;
    }

    public static void setLongitube(double longitube) {
        GeoVariable.longitube = longitube;
    }
}