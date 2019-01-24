package com.example.shruti.sqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DBHelper mydb;
    EditText editRegis, editName, editMarks,editID;
    Button addData,showData,updateData, delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb =new DBHelper ( this );

        editName = (EditText)findViewById ( R.id.editText2 );
        editMarks = (EditText) findViewById ( R.id.editText4 );
        editRegis= (EditText)findViewById ( R.id.editText3 );
        addData = (Button) findViewById ( R.id.button );
        showData = (Button)findViewById ( R.id.button2 );
        updateData = (Button) findViewById ( R.id.button3 );
        editID = (EditText) findViewById ( R.id.editText5 ) ;
        delete = (Button) findViewById ( R.id.button3 );

        setAddData (); //method called when button is clicked
        seeData ();
        setUpdateData ();
        DeleteData();
    }

    public void setAddData() {
        //to make button functional
        addData.setOnClickListener (
                new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {
                        boolean ifInserted = mydb.insertVal ( editName.getText ().toString (),
                                editRegis.getText ().toString (),
                                editMarks.getText ().toString () );
                        //to check if data was inserted
                        if (ifInserted == true)
                            Toast.makeText ( MainActivity.this, "Data Inserted", Toast.LENGTH_LONG ).show ();
                        else
                            Toast.makeText ( MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG ).show ();
                    }
                }
        );
    }

    public void seeData()
    {
        showData.setOnClickListener (
                new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {
                        Cursor res = mydb.getData();
                        if(res.getCount ()==0) {
                            //show message
                            showMessage ( "Error", "No Data Found" );
                            return;
                        }
                        StringBuffer buf = new StringBuffer (  );
                        while(res.moveToNext ())
                        {
                            buf.append ( "ID : "+res.getString ( 0 )+"\n" );
                            buf.append ( "Name : "+res.getString ( 1 )+"\n" );
                            buf.append ( "Registration No. : "+res.getString ( 2 )+"\n" );
                            buf.append ( "Marks : "+res.getString ( 3 )+"\n\n" );

                        }
                        //show data
                        showMessage ( "Data",buf.toString () );
                    }
                }
        );
    }

    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder ( this );
        builder.setCancelable ( true );
        builder.setTitle ( title );
        builder.setMessage ( message );
        builder.show ();
    }

    public void setUpdateData()
    {
        updateData.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                boolean isUpdated =  mydb.update ( editID.getText ().toString (),editName.getText ().toString (),editMarks.getText ().toString (),editRegis.getText ().toString () );

            if(isUpdated==true)
                Toast.makeText ( MainActivity.this, "Data Updated", Toast.LENGTH_LONG ).show ();
            else
                Toast.makeText ( MainActivity.this, "Data not Updated", Toast.LENGTH_LONG ).show ();
            }
        } );
    }

    public void DeleteData()
    {
        delete.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Integer deletedRows = mydb.delete(editID.getText ().toString ());
                if(deletedRows>0)
                    Toast.makeText ( MainActivity.this, "Data Deleted", Toast.LENGTH_LONG ).show ();
                else
                    Toast.makeText ( MainActivity.this, "Data not deleted", Toast.LENGTH_LONG ).show ();

            }
        } );
    }
}
