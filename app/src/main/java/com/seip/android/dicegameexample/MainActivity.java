package com.seip.android.dicegameexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.seip.android.dicegameexample.databinding.ActivityMainBinding;

import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final SecureRandom sr = new SecureRandom();
    private int sum = 0;
    private int target = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.rollBtn.setOnClickListener(v -> {
            roll();
            if (target > 0) {
                checkTarget();
            } else {
                determineStatus();
            }
        });

        binding.resetBtn.setOnClickListener(v -> {
            reset();
        });
    }

    private void roll() {
        int r1 = sr.nextInt(6) + 1;
        int r2 = sr.nextInt(6) + 1;
        sum = r1 + r2;
        binding.dice1Btn.setText(String.valueOf(r1));
        binding.dice2Btn.setText(String.valueOf(r2));
        binding.sumTV.setText(String.valueOf(sum));
    }

    private void reset() {
        binding.dice1Btn.setText("");
        binding.dice2Btn.setText("");
        sum = 0;
        target = 0;
        binding.sumTV.setText(String.valueOf(sum));
        binding.resetBtn.setVisibility(View.GONE);
        binding.statusTV.setVisibility(View.GONE);
        binding.targetLayout.setVisibility(View.GONE);
        binding.rollBtn.setEnabled(true);
    }

    private void checkTarget() {
        if (sum == target) {
            setStatus("YOU WIN!!!");
        } else if (sum == 7) {
            setStatus("YOU LOST!!!");
        }
    }

    private void setStatus(String status) {
        binding.statusTV.setText(status);
        binding.statusTV.setVisibility(View.VISIBLE);
        binding.resetBtn.setVisibility(View.VISIBLE);
        binding.rollAgainTV.setVisibility(View.GONE);
        binding.rollBtn.setEnabled(false);
    }

    private void setNewTarget() {
        target = sum;
        binding.targetLayout.setVisibility(View.VISIBLE);
        binding.rollAgainTV.setVisibility(View.VISIBLE);
        binding.targetTV.setText(String.valueOf(target));
    }

    private void determineStatus() {
        switch (sum) {
            case 7:
            case 11:
                setStatus("YOU WIN!!!");
                break;
            case 2:
            case 3:
            case 12:
                setStatus("YOU LOST!!!");
                break;
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
                setNewTarget();
                break;
        }
    }
}