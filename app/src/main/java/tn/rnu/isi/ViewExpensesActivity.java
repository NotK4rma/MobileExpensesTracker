package tn.rnu.isi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;

public class ViewExpensesActivity extends Activity {

    private ListView expenseList;
    private ArrayList<String> expenses;
    private Button btnAddExpense;
    private static final int REQUEST_ADD_EXPENSE = 1;

    private float expenseDollar = 0f;

    private float budgetDollar= 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense);

        expenseList = findViewById(R.id.expenseList);
        btnAddExpense = findViewById(R.id.addExp);
        FloatingActionButton floatButton = findViewById(R.id.GoHome);

        expenses = new ArrayList<>();


        getIntentData(getIntent());


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                expenses
        );
        expenseList.setAdapter(adapter);

        btnAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(ViewExpensesActivity.this, AddExpenseActivity.class);
            Bundle bund = new Bundle();
            bund.putFloat("budget",budgetDollar);
            intent.putExtras(bund);
            startActivityForResult(intent, REQUEST_ADD_EXPENSE);
        });

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent hIntent = new Intent(ViewExpensesActivity.this, HomeActivity.class);
                Bundle bund = new Bundle();
                bund.putFloat("expenseTotal", expenseDollar);
                bund.putFloat("budget", budgetDollar);
                hIntent.putExtras(bund);
                startActivity(hIntent);
            }
        });



    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_EXPENSE && resultCode == RESULT_OK && data != null) {
            getIntentData(data);
            ((ArrayAdapter) expenseList.getAdapter()).notifyDataSetChanged();
        }
    }




    private void getIntentData(Intent i){
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            String name = bundle.getString("expenseName");
            String price = bundle.getString("expensePrice");
            String amount = bundle.getString("expenseAmount");
            String category = bundle.getString("expenseCategory");
            budgetDollar = bundle.getFloat("budget");

            String emoji ;
            switch (category) {
                case "Food":
                    emoji = "🍴";
                    break;
                case "Transport":
                    emoji = "🚌";
                    break;
                case "Bills":
                    emoji = "💰";
                    break;
                case "Entertainment":
                    emoji = "🎮";
                    break;
                case "Shopping":
                    emoji = "🛒";
                    break;
                case "Other":
                    emoji = "🤷";
                    break;
                default:
                    emoji = "🧾";

            }



            String expenseItem = emoji + " " + name + "\n"
                    + "Category: " + category + "\n"
                    + "Item Price: " + price + "\n"
                    + "Total: " + Integer.parseInt(amount)*Integer.parseInt(price);

            expenses.add(expenseItem);
            expenseDollar += Integer.parseInt(amount)*Integer.parseInt(price);
        }
    }






}
