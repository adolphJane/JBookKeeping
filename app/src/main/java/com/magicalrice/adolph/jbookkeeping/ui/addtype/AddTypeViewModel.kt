package com.magicalrice.adolph.jbookkeeping.ui.addtype

import com.magicalrice.adolph.jbookkeeping.base.BaseViewModel
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordType
import com.magicalrice.adolph.jbookkeeping.datasource.AppDataSource
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * 添加记账类型 ViewModel
 * Created by Adolph on 2018/7/12.
 */
class AddTypeViewModel(dataSource: AppDataSource): BaseViewModel(dataSource) {
    fun getAllTypeImgBeans(type:Int) : Flowable<List<TypeImgBean>> {
        return mDataSource.getAllTypeImgBeans(type)
    }

    /**
     * 保存记账类型，包括新增和更新
     *
     * @param recordType 修改时传
     * @param type       类型
     * @param imgName    图片
     * @param name       类型名称
     */
    fun saveRecordType(recordType: RecordType?,type: Int,imgName:String,name:String):Completable {
        return if (recordType == null) {
            // 添加
            mDataSource.addRecordType(type,imgName,name)
        } else {
            // 修改
            val updateType = RecordType(recordType.id,name,imgName,recordType.type,recordType.ranking)
            updateType.state = recordType.state
            mDataSource.updateRecordType(recordType,updateType)
        }
    }
}