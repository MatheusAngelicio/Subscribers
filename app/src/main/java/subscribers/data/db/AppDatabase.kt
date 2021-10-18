package subscribers.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import subscribers.data.db.dao.SubscriberDAO
import subscribers.data.db.entity.SubscriberEntity

//This class exists because I want to instantiate my database only once and encompass the same
// database for the rest of my app.

// I must pass the list of entities that will have the db and version
@Database(entities = [SubscriberEntity::class], version = 1)
// Use abstract because I don't want to instantiate a class

abstract class AppDatabase : RoomDatabase() {

    abstract val subscriberDAO: SubscriberDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance: AppDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "app_database"
                    ).build()
                }

                return instance
            }
        }
    }


}