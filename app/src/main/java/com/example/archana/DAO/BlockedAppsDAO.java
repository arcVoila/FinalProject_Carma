package com.example.archana.DAO;

/**
 * Created by Archana on 11/19/2015.
 */



    import java.util.ArrayList;
    import java.util.List;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.SQLException;
    import android.database.sqlite.SQLiteDatabase;
    import android.util.Log;

    import com.example.archana.POJO.BlockedAppsPOJO;
    import com.example.archana.dataAccess.BlockedAppsHelper;

public class BlockedAppsDAO {

        // Database fields
        private SQLiteDatabase database;
        private BlockedAppsHelper dbHelper;

        private String[] allColumns = { BlockedAppsHelper.PRIMARY_KEY,
                BlockedAppsHelper.APP_NAME,BlockedAppsHelper.IS_BLOCKED };

        public BlockedAppsDAO(Context context) {
            dbHelper = new BlockedAppsHelper(context);
        }

        public void open() throws SQLException {
            Log.d("IN OPEN","HURRRAYYYYY");
            database = dbHelper.getWritableDatabase();
           // dbHelper.onCreate(database);
        }

        public void close() {
            dbHelper.close();
        }

        public void saveBAs(List<BlockedAppsPOJO> bas) {
           // dbHelper.delete(database);
            for(BlockedAppsPOJO ba : bas) {
                ContentValues values = new ContentValues();
                values.put(BlockedAppsHelper.APP_NAME, ba.getBlockedAppName());
                values.put(BlockedAppsHelper.IS_BLOCKED, ba.getBlocked());
                long insertId = database.insert(BlockedAppsHelper.TABLE_BLOCKEDAPPS, null,
                        values);
            }
        }

        public List<BlockedAppsPOJO> getAllBAs() {
            List<BlockedAppsPOJO> bas = new ArrayList<BlockedAppsPOJO>();
            String whereClause = BlockedAppsHelper.IS_BLOCKED+"=\"true\"";
            Cursor cursor = database.query
                    (BlockedAppsHelper.TABLE_BLOCKEDAPPS,
                    allColumns,whereClause, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                BlockedAppsPOJO app = cursorToBA(cursor);
                bas.add(app);
                cursor.moveToNext();
            }
            cursor.close();
            return bas;
        }

        private BlockedAppsPOJO cursorToBA(Cursor cursor) {
            BlockedAppsPOJO ba = new BlockedAppsPOJO(cursor.getString(1),cursor.getString(2));
          //  ba.setBlockedAppName(cursor.getString(0));
            return ba;
        }
    }


