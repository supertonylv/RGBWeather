package com.info.tony.rgbweather.data.db.dao

import android.content.Context
import com.info.tony.rgbweather.data.db.CityDatabaseHelper
import com.info.tony.rgbweather.data.db.entities.City
import com.info.tony.rgbweather.data.db.entities.CityConst
import com.info.tony.rgbweather.data.db.entities.HotCity
import com.j256.ormlite.dao.Dao
import java.sql.SQLException
import javax.inject.Inject

/**
 * Created by lvlu on 2018/3/11.
 */
class CityDao {
    val cityDaoOperation: Dao<City, Int>?
    val hotCityDaoOperation: Dao<HotCity, Int>?

    @Inject
    constructor(context: Context){
        this.cityDaoOperation = CityDatabaseHelper.instance(context).getCityDao(City::class.java)
        this.hotCityDaoOperation = CityDatabaseHelper.instance(context).getCityDao(HotCity::class.java)
    }

    /**
     * 查询表中的所有城市
     *
     * @return 城市列表数据
     */
    fun queryCityList(): List<City>? {

        try {
            return cityDaoOperation?.queryForAll()
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 根据城市查询城市信息
     *
     * @param cityId 城市ID
     * @return city
     * @throws SQLException
     */
    @Throws(SQLException::class)
    fun queryCityById(cityId: String): City? {

        val queryBuilder = cityDaoOperation?.queryBuilder()
        queryBuilder?.where()?.eq(CityConst.CITY_ID_FIELD_NAME, cityId)

        return queryBuilder?.queryForFirst()
    }

    /**
     * 查询所有热门城市
     *
     * @return 热门城市列表
     */
    fun queryAllHotCity(): List<HotCity>? {
        try {
            return hotCityDaoOperation?.queryForAll()
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return null
    }
}