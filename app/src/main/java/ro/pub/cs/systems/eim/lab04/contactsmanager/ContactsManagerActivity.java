package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.icu.text.SearchIterator;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class ContactsManagerActivity extends AppCompatActivity {

    Button SaveButton;
    Button CancelButton;
    Button AddInfoButton;
    EditText Nume;
    EditText NrTel;
    EditText AdrEl;
    EditText AdrPostala;
    EditText Pozitie;
    EditText Companie;
    EditText SiteWeb;
    EditText IdMsg;
    LinearLayout SecondLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        SaveButton = (Button)findViewById(R.id.Save);
        SaveButton.setOnClickListener(listen);
        CancelButton = (Button)findViewById(R.id.Cancel);
        CancelButton.setOnClickListener(listen);
        AddInfoButton = (Button)findViewById(R.id.MoreInfo);
        AddInfoButton.setOnClickListener(listen);

        AddInfoButton.setText("Hide");

        Nume = (EditText) findViewById(R.id.Nume);
        NrTel = (EditText) findViewById(R.id.NrTel);
        AdrEl = (EditText) findViewById(R.id.AdrEl);
        AdrPostala = (EditText) findViewById(R.id.AdrPostala);
        Pozitie = (EditText) findViewById(R.id.Pozitie);
        Companie = (EditText) findViewById(R.id.Companie);
        SiteWeb = (EditText) findViewById(R.id.Site);
        IdMsg = (EditText) findViewById(R.id.IdMsg);

        SecondLayout = (LinearLayout) findViewById(R.id.SecondLayout);
    }

    Listen listen = new Listen();

    class Listen implements OnClickListener
    {

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.MoreInfo)
            {
                if(SecondLayout.getVisibility() == View.INVISIBLE)
                {
                    AddInfoButton.setText("Hide");
                    SecondLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    AddInfoButton.setText("Show More");
                    SecondLayout.setVisibility(View.INVISIBLE);
                }
            }

            if(view.getId() == R.id.Cancel)
            {
                finish();
            }

            if(view.getId() == R.id.Save)
            {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                if (Nume != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, (Parcelable) Nume);
                }
                if (NrTel != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, (Parcelable) NrTel);
                }
                if (AdrEl != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, (Parcelable) AdrEl);
                }
                if (AdrPostala != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, (Parcelable) AdrPostala);
                }
                if (Pozitie != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, (Parcelable) Pozitie);
                }
                if (Companie != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, (Parcelable) Companie);
                }
                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                if (SiteWeb != null) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(URL, String.valueOf(SiteWeb));
                    contactData.add(websiteRow);
                }
                if (IdMsg != null) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, String.valueOf(IdMsg));
                    contactData.add(imRow);
                }
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                startActivity(intent);
            }

        }
    }
}
