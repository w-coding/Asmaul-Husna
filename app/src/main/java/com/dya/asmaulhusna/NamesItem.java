package com.dya.asmaulhusna;

public class NamesItem {

    private final String id;
    private final String name;
    private final String name_en;
    private final String kurdish;
    private final String english;
    private final String persian;
    private final String turkish;
    private final String spanish;
    private final String french;
    private final String chinese;
    private final String japanese;
    private final String korean;
    private final String hindi;
    private final String russian;

    public NamesItem(String id, String name,String name_en, String kurdish, String english, String persian, String turkish, String spanish, String french, String chinese, String japanese, String korean, String hindi, String russian) {
        this.id = id;
        this.name = name;
        this.name_en = name_en;
        this.kurdish = kurdish;
        this.english = english;
        this.persian = persian;
        this.turkish = turkish;
        this.spanish = spanish;
        this.french = french;
        this.chinese = chinese;
        this.japanese = japanese;
        this.korean = korean;
        this.hindi = hindi;
        this.russian = russian;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getName_en() {
        return name_en;
    }

    public String getKurdish() {
        return kurdish;
    }

    public String getEnglish() {
        return english;
    }

    public String getPersian() {
        return persian;
    }

    public String getTurkish() {
        return turkish;
    }

    public String getSpanish() {
        return spanish;
    }

    public String getFrench() {
        return french;
    }

    public String getChinese() {
        return chinese;
    }

    public String getJapanese() {
        return japanese;
    }

    public String getKorean() {
        return korean;
    }

    public String getHindi() {
        return hindi;
    }

    public String getRussian() {
        return russian;
    }
}
