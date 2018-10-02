package com.magicalrice.adolph.jbookkeeping.ui.home

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.magicalrice.adolph.jbookkeeping.ConfigManager
import com.magicalrice.adolph.jbookkeeping.Injection
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseActivity
import com.magicalrice.adolph.jbookkeeping.base.RouterTable
import com.magicalrice.adolph.jbookkeeping.database.entity.RecordWithType
import com.magicalrice.adolph.jbookkeeping.databinding.ActivityHomeBinding
import com.magicalrice.adolph.jbookkeeping.datasource.BackupFailException
import com.magicalrice.adolph.jbookkeeping.utils.ShortCutUtils
import com.magicalrice.adolph.jbookkeeping.utils.ToastUtils
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

@Route(path = RouterTable.Url.ITEM_HOME, name = "主页面")
class HomeActivity : BaseActivity(), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    private lateinit var mBinding: ActivityHomeBinding
    private lateinit var mViewModel: HomeViewModel
    private lateinit var mAdapter: HomeAdapter
    private var isUserFirst: Boolean = false

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            if (!isUserFirst) {
                AppSettingsDialog.Builder(this)
                        .setRationale(R.string.text_storage_permission_tip)
                        .setTitle(R.string.text_storage)
                        .setPositiveButton(R.string.text_affirm)
                        .setNegativeButton(R.string.text_button_cancel)
                        .build()
                        .show()
            }
        } else {
            updateConfig(false)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        updateConfig(true)
    }

    override fun onRationaleDenied(requestCode: Int) {
        isUserFirst = true
    }

    override fun onRationaleAccepted(requestCode: Int) {
        isUserFirst = true
    }

    override val layoutId: Int
        get() = R.layout.activity_home

    override fun onInit(savedInstanceState: Bundle?) {
        mBinding = getDataBinding()
        val viewModelFactory = Injection.provideViewModelFactory()
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        initView()
        initData()
        checkPermissionForBackup()

        //快速记账
        if (ConfigManager.isFast) {
            ARouter.getInstance().build(RouterTable.Url.ITEM_ADD_RECORD).navigation()
        }
    }

    private fun initView() {
        mBinding.rvHome.layoutManager = LinearLayoutManager(this)
        mAdapter = HomeAdapter(null)
        mBinding.rvHome.adapter = mAdapter

        mAdapter.setOnItemChildLongClickListener { _, _, position ->
            showOperateDialog(mAdapter.data[position])
            false
        }
    }

    private fun initData() {
        initRecordTypes()
        getCurrentMonthRecords()
    }

    @Suppress("never used")
    fun addRecordClick(view: View) {
        ARouter.getInstance().build(RouterTable.Url.ITEM_ADD_RECORD).navigation()
    }

    @Suppress("never used")
    fun statisticsClick(view: View) {
        ARouter.getInstance().build(RouterTable.Url.ITEM_STATISTIC).navigation()
    }

    @Suppress("never used")
    fun settingClick(view: View) {
        ARouter.getInstance().build(RouterTable.Url.ITEM_SETTING).navigation()
    }

    private fun showOperateDialog(record: RecordWithType) {
        MaterialDialog.Builder(this)
                .items(getString(R.string.text_modify), getString(R.string.text_delete))
                .itemsCallback({ _, _, which, _ ->
                    if (which == 0) {
                        modifyRecord(record)
                    } else {
                        deleteRecord(record)
                    }
                })
                .show()
    }

    private fun modifyRecord(record: RecordWithType) {
        ARouter.getInstance().build(RouterTable.Url.ITEM_ADD_RECORD)
                .withSerializable(RouterTable.ExtraKey.KEY_RECORD_BEAN, record)
                .navigation()
    }

    private fun deleteRecord(record: RecordWithType) {
        mDisposable.add(mViewModel.deleteRecord(record)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}) { throwable ->
                    if (throwable is BackupFailException) {
                        ToastUtils.show(throwable.message)
                        Logger.e("备份失败（删除记账记录失败的时候）", throwable)
                    } else {
                        ToastUtils.show(R.string.toast_record_delete_fail)
                        Logger.e("删除记账记录失败", throwable)
                    }
                })
    }

    private fun initRecordTypes() {
        mDisposable.add(mViewModel.initRecordTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ ShortCutUtils.addRecordShortcut(this) }
                ) { throwable ->
                    if (throwable is BackupFailException) {
                        ToastUtils.show(throwable.message)
                        Logger.e("备份失败（初始化类型数据失败的时候）", throwable)
                    } else {
                        ToastUtils.show(R.string.toast_init_types_fail)
                        Logger.e("初始化类型数据失败", throwable)
                    }
                }
        )
    }

    private fun getCurrentMonthRecords() {
        mDisposable.add(mViewModel.currentMonthRecordWithTypes
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ recordWithTypes ->
                    setListData(recordWithTypes)
                    if (recordWithTypes == null || recordWithTypes.isEmpty()) {
                        setEmptyView()
                    }
                })
                { throwable ->
                    ToastUtils.show(R.string.toast_records_fail)
                    Logger.e("获取记录列表失败", throwable)
                })
    }

    private fun setListData(recordWithTypes: List<RecordWithType>?) {
        mAdapter.setNewData(recordWithTypes)
        val isShowFooter = recordWithTypes != null && recordWithTypes.size > MAX_ITEM_TIP
        if (isShowFooter) {
            mAdapter.setFooterView(inflate(R.layout.layout_footer_tip))
        } else {
            mAdapter.removeAllFooterView()
        }
    }

    private fun setEmptyView() {
        mAdapter.emptyView = inflate(R.layout.layout_home_empty)
    }

    private fun checkPermissionForBackup() {
        if (!ConfigManager.isAutoBackup) {
            return
        }
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            return
        }
        EasyPermissions.requestPermissions(
                PermissionRequest.Builder(this, REQUEST_CODE_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .setRationale(R.string.text_storage_content)
                        .setPositiveButtonText(R.string.text_affirm)
                        .setNegativeButtonText(R.string.text_button_cancel)
                        .build()
        )
    }

    private fun updateConfig(isAutoBackup: Boolean) {
        if (isAutoBackup) {
            ConfigManager.setIsAutoBackup(true)
        } else {
            if (ConfigManager.setIsAutoBackup(false)) {
                ToastUtils.show(R.string.toast_open_auto_backup)
            }
        }
    }

    private fun getCurrentMonthSumMoney() {
        mDisposable.add(mViewModel.currentMonthSumMoney
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mBinding.sumMoneyBeanList = it
                }) { throwable ->
                    ToastUtils.show(R.string.toast_current_sum_money_fail)
                    Logger.e("本月支出收入总数获取失败", throwable)
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                updateConfig(true)
            } else {
                updateConfig(false)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getCurrentMonthSumMoney()
        if (ConfigManager.isSuccessive) {
            mBinding.btnAddRecord.setOnLongClickListener {
                ARouter.getInstance().build(RouterTable.Url.ITEM_ADD_RECORD)
                        .withBoolean(RouterTable.ExtraKey.KEY_IS_SUCCESSIVE, true)
                        .navigation()
                false
            }
        } else {
            mBinding.btnAddRecord.setOnLongClickListener(null)
        }
    }

    companion object {
        private const val MAX_ITEM_TIP = 5
        private const val REQUEST_CODE_STORAGE = 11
    }
}
