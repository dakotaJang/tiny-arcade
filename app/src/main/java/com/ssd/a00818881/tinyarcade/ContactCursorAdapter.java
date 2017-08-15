package com.ssd.a00818881.tinyarcade;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by dakotajang on 2017-02-24.
 */

public class ContactCursorAdapter extends CursorAdapter {

    @SuppressWarnings("deprecation")
    public ContactCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // When the view will be created for first time,
        // we need to tell the adapters, how each item will look.
        // Uses an UI Fragment to inflate the View.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ContactFragment m = new ContactFragment();
        View retView = m.onCreateView(inflater,parent,null);
        m.id = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(0)));

        return retView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        /* Here we are setting our data by taking it from the cursor and
         * putting it in textViews defined in the fragment single_row_item.xml
         */
        TextView contactFirstNameTextView = (TextView) view.findViewById(R.id.contact_first_name);
        TextView contactLastNameTextView = (TextView) view.findViewById(R.id.contact_last_name);
        contactFirstNameTextView.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
        contactLastNameTextView.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
    }
}
