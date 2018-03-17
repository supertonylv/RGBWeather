package com.info.tony.rgbweather.data.db.entities.rgblist

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by lvlu on 2018/3/9.
 */
@DatabaseTable(tableName = "LifeIndex")
data class LifeIndex(
        @DatabaseField(columnName = LifeIndexConst.ID_FIELD_NAME, generatedId = true,dataType = DataType.LONG)
        var id: Long = 0,//数据库自增长ID
        @DatabaseField(columnName =  LifeIndexConst.CITY_ID_FIELD_NAME,dataType = DataType.STRING)
        var cityId: String? = null,
        @DatabaseField(columnName =  LifeIndexConst.NAME_ID_FIELD_NAME,dataType = DataType.STRING)
        var name: String? = null,
        @DatabaseField(columnName =  LifeIndexConst.INDEX_ID_FIELD_NAME,dataType = DataType.STRING)
        var index: String? = null,
        @DatabaseField(columnName =  LifeIndexConst.DETAILS_ID_FIELD_NAME,dataType = DataType.STRING)
        var details: String? = null
)

class LifeIndexConst{
    companion object {
        const val ID_FIELD_NAME = "_id"
        const val CITY_ID_FIELD_NAME = "cityId"
        const val NAME_ID_FIELD_NAME = "name"
        const val INDEX_ID_FIELD_NAME = "index"
        const val DETAILS_ID_FIELD_NAME = "details"
    }
}