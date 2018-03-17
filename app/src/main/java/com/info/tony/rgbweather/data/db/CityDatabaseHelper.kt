package com.info.tony.rgbweather.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.info.tony.rgbweather.R
import com.info.tony.rgbweather.WeatherApplication
import com.info.tony.rgbweather.util.DelegatesExt
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.support.ConnectionSource
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.sql.SQLException

/**
 * Created by lvlu on 2018/3/9.
 */
class CityDatabaseHelper: OrmLiteSqliteOpenHelper {

    companion object {
        private val DATABASE_NAME = "city.db"
        private val DATABASE_VERSION = 1
        fun instance(context: Context) : CityDatabaseHelper = Inner.innerSingle
    }

    object Inner{
        val innerSingle = CityDatabaseHelper(WeatherApplication.instance)
    }

   constructor(context: Context):super(context,DATABASE_NAME, null,DATABASE_VERSION)

    override fun onCreate(database: SQLiteDatabase, connectionSource: ConnectionSource) {
        //由于城市数据库是由外部导入的，故不需要创建执行创建表的操作
    }

    override fun onUpgrade(database: SQLiteDatabase, connectionSource: ConnectionSource, oldVersion: Int, newVersion: Int) {

    }


    override fun close() {
        super.close()
        DaoManager.clearCache()
    }


    fun <D : Dao<T, *>, T> getCityDao(clazz: Class<T>): D? {
        try {
            return getDao<D, T>(clazz)
        } catch (e: SQLException) {
            Log.e(this::class.simpleName, e.message)
        }

        return null
    }

    /**
     * 导入城市数据库
     */
    fun importCityDB() {
        Log.e("xxxxx","importCityDB")
        // 判断保持城市的数据库文件是否存在
        val file = File(WeatherApplication.instance.getDatabasePath(DATABASE_NAME).absolutePath)
        if (!file.exists()) {// 如果不存在，则导入数据库文件
            Log.e("xxxxx","import Db!")
            //数据库文件
            val dbFile = WeatherApplication.instance.getDatabasePath(DATABASE_NAME)
            try {
                if (!dbFile.parentFile.exists()) {
                    dbFile.parentFile.mkdir()
                }
                if (!dbFile.exists()) {
                    dbFile.createNewFile()
                }
                //加载欲导入的数据库
                val inputStream = WeatherApplication.instance.resources.openRawResource(R.raw.city)
                val fos = FileOutputStream(dbFile)
                val buffer = ByteArray(inputStream.available())
                inputStream.read(buffer)
                fos.write(buffer)
                inputStream.close()
                fos.close()

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
}