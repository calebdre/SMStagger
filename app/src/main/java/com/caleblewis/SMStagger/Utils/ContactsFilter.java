package com.caleblewis.SMStagger.Utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Collection;

public class ContactsFilter {

    Collection<String> contacts;
    ContentResolver contentResolver;

    public ContactsFilter(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
        this.contacts = get_contacts();
    }

    private Collection<String> get_contacts() {
        ArrayList<String> phone = new ArrayList<String>();

        ContentResolver cr = contentResolver;
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phone.add(name + " " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }

        return phone;
    }

    public Collection<String> filter(String against){
        ArrayList<String> newList = new ArrayList<String>();
        char[] against_array = against.toLowerCase().toCharArray();

        for(String contact: contacts){
            char[] contact_array = contact.toLowerCase().toCharArray();
            boolean match = true;

            for (int i =0; i < against_array.length; i++){
                if(!Character.toString(against_array[i]).equals(Character.toString(contact_array[i]))){
                    match = false;
                    break;
                }
            }

            if(match) newList.add(contact);
        }

        return newList;
    }
}
