package com.example.internationalnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsDetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{
private AppBarLayout appBarLayout;
private Toolbar toolbar;
private String mUrl;
private ImageView imageView;
private String img;
private WebView webView;
private DatabaseReference reference;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        reference=FirebaseDatabase.getInstance().getReference("UriNews");
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView=findViewById(R.id.image);
        appBarLayout=findViewById(R.id.appbar);
        Intent intent = getIntent();
        img =intent.getStringExtra("img");
        mUrl = intent.getStringExtra("url");
        Picasso.get().load(img).into(imageView);
        webView=findViewById(R.id.veb_view);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(mUrl);
       // writeData(mUrl);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String uri=dataSnapshot.getValue().toString();
                for (DataSnapshot child:dataSnapshot.getChildren()){
                    Log.d("myLog","Key is"+ child.getKey());
                    Log.d("myLog","Ref is"+ child.getRef().toString());
                    Log.d("myLog","Value is"+ child.getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*FirebaseDatabaseHelper firebaseDatabaseHelper=new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.writeData(mUrl);
        firebaseDatabaseHelper.readData();*/

    }
    public void writeData(String uri){
        reference.push().child("uri").setValue(uri);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(i) / (float) maxScroll;
        if (percentage == 1f) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }
}
