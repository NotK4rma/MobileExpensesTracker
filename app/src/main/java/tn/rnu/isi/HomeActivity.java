package tn.rnu.isi;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    private TextView Balance;
    private TextView expense;

    private EditText tv_income;

    private CardView addIncomeCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.nav_view);
        tv_income = findViewById(R.id.tv_income);
        Balance = findViewById(R.id.tv_total_balance);
        expense = findViewById(R.id.tv_expenses);
        addIncomeCard = findViewById(R.id.card_add_income);



        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    Toast.makeText(HomeActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_add_expense) {
                    Toast.makeText(HomeActivity.this, "Add Expenses", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(HomeActivity.this, AddExpenseActivity.class);
//                    float budget = Float.parseFloat(tv_income.getText().toString());
//                    Bundle bund = new Bundle();
//                    bund.putFloat("budget", budget);
//                    intent.putExtras(bund);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } else if (id == R.id.nav_view) {
                    Intent intent= new Intent(HomeActivity.this, ViewExpensesActivity.class);
                    Toast.makeText(HomeActivity.this, "Expenses listed here", Toast.LENGTH_SHORT).show();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } else if (id == R.id.nav_reports) {
                    Toast.makeText(HomeActivity.this, "Reports clicked", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_settings) {
                    Toast.makeText(HomeActivity.this, "Settings clicked", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_logout) {
                    Toast.makeText(HomeActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    ViewExpensesActivity.ExpenseData.clearExpenses();
                    Intent intent = new Intent (HomeActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        CardView addExpense = findViewById(R.id.card_add_expense);
        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float budget = Float.parseFloat(tv_income.getText().toString());
                Bundle bund = new Bundle();
                bund.putFloat("budget", budget);
                Intent expIntent = new Intent(HomeActivity.this, AddExpenseActivity.class);
                expIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                expIntent.putExtras(bund);
                startActivity(expIntent);
            }
        });


        addIncomeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_income.setText(String.valueOf(Float.parseFloat(tv_income.getText().toString())+100));
                if(Float.parseFloat(Balance.getText().toString())!=0){
                    Balance.setText(String.valueOf(-Float.parseFloat(expense.getText().toString())+Float.parseFloat(tv_income.getText().toString())));
                }
            }
        });

        tv_income.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                Balance.setText(String.valueOf(-Float.parseFloat(expense.getText().toString())+Float.parseFloat(tv_income.getText().toString())));
            }
        });


        Intent serviceInt = new Intent(HomeActivity.this, NotifService.class);
        startService(serviceInt);


    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        updateUI();
    }

    private void updateUI() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            float expenseTotal = intent.getExtras().getFloat("expenseTotal");
            Balance.setText(String.valueOf(-expenseTotal + Float.parseFloat(tv_income.getText().toString())));
            expense.setText(String.valueOf(expenseTotal));
            //tv_income.setText(String.valueOf(budget));
        }
    }



}