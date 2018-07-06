package com.magicalrice.adolph.jbookkeeping.database.entity

import android.arch.persistence.room.*
import java.io.Serializable
import java.math.BigDecimal
import java.util.*

/**
 * Created by Adolph on 2018/7/6.
 */
@Entity(
        foreignKeys = [ForeignKey(
                entity = RecordType::class,
                parentColumns = ["id"],
                childColumns = ["record_type_id"]
        )],
        indices = [(Index(value = arrayOf("record_type_id", "time", "money", "create_time")))]
)
open class Record : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var money: BigDecimal? = null
    var remark: String? = null
    var time: Date? = null

    @ColumnInfo(name = "create_time")
    var createTime: Date? = null
    @ColumnInfo(name = "record_type_id")
    var recordTypeId: Int = 0
}