package com.bouda.ulia.uielements;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.stream.Collectors;

public class UliaCard {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<UliaCard> makeList(@NonNull List<String> textList){
        return textList.stream().map(UliaCard::newInstance).collect(Collectors.toList());
    }

    private String cardText;

    public UliaCard(String text){
        cardText = text;
    }

    public static UliaCard newInstance(String text){
        return new UliaCard(text);
    }

    @NonNull
    public String toString(){
        return "CARD: " + this.cardText;
    }

    public String getCardText() {
        return cardText;
    }

    public void setCardText(String cardText) {
        this.cardText = cardText;
    }
}
