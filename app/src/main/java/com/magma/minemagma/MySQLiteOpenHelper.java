package com.magma.minemagma;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by apple on 9/22/16.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="MagmaDB";

    private static final String TABLE_USERS="PDFs";

    public static final String ID = "id";
    private static final String NAME="name";
    private static final String FILE="pdfText";



    private static final String COLUMNS[]={ID,NAME};



    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String CREATE_PDF_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT,"
                + FILE + " TEXT" + ")";
        db.execSQL(CREATE_PDF_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    public Boolean addUser(User user){
        Log.d("addUser", user.toString());

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        //values.put(ID, user.getId());
        values.put(NAME, user.getName());
        values.put(FILE, user.getPdfText());

        db.insert(TABLE_USERS, null, values);

        db.close();

        return true;

    }

    public User getUser(int id)
    {
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] { ID,
                        NAME, FILE }, ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);


        if (cursor != null)
            cursor.moveToFirst();



        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));


        return user;
    }

    public List<User> getAllUsers(){
        List<User> users=new LinkedList<User>();

        String query="SELECT * FROM "+TABLE_USERS;

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor= db.rawQuery(query, null);

        User user=null;
        if (cursor.moveToFirst()){
            do{
                user=new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setPdfText(cursor.getString(2));

                users.add(user);
            } while (cursor.moveToNext());
        }

        Log.d("getAllUsers", users.toString());

        return users;
    }

    public void deleteUser(User user){
        SQLiteDatabase db=this.getWritableDatabase();

        db.delete(TABLE_USERS, "Id =?", new String[]{String.valueOf(user.getId())});

        db.close();

        Log.d("deleteUser", user.toString());
    }

    //Getting Users Count
    public int getUserCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    public String Exists(String searchItem) {


            String username="";
            SQLiteDatabase db = this.getReadableDatabase();

            try {
                Cursor c = db.query(TABLE_USERS, null, NAME + "=?", new String[]{String.valueOf(searchItem)},null, null, null);

                if (c == null) {
                    return username;
                }
                else {
                    c.moveToFirst();
                    username = c.getString(c.getColumnIndex(NAME));
                }
            }

            catch(Exception e){
                e.printStackTrace();
            }

            return username;
    }


}
