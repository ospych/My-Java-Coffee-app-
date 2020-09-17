package com.example.myjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public boolean whippedCream(){
        CheckBox whippedCreamCheckbox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        return hasWhippedCream;
    }

    public boolean chocolate(){
        CheckBox chocolateCheckbox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        return hasChocolate;
    }

    public void submitOrder(View view) {

        EditText nameField = findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        int price = calculatePrice();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_for)+" " + name);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(price, whippedCream(), chocolate(), name));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void increment(View view) {
        if (quantity == 100){
            Toast.makeText(this,"Quantity of coffee must be less than 100", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1){
            Toast.makeText(this,"Quantity of coffee must be more than 0", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    private int calculatePrice(){

        if (chocolate()) {
            return quantity * 5 + 2;
        }
        else if (whippedCream()){
            return quantity * 5 + 1;
        }
        else {
            return quantity * 5;
        }

    }

    private String createOrderSummary(int price, boolean hasWhippedCream,
                                      boolean hasChocolate, String name){
        String message = getString(R.string.name_main) + " " + name;
        message +=getString(R.string.quantity_title) + quantity;
        message +=getString(R.string.add_whipped) + " " + hasWhippedCream;
        message +=getString(R.string.add_choco) + " " + hasChocolate;
        message +=getString(R.string.total) + " " + price;
        message +=getString(R.string.thank_you);
        return message;
    }

//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}