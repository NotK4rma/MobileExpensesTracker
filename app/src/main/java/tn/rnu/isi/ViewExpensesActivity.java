package tn.rnu.isi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;

public class ViewExpensesActivity extends Activity {

    private ListView expenseList;
    private ArrayList<String> expenses;
    private Button btnAddExpense;
    private static final int REQUEST_ADD_EXPENSE = 1;

    private ImageView close ;

    private float expenseDollar = 0f;

    private float budgetDollar= 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense);

        expenseList = findViewById(R.id.expenseList);
        btnAddExpense = findViewById(R.id.addExp);
        FloatingActionButton floatButton = findViewById(R.id.GoHome);
        close = findViewById(R.id.wipeList);
        expenses = new ArrayList<>();


        getIntentData(getIntent());


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                ExpenseData.getExpenses()
        );
        expenseList.setAdapter(adapter);

        btnAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(ViewExpensesActivity.this, AddExpenseActivity.class);
            Bundle bund = new Bundle();
            //bund.putFloat("budget",budgetDollar);
            intent.putExtras(bund);
            startActivityForResult(intent, REQUEST_ADD_EXPENSE);
        });

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent hIntent = new Intent(ViewExpensesActivity.this, HomeActivity.class);
                Bundle bund = new Bundle();
                bund.putFloat("expenseTotal", ExpenseData.getExpenseDollar());
                //bund.putFloat("budget", budgetDollar);
                hIntent.putExtras(bund);
                hIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(hIntent);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseData.clearExpenses();
                ((ArrayAdapter) expenseList.getAdapter()).notifyDataSetChanged();

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
        if (bundle != null ) {
            String name = bundle.getString("expenseName");
            String price = bundle.getString("expensePrice");
            String amount = bundle.getString("expenseAmount");
            String category = bundle.getString("expenseCategory");
            //budgetDollar = bundle.getFloat("budget");

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

            int Cost = Integer.parseInt(amount) * Integer.parseInt(price);


            String expenseItem = emoji + " " + name + "\n"
                    + "Category: " + category + "\n"
                    + "Item Price: " + price + "\n"
                    + "Total: " + Cost;

            ExpenseData.addExpense(expenseItem,Cost);

        }
    }









    static class ExpenseData {
        private static ArrayList<String> expenses = new ArrayList<>();
        private static float expenseDollar = 0f;


        public static ArrayList<String> getExpenses() {
            return expenses;
        }

        public static void addExpense(String expense, float cost) {
            expenses.add(expense);
            expenseDollar += cost;
        }

        public static float getExpenseDollar() {
            return expenseDollar;
        }





        public static void clearExpenses() {
            expenses.clear();
            expenseDollar = 0f;
        }
    }






}
