package com.caleblewis.textstagger;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateTextMessageActivity extends Activity{

    private EditText dateText;
    private EditText timeText;
    private EditText contactField;
    private EditText phoneField;
    private EditText messageField;
    private ListView contactsListView;
    private ArrayAdapter<String> conactsListAdapter;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private TextMessageBuilder messageBuilder = new TextMessageBuilder();

    private Calendar dateBuilder = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_text_message);

        set_fields();
        initialize_dialogs();
        set_listeners();

    }

    private void set_listeners() {
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

        contactField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                conactsListAdapter.clear();
                conactsListAdapter.addAll(new ContactsFilter(getContentResolver()).filter(s.toString()));
                contactsListView.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String contact = conactsListAdapter.getItem(position);
                contact = contact.replace("(", "").replace(")", "").replace("-", " ").replace("+1", "");

                Pattern contactNamePattern = Pattern.compile("([^\\d]*)");
                Matcher m = contactNamePattern.matcher(contact);

                if (m.find()) {
                    String name = m.group(0);
                    String number = contact.substring(name.length());

                    contactField.setText(name);
                    phoneField.setText(number);
                    contactsListView.setVisibility(View.GONE);

                    messageBuilder.setName(name);
                    messageBuilder.setPhone(number);
                }
            }
        });
    }

    private void initialize_dialogs() {
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateBuilder.set(year, monthOfYear, dayOfMonth);

                dateText.setText(new SimpleDateFormat("MMMM dd", Locale.US).format(dateBuilder.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String am_pm = (hourOfDay > 12) ? "pm" : "am";
                if(am_pm.equals("pm")) hourOfDay -= 12;
                String str_minute = (minute == 0) ?  "00" : Integer.toString(minute);;

                dateBuilder.set(Calendar.HOUR_OF_DAY, hourOfDay);
                dateBuilder.set(Calendar.MINUTE, minute);

                timeText.setText(hourOfDay + ":" + str_minute + " " + am_pm) ;
            }
        }, newCalendar.get(Calendar.HOUR), newCalendar.get(Calendar.MINUTE), false);

    }

    private void set_fields() {
        dateText = (EditText) findViewById(R.id.new_text_date);
        dateText.setInputType(InputType.TYPE_NULL);

        timeText = (EditText) findViewById(R.id.new_text_time);
        timeText.setInputType(InputType.TYPE_NULL);

        phoneField = (EditText) findViewById(R.id.new_text_phone);
        contactField = (EditText) findViewById((R.id.new_text_recipient));
        messageField = (EditText) findViewById(R.id.new_text_message);

        conactsListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
        contactsListView = (ListView) findViewById(R.id.contact_view);

        contactsListView.setVisibility(View.GONE);
        contactsListView.setAdapter(conactsListAdapter);
    }

    public void createTextMessage(View view) {
        messageBuilder.setMessage(messageField.getText().toString());
        messageBuilder.setDate(new SimpleDateFormat("MMMM dd (EEEE) 'at' h:m a", Locale.US).format(dateBuilder.getTime()));

        try {
            MessagesDB db = new MessagesDB(this);
            TextMessage textMessage = messageBuilder.build();
            db.addTextMessage(textMessage);

            Toast.makeText(this, "Your message has been scheduled!", Toast.LENGTH_SHORT).show();

            new SMSScheduler().schedule(this, textMessage, dateBuilder);

            Intent viewAllMessagesIntent = new Intent(this, MainActivity.class);
            this.startActivity(viewAllMessagesIntent);

        } catch (IncompleteTextMessageException e) {
            Toast.makeText(this, "Please fill out all of the fields.", Toast.LENGTH_SHORT).show();
        }
    }
}
