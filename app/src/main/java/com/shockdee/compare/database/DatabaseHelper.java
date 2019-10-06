package com.shockdee.compare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shockdee.compare.models.Brand;
import com.shockdee.compare.models.Photo;
import com.shockdee.compare.models.Store;
import com.shockdee.compare.models.Type;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Comparatif.db";

    public static final int DATABASE_VERSION = 2;

    //Table Photo
    public static final String TABLE_PHOTO = "Photo";

    public static final String COL_PHOTO_ID = "Photo_ID";
    public static final String COL_PHOTO_NAME = "Photo_Name";
    public static final String COL_PHOTO_PRODUCT = "Photo_Product";
    public static final String COL_PHOTO_TYPE = "Photo_Type";
    public static final String COL_PHOTO_BRAND = "Photo_Brand";
    public static final String COL_PHOTO_STORE = "Photo_Store";
    public static final String COL_PHOTO_DATE = "Photo_Date";
    public static final String COL_PHOTO_PRICE = "Photo_Price";
    public static final String COL_PHOTO_DESCRIPTION = "Description";
    public static final String COL_PHOTO_PATH = "Path";

    public static final String CREATE_TABLE_PHOTO = "CREATE TABLE "
            + TABLE_PHOTO
            + "("
            + COL_PHOTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_PHOTO_NAME + " TEXT, "
            + COL_PHOTO_PRODUCT + " TEXT, "
            + COL_PHOTO_TYPE + " TEXT, "
            + COL_PHOTO_BRAND + " TEXT, "
            + COL_PHOTO_STORE + " TEXT, "
            + COL_PHOTO_DATE + " TEXT, "
            + COL_PHOTO_PRICE + " DOUBLE, "
            + COL_PHOTO_DESCRIPTION + " TEXT, "
            + COL_PHOTO_PATH + " TEXT"
            + ")";

    //Table Brand
    public static final String TABLE_BRAND = "Brand";

    public static final String COL_BRAND_ID = "Brand_ID";
    public static final String COL_BRAND_NAME = "Brand_Name";

    public static final String CREATE_TABLE_BRAND = "CREATE TABLE "
            + TABLE_BRAND
            + "("
            + COL_BRAND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_BRAND_NAME + " TEXT"
            + ")";

    //Table Store
    public static final String TABLE_STORE = "Store";

    public static final String COL_STORE_ID = "Store_ID";
    public static final String COL_STORE_NAME = "Store_Name";

    public static final String CREATE_TABLE_STORE = "CREATE TABLE "
            + TABLE_STORE
            + "("
            + COL_STORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_STORE_NAME + " TEXT NOT NULL"
            + ")";

    //Table Type
    public static final String TABLE_TYPE = "Type";


    public static final String COL_TYPE_ID = "Type_ID";
    public static final String COL_TYPE_NAME = "Type_Name";

    public static final String CREATE_TABLE_TYPE = "CREATE TABLE "
            + TABLE_TYPE
            + "("
            + COL_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_TYPE_NAME + " TEXT NOT NULL"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PHOTO);
        db.execSQL(CREATE_TABLE_BRAND);
        db.execSQL(CREATE_TABLE_TYPE);
        db.execSQL(CREATE_TABLE_STORE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRAND);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE);
        onCreate(db);
    }

    /**
     * ================================Add data in the DB===========================================
     */

    //Table Photo
    public boolean insertPhoto(Photo photo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PHOTO_NAME, photo.photoName);
        contentValues.put(COL_PHOTO_PRODUCT, photo.photoProductName);
        contentValues.put(COL_PHOTO_TYPE, photo.photoProductType);
        contentValues.put(COL_PHOTO_BRAND, photo.photoBrand);
        contentValues.put(COL_PHOTO_STORE, photo.photoStore);
        contentValues.put(COL_PHOTO_DATE, photo.photoDate);
        contentValues.put(COL_PHOTO_PRICE, photo.photoPrice);
        contentValues.put(COL_PHOTO_DESCRIPTION, photo.photoDescription);
        contentValues.put(COL_PHOTO_PATH, photo.photoPath);

        long result = db.insert(TABLE_PHOTO, null, contentValues);
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    //Table Brand
    public boolean insertBrand(Brand brand) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_BRAND_NAME, brand.brandName);
        long result = db.insert(TABLE_BRAND, null, contentValues);
        db.close();
        if (result == -1)
            return false;
        else
            return true;


    }

    //Table Brand
    public boolean insertStore(Store store) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_STORE_NAME, store.storeName);
        long result = db.insert(TABLE_STORE, null, contentValues);
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }


    //Table Type
    public boolean insertType(Type type) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TYPE_NAME, type.typeName);
        long result = db.insert(TABLE_TYPE, null, contentValues);
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }


    /**
     * ==================================Retrieve from DB===========================================
     */
    //Table Photo
    public ArrayList<Photo> selectAllPhoto() {
        ArrayList<Photo> photoArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PHOTO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Photo photo = new Photo();
                photo.photoId = cursor.getInt(cursor.getColumnIndex(COL_PHOTO_ID));
                photo.photoName = cursor.getString(cursor.getColumnIndex(COL_PHOTO_NAME));
                photo.photoProductName = cursor.getString(cursor.getColumnIndex(COL_PHOTO_PRODUCT));
                photo.photoProductType = cursor.getString(cursor.getColumnIndex(COL_PHOTO_TYPE));
                photo.photoBrand = cursor.getString(cursor.getColumnIndex(COL_PHOTO_BRAND));
                photo.photoStore = cursor.getString(cursor.getColumnIndex(COL_PHOTO_STORE));
                photo.photoDate = cursor.getString(cursor.getColumnIndex(COL_PHOTO_DATE));
                photo.photoPrice = cursor.getDouble(cursor.getColumnIndex(COL_PHOTO_PRICE));
                photo.photoDescription = cursor.getString(cursor.getColumnIndex(COL_PHOTO_DESCRIPTION));
                photo.photoPath = cursor.getString(cursor.getColumnIndex(COL_PHOTO_PATH));

                photoArrayList.add(photo);
            } while (cursor.moveToNext());
        }
        db.close();
        return photoArrayList;
    }

    //Table Brand
    public ArrayList<Brand> selectAllBrand() {
        ArrayList<Brand> brandArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_BRAND;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Brand brand = new Brand();
                brand.brandID = cursor.getInt(cursor.getColumnIndex(COL_BRAND_ID));
                brand.brandName = cursor.getString(cursor.getColumnIndex(COL_BRAND_NAME));
                brandArrayList.add(brand);
            } while (cursor.moveToNext());
        }
        db.close();
        return brandArrayList;
    }

    //Table Store
    public ArrayList<Store> selectAllStore() {
        ArrayList<Store> storeArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STORE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Store store = new Store();
                store.storeId = cursor.getInt(cursor.getColumnIndex(COL_STORE_ID));
                store.storeName = cursor.getString(cursor.getColumnIndex(COL_STORE_NAME));
                storeArrayList.add(store);
            } while (cursor.moveToNext());
        }
        db.close();
        return storeArrayList;
    }

    //Table Type
    public ArrayList<Type> selectAllType() {
        ArrayList<Type> typeArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TYPE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Type type = new Type();
                type.typeID = cursor.getInt(cursor.getColumnIndex(COL_TYPE_ID));
                type.typeName = cursor.getString(cursor.getColumnIndex(COL_TYPE_NAME));
                typeArrayList.add(type);
            } while (cursor.moveToNext());
        }
        db.close();
        return typeArrayList;
    }

    /**
     * ===================================Spinner adapter===========================================
     */

    public Cursor spCursorStore() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_STORE_ID, COL_STORE_NAME};
        return db.query(TABLE_STORE, columns, null, null, null, null, COL_STORE_NAME);
    }

    public Cursor spCursorType() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_TYPE_ID, COL_TYPE_NAME};
        return db.query(TABLE_TYPE, columns, null, null, null, null, COL_TYPE_NAME);
    }

    public Cursor spCursorBrand() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_BRAND_ID, COL_BRAND_NAME};
        return db.query(TABLE_BRAND, columns, null, null, null, null, COL_BRAND_NAME);
    }

    /**
     * ===============================Suppression d'un élément======================================
     */

    public void delPhoto(int idDatabase) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PHOTO, COL_PHOTO_ID + " =?", new String[]{String.valueOf(idDatabase)});
        db.close();
    }

    public boolean delBrand(int idDatabase) {
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            int result = db.delete(TABLE_BRAND, COL_BRAND_ID + " =?", new String[]{String.valueOf(idDatabase)});
            if (result > 0) {
                return true;
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean delType(int idDatabase) {
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            int result = db.delete(TABLE_TYPE, COL_TYPE_ID + " =?", new String[]{String.valueOf(idDatabase)});
            if (result > 0) {
                return true;
            }
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delStore(int idDatabase) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            int result = db.delete(TABLE_STORE, COL_STORE_ID + " =?", new String[]{String.valueOf(idDatabase)});
            if (result>0){
                return true;
            }
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }

    public Photo searchPhotoById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PHOTO, new String[]{COL_PHOTO_ID, COL_PHOTO_NAME, COL_PHOTO_PRODUCT, COL_PHOTO_TYPE, COL_PHOTO_BRAND, COL_PHOTO_STORE, COL_PHOTO_DATE, COL_PHOTO_PRICE, COL_PHOTO_DESCRIPTION, COL_PHOTO_PATH},
                COL_PHOTO_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Photo photo = null;
        if (cursor.moveToFirst()) {
            do {
                photo = new Photo();
                photo.photoId = cursor.getInt(cursor.getColumnIndex(COL_PHOTO_ID));
                photo.photoName = cursor.getString(cursor.getColumnIndex(COL_PHOTO_NAME));
                photo.photoProductName = cursor.getString(cursor.getColumnIndex(COL_PHOTO_PRODUCT));
                photo.photoProductType = cursor.getString(cursor.getColumnIndex(COL_PHOTO_TYPE));
                photo.photoBrand = cursor.getString(cursor.getColumnIndex(COL_PHOTO_BRAND));
                photo.photoStore = cursor.getString(cursor.getColumnIndex(COL_PHOTO_STORE));
                photo.photoDate = cursor.getString(cursor.getColumnIndex(COL_PHOTO_DATE));
                photo.photoPrice = cursor.getDouble(cursor.getColumnIndex(COL_PHOTO_PRICE));
                photo.photoDescription = cursor.getString(cursor.getColumnIndex(COL_PHOTO_DESCRIPTION));
                photo.photoPath = cursor.getString(cursor.getColumnIndex(COL_PHOTO_PATH));

            } while (cursor.moveToNext());

        }
        cursor.close();
        return photo;
    }

    //Modif
    public boolean updateStudent(int upPhotoId,
                                 String upPhotoName,
                                 String upPhotoProductName,
                                 String upPhotoProductType,
                                 String upPhotoBrand,
                                 String upPhotoStore,
                                 String upPhotoDate,
                                 Double upPhotoPrice,
                                 String upPhotoDescription,
                                 String upPhotoPath) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PHOTO_ID, upPhotoId);
        contentValues.put(COL_PHOTO_NAME, upPhotoName);
        contentValues.put(COL_PHOTO_PRODUCT, upPhotoProductName);
        contentValues.put(COL_PHOTO_TYPE, upPhotoProductType);
        contentValues.put(COL_PHOTO_BRAND, upPhotoBrand);
        contentValues.put(COL_PHOTO_STORE, upPhotoStore);
        contentValues.put(COL_PHOTO_DATE, upPhotoDate);
        contentValues.put(COL_PHOTO_PRICE, upPhotoPrice);
        contentValues.put(COL_PHOTO_DESCRIPTION, upPhotoDescription);
        contentValues.put(COL_PHOTO_PATH, upPhotoPath);

        db.update(TABLE_PHOTO, contentValues, "Photo_ID = ?", new String[]{String.valueOf(upPhotoId)});
        db.close();
        return true;
    }

}
