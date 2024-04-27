package com.example.proekt_10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private final List<Contact> contacts;

    public ContactAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView phoneTextView;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.contact_name);
            phoneTextView = view.findViewById(R.id.contact_phone);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public  void onBindViewHolder (ViewHolder holder, int position) {
        holder.phoneTextView.setText(contacts.get(position).getPhone());
        holder.nameTextView.setText(contacts.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
