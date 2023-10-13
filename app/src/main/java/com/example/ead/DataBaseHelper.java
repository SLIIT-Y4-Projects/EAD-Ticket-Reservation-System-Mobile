package com.example.ead;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_AUTH_TOKEN = "auth_tokens";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TOKEN = "token";



    public static final String databaseName = "Signup.db";
    //public static final String loginDb = "login.db";
    public static final String loginData= "user_auth";

    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {

        //super(context,databaseName , null, DATABASE_VERSION);
        super(context,loginData , null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("CREATE TABLE allusers(nic_no TEXT PRIMARY KEY, email TEXT, password TEXT, status TEXT DEFAULT 'pending')");
        MyDatabase.execSQL("CREATE TABLE user_auth(id1 TEXT,token TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("drop Table if exists allusers");
    }
    public boolean insertData(String nic_no,String email,String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nic_no", nic_no);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("status", "pending");

        long result = MyDatabase.insert( "allusers" , null, contentValues) ;
        if(result == -1){
            return false;

        }else {
            return true;
        }
    }
    public boolean checkNIC(String nic) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM allusers WHERE nic_no = ?", new String[]{nic});

        return cursor.getCount() > 0;
    }
    public boolean isNICValid(String nic) {
        // Remove spaces and convert to uppercase
        nic = nic.replaceAll("\\s", "").toUpperCase();

        // Check the length of the NIC
        if (nic.length() != 10 && nic.length() != 12) {
            return false;
        }

        // Check if it is a new format NIC
        if (nic.length() == 12) {
            // New format NIC: 123456789012
            // Check if the first 9 characters are digits
            if (!nic.substring(0, 9).matches("\\d{9}")) {
                return false;
            }

            // Check if the 10th character is a 'V' or 'X'
            char tenthChar = nic.charAt(9);
            if (tenthChar != 'V' && tenthChar != 'X') {
                return false;
            }

            // Calculate the checksum
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                int digit = Character.getNumericValue(nic.charAt(i));
                sum += digit * (9 - i);
            }

            char[] checkChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'V'};
            char expectedCheckChar = checkChars[sum % 30];

            return tenthChar == expectedCheckChar;
        } else if (nic.length() == 10) {
            // Old format NIC: 123456789V
            // Check if the first 9 characters are digits
            if (!nic.substring(0, 9).matches("\\d{9}")) {
                return false;
            }

            // Check if the 10th character is a 'V' or 'X'
            char tenthChar = nic.charAt(9);
            return tenthChar == 'V' || tenthChar == 'X';
        }

        return false;
    }
    public String getAccountStatus(String email) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT status FROM allusers WHERE email = ?", new String[]{email});

        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        } else {
            return null;
        }
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from allusers where email = ?",new String[]{email});

        if (cursor.getCount()>0) {
        return true;
        }
        else {
            return false;
        }
        }
        public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from allusers where email = ? and password = ?",new String[]{email,password});

        if(cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }
        }


    public boolean insertLogin(String id,String token){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id1", id);
        contentValues.put("token", token);


        long result = MyDatabase.insert( loginData , null, contentValues) ;
        if(result == -1){
            return false;

        }else {
            return true;
        }
    }
    public boolean deleteLoginByID(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the WHERE clause to specify which rows to delete based on the "id1" column
        String whereClause = "id1 = ?";
        String[] whereArgs = {id};

        // Delete the rows that match the "id1" value
        int rowsDeleted = db.delete("loginData", whereClause, whereArgs);

        db.close();

        // Check if any rows were deleted (returns the number of rows deleted)
        return rowsDeleted > 0;
    }






}

