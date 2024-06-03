package com.example.lampara;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Toolbarclass extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.inicio) {
            Toast.makeText(this, getString(R.string.strinicio), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return true;
        }

        if (id == R.id.tempo) {
            Toast.makeText(this, getString(R.string.strtempo), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, temporizador.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.conect) {
            Toast.makeText(this, getString(R.string.strconect), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, conecta.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
