package com.example.bancodedados;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor favoritesCursor;
    private Cursor categoriasCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int indice = 1;
        ListView listCategorias = (ListView) findViewById(R.id.list_options);
        try {
            SQLiteOpenHelper bebidaBDHelper = new BebidaBDHelper(this);
            db = bebidaBDHelper.getReadableDatabase();
            categoriasCursor = db.query("CATEGORIAS", new String[] {"NOME"}, "_id = ?", new String[] {Integer.toString(indice)}, null, null, null);
            CursorAdapter categoriasAdapter = new SimpleCursorAdapter(MainActivity.this, android.R.layout.simple_list_item_1, categoriasCursor, new String[]{"NOME"}, new int[]{android.R.id.text1}, 0);
            listCategorias.setAdapter(categoriasAdapter);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Banco de dados indisponível", Toast.LENGTH_SHORT);
            toast.show();
        }
        listCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {


                switch (position) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "Ainda não servimos comida!", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "Ainda não temos produtos na mercearia!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        ListView listaFavoritos = (ListView) findViewById(R.id.lista_favoritos);
        try {
            SQLiteOpenHelper bebidaBDHelper = new BebidaBDHelper(this);
            db = bebidaBDHelper.getReadableDatabase();
            favoritesCursor = db.query("BEBIDA", new String[]{"_id", "NOME"}, "FAVORITO = 1", null, null, null, null);
            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(MainActivity.this, android.R.layout.simple_list_item_1, favoritesCursor, new String[]{"NOME"}, new int[]{android.R.id.text1}, 0);
            listaFavoritos.setAdapter(favoriteAdapter);


        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Banco de dados indisponível", Toast.LENGTH_SHORT);
            toast.show();
        }

        listaFavoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                Bundle params = new Bundle();
                params.putInt("id", (int) id);
                intent.putExtras(params);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        favoritesCursor.close();
        categoriasCursor.close();
        db.close();
    }
    public void onRestart() {
        super.onRestart();
        try {
            BebidaBDHelper bebidaBDHelper = new BebidaBDHelper(this);
            db = bebidaBDHelper.getReadableDatabase();
            Cursor newCursor = db.query("BEBIDA", new String[] { "_id", "NOME"}, "FAVORITO = 1", null, null, null, null);
            ListView listaFavoritos = (ListView)findViewById(R.id.lista_favoritos);
            CursorAdapter adapter = (CursorAdapter) listaFavoritos.getAdapter();
            adapter.changeCursor(newCursor);
            favoritesCursor = newCursor;
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Banco de dados indisponível", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}