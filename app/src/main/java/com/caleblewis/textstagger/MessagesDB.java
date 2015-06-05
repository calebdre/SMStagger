package com.caleblewis.textstagger;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MessagesDB {

    SQLiteDatabase conn;
    Context context;

    public MessagesDB(Context context) {
        this.context = context;
        this.conn = this.context.openOrCreateDatabase("textMessages", this.context.MODE_PRIVATE, null);
    }

    public void createDB(){
        conn.execSQL("CREATE TABLE IF NOT EXISTS messages (" +
                "id integer primary key," +
                "name VARCHAR," +
                "number VARCHAR" +
                "message VARCHAR" +
                "date VARCHAR" +
                ");");

        File database = context.getDatabasePath("TextMessages.db");
    }

    public long addTextMessage(String name, String number, String message, String date){
        SQLiteStatement statement = conn.compileStatement("insert into messages (name, number, message, date) values (?, ?, ?, ?);");
        statement.bindAllArgsAsStrings(new String[]{name, number, message, date});
        return statement.executeInsert();
    }

    public void deleteMessage(long id){
        conn.execSQL("DELETE FROM messages WHERE id = " + id + ";");
    }

    public ArrayList<HashMap<String, String>> getMessages(){
        Cursor cursor = conn.rawQuery("SELECT * FROM messages", null);

        int idColumn = cursor.getColumnIndex("id");
        int nameColumn = cursor.getColumnIndex("name");
        int numberColumn= cursor.getColumnIndex("number");
        int messageColumn= cursor.getColumnIndex("message");
        int dateColumn= cursor.getColumnIndex("date");

        cursor.moveToFirst();

        ArrayList<HashMap<String, String>> messages = new ArrayList<HashMap<String, String>>();

        if(cursor != null && (cursor.getCount() > 0)){
            do{
                HashMap<String, String> message = new HashMap<String, String>();

                message.put("id", cursor.getString(idColumn));
                message.put("name", cursor.getString(nameColumn));
                message.put("number", cursor.getString(numberColumn));
                message.put("message", cursor.getString(messageColumn));
                message.put("date", cursor.getString(dateColumn));

                messages.add(message);
            }while(cursor.moveToNext());
        }

        return messages;
    }

}
