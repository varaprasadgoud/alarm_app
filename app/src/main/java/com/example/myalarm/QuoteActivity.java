package com.example.myalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class QuoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        retrieveQuote();

        EditText quote = findViewById(R.id.new_quote);
        Button saveQuoteButton = findViewById(R.id.save_quote);

        saveQuoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quote.getText() != null && quote.getText().length() != 0){
                    saveQuote(String.valueOf(quote.getText()));
                }else{
                    Toast.makeText(QuoteActivity.this, "Enter the morning quote!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void saveQuote(String quote){
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("quote", quote);
        myEdit.commit();
        Toast.makeText(this, "Saved your morning quote.", Toast.LENGTH_SHORT).show();
    }

    void retrieveQuote(){
        EditText quote = findViewById(R.id.new_quote);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String saved_quote = sh.getString("quote",null);

        if(saved_quote!= null){
            quote.setText(saved_quote);
        }
    }
}