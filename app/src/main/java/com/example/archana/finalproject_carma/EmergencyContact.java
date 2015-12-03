package com.example.archana.finalproject_carma;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class EmergencyContact extends ListActivity {
    List<String> contactList = new ArrayList<String>();
    ArrayAdapter myAdaptor = null;
    HashMap<String,String> ec = new HashMap<String,String>();
    int checkedCounter = 1;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferencesCheckedContacts;
    SharedPreferences phoneNos;
    SharedPreferences.Editor editor ;
    SharedPreferences.Editor editorCheckedContacts;


    SharedPreferences.Editor phoneNoEditor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);
       // setContentView(R.layout.activity_list_phone_apps);

        ActionBar actionBar;
        actionBar = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(
                Color.parseColor("#9999FF"));
        actionBar.setBackgroundDrawable(colorDrawable);


        String result = String.valueOf(new LoadApplications().execute());
        sharedPreferencesCheckedContacts = getApplicationContext()
                .getSharedPreferences("noOfCheckedContacts", Context.MODE_PRIVATE);
        editorCheckedContacts = sharedPreferencesCheckedContacts.edit();
        checkedCounter = Integer.parseInt(sharedPreferencesCheckedContacts.getString("noOfCheckedContacts","1"));
        ListView listView = getListView();
        listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);
        listView.setTextFilterEnabled(true);

        myAdaptor = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked,contactList);



    }

    private void setCheckedContacts() {

       sharedPreferences = getApplicationContext()
                .getSharedPreferences("emerContactNewFinal", Context.MODE_PRIVATE);
       phoneNos = getApplicationContext()
                .getSharedPreferences("phoneNosFinal", Context.MODE_PRIVATE);

        phoneNoEditor  = phoneNos.edit();
        int pos = 0;
        for(String s : contactList){
           if(sharedPreferences.getBoolean(s,false)){
               Log.d("Checking True Names",s);
               getListView().setItemChecked(pos,true);
             /*  phoneNoEditor.putString(s, ec.get(s));
               phoneNoEditor.commit();*/
           }
            pos++;
        }

      /*  editor.clear();
        phoneNoEditor.clear();
        phoneNoEditor.commit();
        editor.commit();*/
    }


    public void onListItemClick(ListView parent, View v,int position,long id){
        CheckedTextView item = (CheckedTextView) v;


         sharedPreferences = getApplicationContext()
                .getSharedPreferences("emerContactNewFinal", Context.MODE_PRIVATE);
         editor = sharedPreferences.edit();

        sharedPreferencesCheckedContacts = getApplicationContext()
                .getSharedPreferences("noOfCheckedContacts", Context.MODE_PRIVATE);
        editorCheckedContacts = sharedPreferencesCheckedContacts.edit();


        if ( checkedCounter == 3 && item.isChecked())
           {
               Log.d("at 5 contacts",String.valueOf(item.getText())+"");
               getListView().setItemChecked(position, false);
               Toast.makeText(EmergencyContact.this, "Please limit the emergency contacts to 3", Toast.LENGTH_SHORT).show();
           }
          else if(!item.isChecked()){
            Log.d("at removing tick",String.valueOf(item.getText())+"");
            editor.putBoolean(String.valueOf(item.getText()),false);
            editor.commit();

            phoneNoEditor.remove(String.valueOf(item.getText()));
            phoneNoEditor.commit();

            checkedCounter--;
        }
         else if(item.isChecked()){
            Log.d("at ticking",String.valueOf(item.getText())+"");
            checkedCounter++;
            //Log.d("checkedCounter",String.valueOf(item.getText())+"");
            editor.putBoolean(String.valueOf(item.getText()),true);
            editor.commit();

            phoneNoEditor.putString(String.valueOf(item.getText()),ec.get(String.valueOf(item.getText())));
            phoneNoEditor.commit();

        }

        editorCheckedContacts.putString("noOfCheckedContacts",checkedCounter+"");
        editorCheckedContacts.commit();
    }

   /* public void updateBlockedAppList(View view){
        for(String s : blockedApps)
            Log.d("BlockedApps",s);
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_emergency_contact, menu);
        return true;
    }




    private static final String[] PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;


        @Override

        protected Void doInBackground(Void... params) {
            ContentResolver cr = getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);

            Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                try {
                    final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                    String name, number;
                    while (cursor.moveToNext()) {
                        name = cursor.getString(nameIndex);
                        number = cursor.getString(numberIndex);

                        if(!contactList.contains(name))
                        {
                            contactList.add(name);
                            ec.put(name,number);
                        }
                    }

                } finally {
                    cursor.close();
                }
            }

          /*  if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));



                    if (Integer.parseInt(cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            if(!contactList.contains(name))
                            {
                                contactList.add(name);
                                ec.put(name,phoneNo);
                            }
                            //Toast.makeText(EmergencyContact.this, "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                        }
                        pCur.close();
                    }
                }
            }*/

            Collections.sort(contactList);

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("OMGGGGGGGGGGGGGGGGg","haha");
            setListAdapter(myAdaptor);
            setCheckedContacts();
            //getListView().setChoiceMode(getListView().CHOICE_MODE_MULTIPLE);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(EmergencyContact.this, null,
                    "Loading contacts from your phone ...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
