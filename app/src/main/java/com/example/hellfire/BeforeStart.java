package com.example.hellfire;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BeforeStart extends AppCompatActivity {

    private CheckBox checkBoxAgree;
    private Button buttonAgree;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_start);

        // Получаем ссылку на TextView из разметки
        TextView textView = findViewById(R.id.agree);
        checkBoxAgree = findViewById(R.id.checkBoxAgree);
        buttonAgree = findViewById(R.id.button2);



        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("By clicking \"I agree\" you also agree to our ");

        // Создаем кликабельный текст "privacy policy" с синим цветом
        SpannableString privacyPolicyClickableSpan = new SpannableString("privacy policy");
        privacyPolicyClickableSpan.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                // Действие при клике на "privacy policy"
                //
                // Toast.makeText(Before.this, "Вы перешли к политике конфиденциальности", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(BeforeStart.this, PrivacyPolicy.class);
                startActivity(intent);
                finish();
            }
        }, 0, privacyPolicyClickableSpan.length(), 0);
        privacyPolicyClickableSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 0, privacyPolicyClickableSpan.length(), 0);

        // Создаем кликабельный текст "terms" с красным цветом
        SpannableString termsClickableSpan = new SpannableString("terms of use");
        termsClickableSpan.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                // Действие при клике на "terms"
                //Toast.makeText(Before.this, "Вы перешли к условиям использования", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(BeforeStart.this, TermsActivity.class);
                startActivity(intent);
            }
        }, 0, termsClickableSpan.length(), 0);
        termsClickableSpan.setSpan(new ForegroundColorSpan(Color.RED), 0, termsClickableSpan.length(), 0);

        //SpannableString dot = new SpannableString(".");

        builder.append(privacyPolicyClickableSpan);
        builder.append(" and the ");
        builder.append(termsClickableSpan);
        builder.append(".");

        // Устанавливаем текст в TextView и делаем ссылки кликабельными
        textView.setText(builder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        checkBoxAgree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            buttonAgree.setEnabled(isChecked);
        });
    }

    public void to_SignUp(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Google ign In...");
        progressDialog.show();
        Intent intent = new Intent(BeforeStart.this, SignUp.class);
        startActivity(intent);
        progressDialog.dismiss();
        finish();
    }

    public void toMain(View view) {
        Intent intent = new Intent(BeforeStart.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}