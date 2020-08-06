package com.example.bancodedados;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bancodedados.R;

public class BebidaBDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bdCafe";
    private static final int DB_VERSION = 1;
    public BebidaBDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        atualizarBD(db, 0, DB_VERSION);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        atualizarBD(db, oldVersion, newVersion);
    }
    private void atualizarBD(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE CATEGORIAS (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "NOME TEXT);");
            inserirCategorias(db, "Bebidas");
            inserirCategorias(db, "Comida");
            inserirCategorias(db, "Mercadorias");
            db.execSQL("CREATE TABLE BEBIDA (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "NOME TEXT, " + "DESCRICAO TEXT, " + "IMAGEM_RECURSO_ID INTEGER);");
            inserirBebida(db, "Latte", "Latte é uma bebida de café expresso com uma quantidade " + "generosa de espuma de leite no topo.", R.drawable.latte);
            inserirBebida(db, "Cappuccino", "Um cappuccino clássico e consiste em um terço de café " + "expresso, um terço de leite vaporizado e um terço de " + "espuma de leite vaporizado.", R.drawable.cappuccino);
            inserirBebida(db, "Filter", "Café coado do grau torrado e fresco da mais alta qualidade.", R.drawable.filter);
        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE BEBIDA ADD COLUMN FAVORITO NUMERIC;");
        }
    }
    private static void inserirBebida(SQLiteDatabase db, String nome, String descricao, int recursoId) {
        ContentValues bebidaValores = new ContentValues();
        bebidaValores.put("NOME", nome);
        bebidaValores.put("DESCRICAO", descricao);
        bebidaValores.put("IMAGEM_RECURSO_ID", recursoId);
        db.insert("BEBIDA", null, bebidaValores);
    }
    private static void inserirCategorias(SQLiteDatabase db, String nome) {
        ContentValues categoriaValores = new ContentValues();
        categoriaValores.put("NOME", nome);
        db.insert("CATEGORIAS", null, categoriaValores);
    }
}