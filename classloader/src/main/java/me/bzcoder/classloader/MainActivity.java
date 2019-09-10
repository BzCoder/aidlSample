package me.bzcoder.classloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView class_loader_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        class_loader_name = findViewById(R.id.class_loader_name);
        class_loader_name.setText(getClassLoader().getClass().getName()+"\n"+getClassLoader().getParent().getClass().getName());
    }
}
