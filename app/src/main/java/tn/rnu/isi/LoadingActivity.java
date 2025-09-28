package tn.rnu.isi;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_load);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ProgressBar bar = findViewById(R.id.loadingBar);

        CountDownTimer timer = new CountDownTimer(3000, 600) {
            @Override
            public void onTick(long millisUntilFinished) {
                int current = bar.getProgress();
                if(current<bar.getMax()){
                    bar.setProgress(current + 20);

                }else{
                    bar.setProgress(100);
                }
            }
            @Override
            public void onFinish() {
                bar.setProgress(100);
                Toast.makeText(LoadingActivity.this, "Welcome to The Home page" , Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoadingActivity.this, HomeActivity.class);
                startActivity(i);
            }

        };

        timer.start();

    }
}