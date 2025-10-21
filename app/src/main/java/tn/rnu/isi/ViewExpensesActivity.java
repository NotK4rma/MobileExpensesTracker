package tn.rnu.isi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class ViewExpensesActivity extends Activity {

    private ListView expenseList;
    private ArrayList<String> expenses;
    private Button btnAddExpense;

    private static final int REQUEST_ADD_EXPENSE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense);

        expenseList = findViewById(R.id.expenseList);
        btnAddExpense = findViewById(R.id.addExp);
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
            startActivityForResult(intent, REQUEST_ADD_EXPENSE);
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
        }
    }


}
