package com.example.proekt_10;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        EditText nameInput = findViewById(R.id.name_input);
        EditText phoneInput = findViewById(R.id.phone_input);
        Button buttonSave = findViewById(R.id.buttonSave);
        Button buttonDel = findViewById(R.id.buttonDel);
        Button findButton = findViewById(R.id.buttonFind);
        RecyclerView contactList = findViewById(R.id.contact_list);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Contact> contacts = dbHelper.getAllContacts();
        ContactAdapter adapter = new ContactAdapter(contacts);
        contactList.setLayoutManager(new LinearLayoutManager(this));
        contactList.setAdapter(adapter);

        buttonSave.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String phone = phoneInput.getText().toString();
            if (dbHelper.addContact(new Contact(0, name, phone))) {
                contacts.add(new Contact(0, name, phone));
                adapter.notifyItemInserted(contacts.size() - 1);
                Toast.makeText(this, "Contact saved successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to save contact", Toast.LENGTH_LONG).show();
            }
        });

        buttonDel.setOnClickListener(v -> {
            String phone = phoneInput.getText().toString();
            if (dbHelper.deleteContact(phone)) {
                int position = -1;
                for (int i = 0; i < contacts.size(); i++) {
                    if (contacts.get(i).getPhone().equals(phone)) {
                        position = i;
                        contacts.remove(i);
                        break;
                    }
                }
                if (position != -1) {
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(this, "Contact deleted successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Contact not found", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Failed to delete contact", Toast.LENGTH_LONG).show();
            }
        });

        findButton.setOnClickListener(v -> {
            String phone = phoneInput.getText().toString();
            Contact foundContact = dbHelper.findContact(phone);
            if (foundContact != null) {
                nameInput.setText(foundContact.getName());
                phoneInput.setText(foundContact.getPhone());
                Toast.makeText(this, "Contact found: " + foundContact.getName(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Contact not found", Toast.LENGTH_LONG).show();
            }
        });

        Button updateButton = findViewById(R.id.buttonUpdate);
        updateButton.setOnClickListener(v -> {
            String oldPhone = phoneInput.getText().toString();
            String newName = nameInput.getText().toString();
            String newPhone = phoneInput.getText().toString();

            if (dbHelper.updateContact(oldPhone, newName, newPhone)) {
                Toast.makeText(this, "Contact update successfully", Toast.LENGTH_LONG).show();
                refreshContactsList(dbHelper, contacts, adapter, contactList);
            } else {
                Toast.makeText(this, "Failed to update contact", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void refreshContactsList(DatabaseHelper dbHelper, List<Contact> contacts, ContactAdapter adapter, RecyclerView contactsList) {
        contacts = dbHelper.getAllContacts();
        adapter = new ContactAdapter (contacts);
        contactsList.setAdapter(adapter);
    }
}