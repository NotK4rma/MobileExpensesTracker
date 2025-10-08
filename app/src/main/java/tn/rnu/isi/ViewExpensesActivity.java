package tn.rnu.isi;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class ViewExpensesActivity extends AppCompatActivity {

    private ListView expenseList;
    private ArrayList<String> expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense);

        expenseList = findViewById(R.id.expenseList);
        expenses = new ArrayList<>();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String name = bundle.getString("expenseName");
            String price = bundle.getString("expensePrice");
            String amount = bundle.getString("expenseAmount");
            String category = bundle.getString("expenseCategory");


            String expenseItem = "🧾 " + name + "\n"
                    + "Category: " + category + "\n"
                    + "Item Price: " + (price.isEmpty() ? "N/A" : price) + "\n"
                    + "Total: " + amount;

            expenses.add(expenseItem);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                expenses
        );
        expenseList.setAdapter(adapter);
    }
}
