package com.algonquincollege.hurdleg.caesarcipher;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import util.CaesarCipher;

import static com.algonquincollege.hurdleg.caesarcipher.Constants.ABOUT_DIALOG_TAG;
import static com.algonquincollege.hurdleg.caesarcipher.Constants.DEBUG;
import static com.algonquincollege.hurdleg.caesarcipher.Constants.LOG_TAG;
import static com.algonquincollege.hurdleg.caesarcipher.Constants.ROT13;
import static com.algonquincollege.hurdleg.caesarcipher.Constants.ROTATIONS;
import static com.algonquincollege.hurdleg.caesarcipher.Constants.THE_MESSAGE;
import static com.algonquincollege.hurdleg.caesarcipher.Constants.THE_ROTATION;

/**
 * EncryptedTextActivity
 *
 * Display the encrypted message to the user.
 * Call-to-action: de-crypt
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 */
public class EncryptedTextActivity extends AppCompatActivity {
    private DialogFragment        mAboutDialog;
    private EditText              mEncryptedText;
    private int                   mRotation;
    private CharacterPickerDialog mRotationsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypted_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String encryptedText = mEncryptedText.getText().toString();
                Intent intent = new Intent( getApplicationContext(), PlainTextActivity.class );
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                intent.putExtra( THE_MESSAGE, encryptedText );
                intent.putExtra( THE_ROTATION, mRotation );
                startActivity( intent );
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAboutDialog = new AboutDialogFragment();
        // Default rotation.
        mRotation = ROT13;

        mRotationsDialog = new CharacterPickerDialog(this, new View(this), null, ROTATIONS, false) {
            @Override
            public void onClick(View v) {
                int index = ROTATIONS.indexOf( ((Button)v).getText().toString() );
                if ( index >= 0 ) {
                    mRotation = index;
                }
                dismiss();
            }
        };

        Button clearButton = (Button) findViewById( R.id.bClearEncrypted );
        clearButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEncryptedText.setText( "" );
            }
        });

        mEncryptedText = (EditText) findViewById( R.id.etEncrypted );
        mEncryptedText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //NO-OP
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //NO-OP
            }

            @Override
            public void afterTextChanged(Editable s) {
                fab.setEnabled(s.length() != 0);
            }
        });

        // Get the bundle of extras that was sent to this activity
        Bundle bundle = getIntent().getExtras();
        if ( bundle != null ) {
            String plainMessage = bundle.getString( THE_MESSAGE );
            int rotation = bundle.getInt( THE_ROTATION );
            if ( DEBUG ) {
                Log.i( LOG_TAG, "Plain Message (rotation: " + rotation + "): " + plainMessage );
            }

            mEncryptedText.setText( 0 );
           // mEncryptedText.setText( CaesarCipher.encrypt(plainMessage, rotation) );
        }

        // init
        fab.setEnabled( mEncryptedText.length() != 0 );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if ( id == R.id.action_about) {
            mAboutDialog.show( getFragmentManager(), ABOUT_DIALOG_TAG );
            return true;
        }

        if (id == R.id.action_settings) {
            mRotationsDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
