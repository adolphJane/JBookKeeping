package com.magicalrice.adolph.jbookkeeping.datasource

import com.magicalrice.adolph.jbookkeeping.ConfigManager
import com.magicalrice.adolph.jbookkeeping.database.AppDatabase
import com.magicalrice.adolph.jbookkeeping.database.entity.*
import com.magicalrice.adolph.jbookkeeping.ui.addtype.TypeImgBean
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*

/**
 * Created by Adolph on 2018/7/9.
 */
class LocalAppDataSource(private val mAppDatabase: AppDatabase) : AppDataSource {

    @Throws(Exception::class)
    private fun autoBackup() {
        if (ConfigManager.isAutoBackup) {
            val isSuccess =
        }
    }

    override fun initRecordTypes(): Completable {
        return Completable.fromAction {
            if (mAppDatabase.recordTypeDao().getRecordTypeCount() < 1) {
                // 没有记账类型数据记录，插入默认的数据类型
                mAppDatabase.recordTypeDao().insertRecordTypes(*RecordTypeInitCreator.createRecordTypeData())
            }
        }
    }

    override fun addRecordType(type: Int, imgName: String, name: String): Completable {
        return Completable.fromAction {
            val recordType = mAppDatabase.recordTypeDao().getTypeByName(type,name)
            if (recordType != null) {
                //name 类型存在
                if (recordType.state == RecordType.STATE_DELETED) {
                    // 已删除状态
                    recordType.state = RecordType.STATE_NORMAL
                    recordType.ranking = System.currentTimeMillis()
                    recordType.imgName = imgName
                    mAppDatabase.recordTypeDao().updateRecordTypes(recordType)
                } else {
                    //提示用户该类型已经存在
                    throw IllegalStateException(name + "该类型已经存在")
                }
            } else {
                //不存在，直接新增
                val insertType = RecordType(name,imgName,type,System.currentTimeMillis())
                mAppDatabase.recordTypeDao().insertRecordTypes(insertType)
            }

        }
    }

    override fun updateRecordType(oldRecordType: RecordType, recordType: RecordType): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteRecordType(recordType: RecordType): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllRecordType(): Flowable<List<RecordType>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRecordTypes(type: Int): Flowable<List<RecordType>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sortRecordTypes(recordTypes: List<RecordType>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllTypeImgBeans(type: Int): Flowable<List<TypeImgBean>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertRecord(record: Record): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateRecord(record: Record): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteRecord(record: Record): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCurrentMonthRecordWithTypes(): Flowable<List<RecordWithType>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRecordWithTypes(dateFrom: Date, dateTo: Date, type: Int): Flowable<List<RecordWithType>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRecordWithTypes(dateFrom: Date, dateTo: Date, type: Int, typeId: Int): Flowable<List<RecordWithType>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRecordWithTypesSortMoney(dateFrom: Date, dateTo: Date, type: Int, typeId: Int): Flowable<List<RecordWithType>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCurrentMonthSumMoney(): Flowable<List<SumMoneyBean>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMonthSumMoney(dateFrom: Date, dateTo: Date): Flowable<List<SumMoneyBean>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDaySumMoney(year: Int, month: Int, type: Int): Flowable<List<DaySumMoneyBean>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTypeSumMoney(from: Date, to: Date, type: Int): Flowable<List<TypeSumMoneyBean>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMonthOfYearSumMoney(from: Date, to: Date): Flowable<List<MonthSumMoneyBean>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}