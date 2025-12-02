package tn.rnu.isi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class AskAi extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ScrollView chat;
    private TextView chatContent ;
    private EditText prompt;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ask_ai);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.nav_view);
        chat = findViewById(R.id.chat);
        chatContent = findViewById(R.id.chat_content);
        prompt = findViewById(R.id.prompt);


        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    Toast.makeText(AskAi.this, "Home clicked", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(AskAi.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } else if (id == R.id.nav_add_expense) {
                    Toast.makeText(AskAi.this, "Add Expenses", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(AskAi.this, AddExpenseActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } else if (id == R.id.nav_view) {
                    Intent intent= new Intent(AskAi.this, ViewExpensesActivity.class);
                    Toast.makeText(AskAi.this, "Expenses listed here", Toast.LENGTH_SHORT).show();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } else if (id == R.id.nav_reports) {
                    Toast.makeText(AskAi.this, "Reports clicked", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_settings) {
                    Toast.makeText(AskAi.this, "Settings clicked", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_logout) {
                    Toast.makeText(AskAi.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    ViewExpensesActivity.ExpenseData.clearExpenses();
                    Intent intent = new Intent (AskAi.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });




        prompt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_SEND) {

                    String userMessage = prompt.getText().toString().trim();
                    if (!userMessage.isEmpty()) {
                        chatContent.append("You: " + userMessage + "\n\n");
                        prompt.setText("");
                        GeminiClient gemini = new GeminiClient();
                        String context = chatContent.getText().toString();
                        gemini.generateResponse(context, new GeminiClient.GeminiCallback() {
                            @Override
                            public void onResult(String text) {

                                runOnUiThread(() -> {



                                    chatContent.append("AI: " + text + "\n\n\n");

                                });
                            }

                            @Override
                            public void onError(String error) {
                                chatContent.append("Ai: Sorry I didn't get that!" + "\n" + error);
                                Log.e("APP", "Error: " + error);

                            }
                        });

                    }
                    return true;
                }
                return false;
            }
        });

    }
}