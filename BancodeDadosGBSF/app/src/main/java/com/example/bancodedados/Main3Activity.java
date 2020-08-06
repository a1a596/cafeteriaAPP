package com.example.bancodedados;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bancodedados.BebidaBDHelper;
import com.example.bancodedados.R;

public class Main3Activity extends AppCompatActivity {
    private int indiceBebida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Bundle args = getIntent().getExtras();
        indiceBebida = args.getInt("id");
        try {
            SQLiteOpenHelper bebidaBDHelper = new BebidaBDHelper(this);
            SQLiteDatabase db = bebidaBDHelper.getReadableDatabase();
            Cursor cursor = db.query ("BEBIDA",
                    new String[] {"NOME", "DESCRICAO", "IMAGEM_RECURSO_ID", "FAVORITO"}, "_id = ?", // busca pela chave primária
                    new String[] {Integer.toString(indiceBebida)}, null, null, null);
            if (cursor.moveToFirst()) {
                String nomeText = cursor.getString(0);
                String descricaoText = cursor.getString(1);
                int fotoId = cursor.getInt(2);
                boolean isFavorito = (cursor.getInt(3) == 1);
                TextView nome = (TextView)findViewById(R.id.name);
                nome.setText(nomeText);
                TextView descricao = (TextView)findViewById(R.id.description);
                descricao.setText(descricaoText);
                ImageView photo = (ImageView)findViewById(R.id.photo);
                photo.setImageResource(fotoId);
                photo.setContentDescription(nomeText);
                CheckBox favorito = (CheckBox)findViewById(R.id.favorito);
                favorito.setChecked(isFavorito);
            }
            cursor.close();
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Banco de dados indisponível", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void onFavoritoClicked(View view){
        int drinkNo = indiceBebida;
        CheckBox favorito = (CheckBox)findViewById(R.id.favorito);
        ContentValues bebidaValores = new ContentValues();
        bebidaValores.put("FAVORITO", favorito.isChecked());
        SQLiteOpenHelper bebidaBDHelper = new BebidaBDHelper(Main3Activity.this);
        try {
            SQLiteDatabase db = bebidaBDHelper.getWritableDatabase();
            db.update("BEBIDA", bebidaValores, "_id = ?", new String[] {Integer.toString(drinkNo)});
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(Main3Activity.this, "Banco de dados indisponível", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}

