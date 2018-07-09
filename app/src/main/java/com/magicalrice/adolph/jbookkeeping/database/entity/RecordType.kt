package com.magicalrice.adolph.jbookkeeping.database.entity

import android.arch.persistence.room.*
import java.io.Serializable

/**
 * Created by Adolph on 2018/7/6.
 */
@Entity(indices = [Index("type","ranking","state")])
class RecordType : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String?
    //图片name
    @ColumnInfo(name = "img_name")
    var imgName: String?
    //类型：0-支出 1-收入
    var type: Int = 0
    //排序
    var ranking: Long = 0
    //状态：0-正常 1-已删除
    var state: Int = 0
    //是否选中，用于UI
    @Ignore
    var isChecked: Boolean = false

    @Ignore
    constructor(name: String,imgName: String,type: Int) {
        this.name = name
        this.imgName = imgName
        this.type = type
    }

    constructor(name: String, imgName: String, type: Int, ranking: Long) {
        this.name = name
        this.imgName = imgName
        this.type = type
        this.ranking = ranking
    }

    constructor(id: Int,name: String,imgName: String,type: Int,ranking: Long) {
        this.id = id
        this.name = name
        this.imgName = imgName
        this.type = type
        this.ranking = ranking
    }

    companion object {
        @Ignore
        var TYPE_OUTLAY = 0
        @Ignore
        var TYPE_INCOME = 1
        @Ignore
        var STATE_NORMAL = 0
        @Ignore
        var STATE_DELETED = 1
    }
}