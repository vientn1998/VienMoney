package com.tnv.moneymanager.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.tnv.moneymanager.model.Bill;
import com.tnv.moneymanager.model.Event;
import com.tnv.moneymanager.model.Menu;
import com.tnv.moneymanager.model.MenuItem;
import com.tnv.moneymanager.model.User;
import com.tnv.moneymanager.utils.Converters;

@Database(entities = {User.class, Menu.class, MenuItem.class, Bill.class, Event.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "APP_MONEY_DATABASE";
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                }
                            })
                            .build();
                }
            }
        }

        return INSTANCE;
    }
    public abstract UserDao userDao();
    public abstract MenuDao menuDao();
    public abstract MenuItemDao menuItemDao();
    public abstract BillDao billDao();
    public static void destroyInstance() {
        INSTANCE = null;
    }
}
