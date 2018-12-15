package com.teamedge.android.ministore;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    int price;
    int pairsOfShoes = 0;
    Button priceButton;
    CheckBox cleaningKitCheckBox;
    CheckBox stickersCheckBox;
    CheckBox hatCheckBox;
    boolean hasCleaningKit;
    boolean hasStickers;
    boolean hasHat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cleaningKitCheckBox = (CheckBox) findViewById(R.id.cleaningKit);
        stickersCheckBox = (CheckBox) findViewById(R.id.stickers);
        hatCheckBox = (CheckBox) findViewById(R.id.hat);
    }

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView)
                findViewById(R.id.quantity_text_view);
        quantityTextView.setText(Integer.toString(number));
    }

    public void increaseQuantity(View view){
        pairsOfShoes++;
        displayQuantity(pairsOfShoes);
        price = calculatePrice(hasCleaningKit, hasStickers, hasHat);
        displayPrice(price);
    }

    public void decreaseQuantity(View view){
        if(pairsOfShoes > 1){
            pairsOfShoes--;
        }
        displayQuantity(pairsOfShoes);
        price = calculatePrice(hasCleaningKit, hasStickers, hasHat);
        displayPrice(price);

        if (pairsOfShoes == 0){
            price /= 200;
        }
    }

    public void submitOrder(View view){

        hasCleaningKit = cleaningKitCheckBox.isChecked();
        hasStickers = stickersCheckBox.isChecked();
        hasHat = hatCheckBox.isChecked();


        price = calculatePrice(hasCleaningKit, hasStickers, hasHat);
        displayPrice(price);

        String orderMessage = "Thanks! here's your receipt: " + "\n" +
                "Ordered cleaning kit? " + hasCleaningKit + "\n" + "What about stickers? " + hasStickers + "\n" +
                "Mystery hat? " + hasHat + "\n" + "Pairs of shoes: " + pairsOfShoes + "\n" + "Price: " + price;

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Di-Hundred");
        intent.putExtra(Intent.EXTRA_TEXT, orderMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }




    private int calculatePrice(boolean addCleaningKit, boolean addStickers, boolean addHat){
        price = pairsOfShoes * 200;
        if(addCleaningKit == true){
            price += 15;
        }
        if(addStickers == true){
            price += 5;

        }
        if(addHat == true){
            price += 25;
        }
        return price;
    }

    private void displayPrice(int number){
        if(pairsOfShoes > 0) {
            priceButton = findViewById(R.id.order_button);
            priceButton.setText("Order" + "\n" + "$" + number);
        }
    }


    public void cleaningKitPrice(View view) {
        price = calculatePrice(hasCleaningKit, hasStickers, hasHat);
        displayPrice(price);
    }


    public void stickersPrice(View view) {
        price += 5;
        price = calculatePrice(hasCleaningKit, hasStickers, hasHat);
        displayPrice(price);
    }


    public void hatPrice(View view) {
        price += 25;
        price = calculatePrice(hasCleaningKit, hasStickers, hasHat);
        displayPrice(price);
    }
}
