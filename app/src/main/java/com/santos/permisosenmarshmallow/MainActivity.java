package com.santos.permisosenmarshmallow;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private View vista;
    private static final int SOLICITUD_PERMISO_WRITE_CALL_LOG = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                borrarLlamada();
                vista = findViewById(R.id.content_main);

            }
        });
    }

    void borrarLlamada() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALL_LOG)
                == PackageManager.PERMISSION_GRANTED) {
            getContentResolver().delete(CallLog.Calls.CONTENT_URI,
                    "number='4454552191'", null);
            Snackbar.make(vista, "Llamadas borradas del registro.",
                    Snackbar.LENGTH_SHORT).show();
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALL_LOG)
                == PackageManager.PERMISSION_GRANTED) {

        } else {
            solicitarPermiso(Manifest.permission.WRITE_CALL_LOG, "Sin el permiso"+
                            " administrar llamadas no puedo borrar llamadas del registro.",
                    SOLICITUD_PERMISO_WRITE_CALL_LOG, this);
        }
    }
    public static void solicitarPermiso(final String permiso, String justificacion,
                                        final int requestCode, final Activity actividad) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad,
                permiso)){
            new AlertDialog.Builder(actividad)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(actividad,
                                    new String[]{permiso}, requestCode);
                        }})
                    .show();
        } else {
            ActivityCompat.requestPermissions(actividad,
                    new String[]{permiso}, requestCode);
        }
    }
    @Override public void onRequestPermissionsResult(int requestCode,
                                                     String[] permissions, int[] grantResults) {
        if (requestCode == SOLICITUD_PERMISO_WRITE_CALL_LOG) {
            if (grantResults.length== 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                borrarLlamada();
            } else {
                Toast.makeText(this, "Sin el permiso, no puedo realizar la " +
                        "acción", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
