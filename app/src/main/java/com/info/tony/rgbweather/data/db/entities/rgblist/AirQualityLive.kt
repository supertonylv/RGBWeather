package com.info.tony.rgbweather.data.db.entities.rgblist

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.stmt.query.In
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by lvlu on 2018/3/9.
 */
@DatabaseTable(tableName = "AirQuality")
data class AirQualityLive(
        @DatabaseField(columnName = AirQualityLiveConst.CITY_ID_FIELD_NAME, id = true,dataType = DataType.STRING)
        private var cityId: String? = null,

        @DatabaseField(columnName = AirQualityLiveConst.AQI_FIELD_NAME,dataType = DataType.INTEGER)
        private var aqi: Int = 0,

        @DatabaseField(columnName = AirQualityLiveConst.PM25_FIELD_NAME,dataType = DataType.INTEGER)
        private var pm25: Int = 0,

        @DatabaseField(columnName = AirQualityLiveConst.PM10_FIELD_NAME,dataType = DataType.INTEGER)
        private var pm10: Int = 0,

        @DatabaseField(columnName = AirQualityLiveConst.PUBLISH_TIME_FIELD_NAME,dataType = DataType.STRING)
        private var publishTime: String? = null,

        @DatabaseField(columnName = AirQualityLiveConst.ADVICE_FIELD_NAME,dataType = DataType.STRING)
        private var advice: String? = null,//建议

        @DatabaseField(columnName = AirQualityLiveConst.CITY_RANK_FIELD_NAME,dataType = DataType.STRING)
        private var cityRank: String? = null,//城市排名

        @DatabaseField(columnName = AirQualityLiveConst.QUALITY_FIELD_NAME,dataType = DataType.STRING)
        private var quality: String? = null,//空气质量
        @DatabaseField(columnName = AirQualityLiveConst.CO_FIELD_NAME,dataType = DataType.STRING)
        private var co: String? = null,//一氧化碳浓度(mg/m3)
        @DatabaseField(columnName = AirQualityLiveConst.SO2_FIELD_NAME,dataType = DataType.STRING)
        private var so2: String? = null,//二氧化硫浓度(μg/m3)
        @DatabaseField(columnName = AirQualityLiveConst.NO2_FIELD_NAME,dataType = DataType.STRING)
        private var no2: String? = null,//二氧化氮浓度(μg/m3)
        @DatabaseField(columnName = AirQualityLiveConst.O3_FIELD_NAME,dataType = DataType.STRING)
        private var o3: String? = null,//臭氧浓度(μg/m3)
        @DatabaseField(columnName = AirQualityLiveConst.PRIMARY_FIELD_NAME,dataType = DataType.STRING)
        private var primary: String? = null//首要污染物
) {
}

class AirQualityLiveConst {
    companion object {
        const val CITY_ID_FIELD_NAME = "cityId"
        const val AQI_FIELD_NAME = "aqi"
        const val PM25_FIELD_NAME = "pm25"
        const val PM10_FIELD_NAME = "pm10"
        const val PUBLISH_TIME_FIELD_NAME = "publishTime"
        const val ADVICE_FIELD_NAME = "advice"
        const val CITY_RANK_FIELD_NAME = "cityRank"
        const val QUALITY_FIELD_NAME = "quality"

        const val CO_FIELD_NAME = "co"
        const val SO2_FIELD_NAME = "so2"
        const val NO2_FIELD_NAME = "no2"
        const val O3_FIELD_NAME = "o3"
        const val PRIMARY_FIELD_NAME = "primary"
    }
}