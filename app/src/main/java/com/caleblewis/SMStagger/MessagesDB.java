package com.caleblewis.SMStagger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.caleblewis.SMStagger.Models.TextMessage;
import com.caleblewis.SMStagger.Models.TextMessageBuilder;
import com.caleblewis.SMStagger.Exceptions.IncompleteTextMessageException;

import java.util.ArrayList;
import java.util.List;

public class MessagesDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TextMessage";
    private static final String TABLE_NAME = "messages";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NUMBER = "number";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_DATE = "date_string";
    private static final String KEY_SENT = "sent";

    public MessagesDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public long addTextMessage(TextMessage tm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = storeTextMessageValues(tm);

        long id = db.insert(TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public void deleteMessage(TextMessage textMessage) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(textMessage.getId())});
        db.close();
    }

    public List<TextMessage> getSentMessages(){
        return getMessages(true);
    }

    public List<TextMessage> getScheduledMessages() {
        return getMessages(false);
    }

    public List<TextMessage> getMessages(boolean sent){
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " where " + KEY_SENT + " =";
        if(sent){
            selectQuery += 1;
        }else{
            selectQuery += 0;
        }

        List<TextMessage> messageList = new ArrayList<TextMessage>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TextMessageBuilder tm = new TextMessageBuilder();
                tm.setId(Integer.parseInt(cursor.getString(0)));
                tm.setName(cursor.getString(1));
                tm.setPhone(cursor.getString(2));
                tm.setMessage(cursor.getString(3));
                tm.setDate(cursor.getString(4));
                // Adding contact to list
                try {
                    messageList.add(tm.build());
                } catch (IncompleteTextMessageException e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());
        }

        // return contact list
        return messageList;
    }

    public void updateMessage(TextMessage textMessage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = storeTextMessageValues(textMessage);
        db.update(TABLE_NAME, values, KEY_ID + "= ?", new String[] {Long.toString(textMessage.getId())});
    }

    public void markMessageSent(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SENT, 1);

        db.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{id});
    }

    private ContentValues storeTextMessageValues(TextMessage tm){
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, tm.getName());
        values.put(KEY_PHONE_NUMBER, tm.getPhone());
        values.put(KEY_MESSAGE, tm.getMessage());
        values.put(KEY_DATE, tm.getDate());
        values.put(KEY_SENT, tm.getSent());

        return values;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " VARCHAR,"
                + KEY_PHONE_NUMBER + " VARCHAR,"
                + KEY_MESSAGE + " TEXT,"
                + KEY_DATE + " VARCHAR,"
                + KEY_SENT + " INREGER default 0"
                + ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
