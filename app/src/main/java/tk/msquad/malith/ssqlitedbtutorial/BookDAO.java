package tk.msquad.malith.ssqlitedbtutorial;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Malith on 12/6/2015.
 */
public class BookDAO {

    // Books table name
    private static final String TABLE_NAME = "books";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHOR = "author";

    private static final String[] COLUMNS = {KEY_ID,KEY_TITLE,KEY_AUTHOR};

    private MySQLiteHelper mySQLiteHelper;

    public BookDAO(MySQLiteHelper mySQLiteHelper) {
        this.mySQLiteHelper = mySQLiteHelper;
    }

    public void addBook(Book book){
        //for logging
        Log.d("addBook ", book.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = mySQLiteHelper.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, book.getTitle()); // get title
        values.put(KEY_AUTHOR, book.getAuthor()); // get author

        // 3. insert
        db.insert(TABLE_NAME, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Book getBook(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = mySQLiteHelper.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_NAME, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Book book = new Book();
        book.setId(Integer.parseInt(cursor.getString(0)));
        book.setTitle(cursor.getString(1));
        book.setAuthor(cursor.getString(2));

        //log
        Log.d("getBook(" + id + ")", book.toString());

        // 5.close the Cursor
        cursor.close();

        // 6. close db
        db.close();

        // 7. return book
        return book;
    }

    public List<Book> getAllBooks() {
        List<Book> books = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_NAME;

        // 2. get reference to readable DB
        SQLiteDatabase db = mySQLiteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Book book = null;
        if (cursor.moveToFirst()) {
            do {
                book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setTitle(cursor.getString(1));
                book.setAuthor(cursor.getString(2));

                // Add book to books
                books.add(book);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBooks()", books.toString());

        // close the Cursor
        cursor.close();

        // close db
        db.close();

        // return books
        return books;
    }

    public int updateBook(Book book) {

        // 1. get reference to writable DB
        SQLiteDatabase db = mySQLiteHelper.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("title", book.getTitle()); // get title
        values.put("author", book.getAuthor()); // get author

        // 3. updating row
        int i = db.update(TABLE_NAME, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(book.getId()) }); //selection args

        // 4. close
        db.close();

        return i;
    }

    public void deleteBook(Book book) {

        // 1. get reference to writable DB
        SQLiteDatabase db = mySQLiteHelper.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_NAME, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(book.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteBook", book.toString());

    }

}
