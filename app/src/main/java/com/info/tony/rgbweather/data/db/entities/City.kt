package com.info.tony.rgbweather.data.db.entities

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by lvlu on 2018/3/9.
 */
@DatabaseTable(tableName = "City")
data  class City (
    @DatabaseField(columnName = CityConst.ID_FIELD_NAME,dataType = DataType.INTEGER, generatedId = true)
    private val id: Int? = null,
    @DatabaseField(columnName = CityConst.ROOT_FIELD_NAME,dataType = DataType.STRING)
    private var root: String? = null,
    @DatabaseField(columnName = CityConst.PARENT_FIELD_NAME,dataType = DataType.STRING)
    private var parent: String? = null,
    @DatabaseField(columnName = CityConst.CITY_ID_FIELD_NAME,dataType = DataType.INTEGER)
    private var cityId: Int = 0,
    @DatabaseField(columnName = CityConst.CITY_NAME_FIELD_NAME,dataType = DataType.STRING)
    private var cityName: String? = null,
    @DatabaseField(columnName = CityConst.CITY_NAME_EN_FIELD_NAME,dataType = DataType.STRING)
    private var cityNameEn: String? = null,
    @DatabaseField(columnName = CityConst.LON_FIELD_NAME,dataType = DataType.STRING)
    private var lon: String? = null,
    @DatabaseField(columnName = CityConst.LAT_FIELD_NAME,dataType = DataType.STRING)
    private var lat: String? = null
){

}

class CityConst {
    companion object {
        //City
            const val ID_FIELD_NAME:String = "_id"
            const val ROOT_FIELD_NAME:String = "root"
            const val PARENT_FIELD_NAME:String = "parent"
            const val CITY_NAME_FIELD_NAME:String = "name"
            const val CITY_NAME_EN_FIELD_NAME:String = "pinyin"
            const val LON_FIELD_NAME:String = "x"
            const val LAT_FIELD_NAME:String = "y"
            const val CITY_ID_FIELD_NAME:String = "posID"
    }
}