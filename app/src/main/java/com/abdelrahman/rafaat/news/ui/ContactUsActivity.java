package com.abdelrahman.rafaat.news.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


import com.abdelrahman.rafaat.news.R;
import com.abdelrahman.rafaat.news.databinding.ActivityContactUsBinding;
import com.google.android.material.snackbar.Snackbar;

public class ContactUsActivity extends AppCompatActivity {

    private ActivityContactUsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.gmailTextView.setOnClickListener(v -> openGmail());

        binding.websiteTextView.setOnClickListener(v ->
                openLink(getString(R.string.website_link))
        );

        binding.telegramTextView.setOnClickListener(v ->
                openLink(getString(R.string.telegram_link))
        );

        binding.facebookImageView.setOnClickListener(v ->
                openLink(getString(R.string.facebook_link))
        );

        binding.instagramImageView.setOnClickListener(v ->
                openLink(getString(R.string.instagram_link))
        );

        binding.twitterImageView.setOnClickListener(v ->
                openLink(getString(R.string.twitter_link))
        );

        binding.linkedImageView.setOnClickListener(v ->
                openLink(getString(R.string.linkedin_link))
        );

        binding.mediumImageView.setOnClickListener(v ->
                openLink(getString(R.string.medium_link))
        );

    }

    private void openGmail() {
        String recipientEmail = getString(R.string.email);
        String emailSubject = getString(R.string.email_title);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

        emailIntent.setType("message/rfc822");

        Uri uri = Uri.parse("mailto:" + recipientEmail + "?subject=" + Uri.encode(emailSubject));
        emailIntent.setData(uri);

        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException exception) {
            showSnackBar(binding.getRoot(), exception.getLocalizedMessage());
        }
    }

    private void showSnackBar(View view, String message) {
        Snackbar snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setActionTextColor(Color.WHITE);
        snackBar.getView().setBackgroundColor(Color.RED);
        snackBar.show();
    }

    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}