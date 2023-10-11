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
    public DataBaseHelper(@Nullable Context context) {
        super(context, "Signup.db",null,1);
    }

    public static final String databaseName = "Signup.db";
    public static final String logindb = "login.db";

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("CREATE TABLE allusers(nic_no TEXT PRIMARY KEY, email TEXT, password TEXT, status TEXT DEFAULT 'pending')");

        MyDatabase.execSQL("CREATE TABLE " + TABLE_AUTH_TOKEN + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TOKEN + " TEXT)");
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

    public boolean saveAuthToken(String authToken) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOKEN, authToken);
        long result = db.insert(TABLE_AUTH_TOKEN, null, values);
        db.close();
        return result != -1;
    }

    // Add a method to retrieve the authentication token
    public String getAuthToken() {
        SQLiteDatabase db = this.getReadableDatabase();
        String token = null;
        Cursor cursor = db.query(
                TABLE_AUTH_TOKEN,
                new String[]{COLUMN_TOKEN},
                null,
                null,
                null,
                null,
                null
        );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(COLUMN_TOKEN);
                if (columnIndex != -1) {
                    token = cursor.getString(columnIndex);
                }
            }
            cursor.close();
        }
        db.close();
        return token;
    }
    public String getAuthTokenByUsername(String username) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT auth_token FROM allusers WHERE username = ?", new String[]{username});

        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        } else {
            return null;
        }
    }


}

