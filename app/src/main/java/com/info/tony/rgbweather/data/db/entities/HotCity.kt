package com.info.tony.rgbweather.data.db.entities

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by lvlu on 2018/3/9.
 */
@DatabaseTable(tableName = "HotCity")
data class HotCity(
        @DatabaseField(columnName = HotCityConst.ID_FIELD_NAME, generatedId = true,dataType = DataType.INTEGER)
        val id: Int = 0,

        @DatabaseField(columnName = HotCityConst.CITY_ID_FIELD_NAME,dataType = DataType.STRING_BYTES)
        var cityId: Int = 0,

        @DatabaseField(columnName = HotCityConst.CITY_NAME_FIELD_NAME,dataType = DataType.STRING)
        var cityName: String? = null)

class HotCityConst {
    companion object {
        const val ID_FIELD_NAME = "_id"
        const val CITY_NAME_FIELD_NAME = "name"
        const val CITY_ID_FIELD_NAME = "posID"
    }
}