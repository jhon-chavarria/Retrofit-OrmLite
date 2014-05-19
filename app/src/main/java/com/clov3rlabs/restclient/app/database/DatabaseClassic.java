package com.clov3rlabs.restclient.app.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** SQLite Adapter 
 *  
 * 
 * Database Structure
 * 
 * 
 * */
public class DatabaseClassic {

	public  SQLiteDatabase sqlDatabase;
	private  Context context;
    private static DatabaseHelper articleDBHelper;
    static int dbVersion = 1;
	/**  Database Name **/
	public static final String dbName = "test2";

	/**  Table Names **/
	public static final String ARTICLE_TABLE = "articles";


    /** DATABASE Schema **/
	private static final String ARTICLES =
			"CREATE TABLE IF NOT EXISTS "
                    + ARTICLE_TABLE
                    + " (id INT PRIMARY KEY, "
                    + " title VARCHAR(250),"
                    + " section varchar(250));";


	public DatabaseClassic(Context context) {
		this.context = context;
	}


    @SuppressWarnings("deprecation")
	public synchronized DatabaseClassic open() {
		articleDBHelper = new DatabaseHelper(context);
		sqlDatabase = articleDBHelper.getWritableDatabase();
		sqlDatabase.setLockingEnabled(true);
		return this;
	}
	
	public void close() {
		articleDBHelper.close();	
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		private Context mCxt;
		private DatabaseHelper(Context context) {
			super(context, dbName, null, dbVersion);
	        this.mCxt = context;
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d("END", "Creando tablas :" + ARTICLES);
			db.execSQL(ARTICLES);
		}

		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d("END", "Upgrading database from version " + oldVersion
					+ " to " + newVersion + "");
			if (newVersion > oldVersion) {
				
				 db.execSQL("DROP TABLE IF EXISTS "+ARTICLE_TABLE);
				 
			     onCreate(db);
	        }
		}

	}	
	
	/** Check database content **/
	public Cursor  CheckDatabase() {
		Log.d("END", "Checking if articles table contains rows");
		return sqlDatabase.rawQuery("SELECT * FROM articles", null);
	}

	/** Delete Data **/
	public void deleteAllArticles() {
		Log.d("END", "Eliminando todos los articulos y sus comentarios");
		 sqlDatabase.delete(ARTICLE_TABLE,null,null);
	}
    public long saveArticle(String idarticle,String title,String section)  {
        Log.d("END", "Saving Bookmark , Article: " + title);
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", idarticle);
        contentValues.put("title",title);
        contentValues.put("section", section);

        return sqlDatabase.insert(ARTICLE_TABLE, null, contentValues);
    }
}
