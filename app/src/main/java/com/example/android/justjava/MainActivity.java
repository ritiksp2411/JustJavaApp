package com.example.android.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameEditText = (EditText) findViewById(R.id.name_description_view);
        String name=nameEditText.getText().toString();
        CheckBox whippedCreamCheckBox= (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox= (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();
        boolean hasChocolate=chocolateCheckBox.isChecked();
        int priceofcoffee=calculatePrice(hasWhippedCream,hasChocolate);
        String message=createOrderSummary(priceofcoffee,hasWhippedCream,hasChocolate,name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
//        displayMessage(message);
    }
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate,String name){
        String summary="Name: " + name + "\nQuantity: " + quantity + "\nAdd whipped cream? " + hasWhippedCream + "\nAdd Chocolate? " + hasChocolate + "\nTotal: $" + price + "\nThank You!!";
        return summary;
    }
    public void increment(View view) {
        if(quantity<100) quantity++;
        else Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if(quantity>1) quantity--;
        else Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
        displayQuantity(quantity);
    }

//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int percoffee=5;
        if(hasWhippedCream==true) percoffee++;
        if(hasChocolate==true) percoffee+=2;
        return quantity * percoffee;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}