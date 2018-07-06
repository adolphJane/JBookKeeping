package com.magicalrice.adolph.jbookkeeping.database.entity

import android.arch.persistence.room.Relation

/**
 * 包含RecordType的Record
 * 用于联名查询
 * Created by Adolph on 2018/7/6.
 */
class RecordWithType : Record() {
    @Relation(parentColumn = "record_type_id", entityColumn = "id", entity = RecordType::class)
    var mRecordTypes: List<RecordType>? = null
}