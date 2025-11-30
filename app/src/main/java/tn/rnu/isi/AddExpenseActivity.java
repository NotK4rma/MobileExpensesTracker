package tn.rnu.isi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import tn.rnu.isi.databinding.ActivityAddExpenseBinding;
public class AddExpenseActivity extends AppCompatActivity {

    private ActivityAddExpenseBinding binding;

//    private EditText expenseName, expensePrice, expenseAmount;
//    private Spinner expenseCategorySpinner;
//    private Button btnAddExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_expense);

        binding = ActivityAddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        expenseName = findViewById(R.id.expenseName);
//        expensePrice = findViewById(R.id.expensePrice);
//        expenseAmount = findViewById(R.id.expenseAmount);
//        expenseCategorySpinner = findViewById(R.id.expenseCategorySpinner);
//        btnAddExpense = findViewById(R.id.btnAddExpense);
//        EditText dateInput = findViewById(R.id.dateInput);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.expense_categories,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.expenseCategorySpinner.setAdapter(adapter);


        binding.btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.expenseName.getText().toString().trim();
                String price = binding.expensePrice.getText().toString().trim();
                String amount = binding.expenseAmount.getText().toString().trim();
                String category = binding.expenseCategorySpinner.getSelectedItem().toString();
                String date;

                if (name.isEmpty() || amount.isEmpty() || price.isEmpty()) {
                    Toast.makeText(AddExpenseActivity.this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }


                Bundle bundle = new Bundle();
                bundle.putString("expenseName", name);
                bundle.putString("expensePrice", price);
                bundle.putString("expenseAmount", amount);
                bundle.putString("expenseCategory", category);
                if(!binding.dateInput.getText().toString().isEmpty()) {
                    date = binding.dateInput.getText().toString();
                    bundle.putString("date", date);

                }

                //                if(getIntent()!=null && getIntent().getExtras()!=null && getIntent().getExtras().containsKey("budget")){
//                    bundle.putFloat("budget",getIntent().getExtras().getFloat("budget")
//                    );
//
//                }





                Intent intent = new Intent(AddExpenseActivity.this, ViewExpensesActivity.class);
                intent.putExtras(bundle);

                if (getCallingActivity() != null) {
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });


        binding.dateInput.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    this,
                    (view, y, m, d) -> binding.dateInput.setText(d + "/" + (m + 1) + "/" + y),
                    year, month, day
            );
            dialog.show();
        });



    }
}
