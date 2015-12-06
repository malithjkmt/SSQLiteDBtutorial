package tk.msquad.malith.ssqlitedbtutorial;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MySQLiteHelper dbHelper = new MySQLiteHelper(this);
        BookDAO bookDAO = new BookDAO(dbHelper);

        /**
         * CRUD Operations
         * */
        // add Books
        bookDAO.addBook(new Book("Android Application Development Cookbook", "Wei Meng Lee"));
        bookDAO.addBook(new Book("Android Programming: The Big Nerd Ranch Guide", "Bill Phillips and Brian Hardy"));
        bookDAO.addBook(new Book("Learn Android App Development", "Wallace Jackson"));

        // get all books
        List<Book> list = bookDAO.getAllBooks();

        // delete one book
        bookDAO.deleteBook(list.get(0));

        // get all books
        List<Book> list2 =  bookDAO.getAllBooks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
