package com.magicalrice.adolph.jbookkeeping.ui.setting

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import com.afollestad.materialdialogs.MaterialDialog
import com.alibaba.android.arouter.facade.annotation.Route
import com.magicalrice.adolph.jbookkeeping.BuildConfig
import com.magicalrice.adolph.jbookkeeping.ConfigManager
import com.magicalrice.adolph.jbookkeeping.Constant
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseActivity
import com.magicalrice.adolph.jbookkeeping.base.RouterTable
import com.magicalrice.adolph.jbookkeeping.databinding.ActivitySettingBinding
import com.magicalrice.adolph.jbookkeeping.utils.AndroidUtil
import com.magicalrice.adolph.jbookkeeping.utils.ToastUtils
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

/**
 * Created by Adolph on 2018/7/10.
 */
@Route(path = RouterTable.ITEM_SETTING,name = "设置页面")
class SettingActivity : BaseActivity(),EasyPermissions.PermissionCallbacks {
    private lateinit var mBinding: ActivitySettingBinding
    private lateinit var mViewModel: SettingViewModel
    private lateinit var mAdapter: SettingAdapter
    override val layoutId: Int
        get() = R.layout.activity_setting

    override fun onInit(savedInstanceState: Bundle?) {
        mBinding = getDataBinding()
        mViewModel = ViewModelProviders.of(this).get(SettingViewModel::class.java)
        initView()
    }

    private fun initView() {
        mBinding.titleBar?.btnBack?.setOnClickListener{finish()}
        mBinding.titleBar?.title=getString(R.string.text_title_setting)

        mBinding.rvSetting.layoutManager = LinearLayoutManager(this)
        mAdapter = SettingAdapter(null)

        val list = ArrayList<SettingSectionEntity>()

        list.add(SettingSectionEntity(getString(R.string.text_setting_money)))
        val budget = if (ConfigManager.budget == 0) getString(R.string.text_no_budget) else getString(R.string.text_money_symbol) + ConfigManager.budget
        list.add(SettingSectionEntity(SettingSectionEntity.Item(getString(R.string.text_monty_budget), budget)))
        list.add(SettingSectionEntity(SettingSectionEntity.Item(getString(R.string.text_setting_type_manage), null)))
        list.add(SettingSectionEntity(SettingSectionEntity.Item(getString(R.string.text_fast_accounting), getString(R.string.text_fast_tip), ConfigManager.isFast)))
        list.add(SettingSectionEntity(SettingSectionEntity.Item(getString(R.string.text_successive_record), getString(R.string.text_successive_record_tip), ConfigManager.isSuccessive)))

        list.add(SettingSectionEntity(getString(R.string.text_setting_backup)))
        list.add(SettingSectionEntity(SettingSectionEntity.Item(getString(R.string.text_go_backup), getString(R.string.text_setting_go_backup_content, backupDir))))
        list.add(SettingSectionEntity(SettingSectionEntity.Item(getString(R.string.text_setting_restore), getString(R.string.text_setting_restore_content, backupDir))))
        list.add(SettingSectionEntity(SettingSectionEntity.Item(getString(R.string.text_setting_auto_backup), getString(R.string.text_setting_auto_backup_content), ConfigManager.isAutoBackup)))

        list.add(SettingSectionEntity(getString(R.string.text_setting_about_and_help)))
        list.add(SettingSectionEntity(SettingSectionEntity.Item(getString(R.string.text_about), getString(R.string.text_about_content))))
        list.add(SettingSectionEntity(SettingSectionEntity.Item(getString(R.string.text_setting_help))))

        mAdapter.setNewData(list)
        addListener()
        mBinding.rvSetting.adapter = mAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            initView()
        }
    }

    private fun addListener() {
        mAdapter.setOnItemClickListener {
            _,_,position ->
            when (position){
                1 -> setBudget(position)
//                2 -> Floo.navigation(this, Router.Url.URL_TYPE_MANAGE).start()
                6 -> showBackupDialog()
                7 -> showRestoreDialog()
//                10 -> Floo.navigation(this, Router.Url.URL_ABOUT).start()
                11 -> AndroidUtil.openWeb(this, Constant.URL_HELP)
                else -> {
                }
            }
        }

        mAdapter.setOnItemChildClickListener{
            _,_,position ->
            when(position) {
                3 -> switchFast()
                4 -> switchSuccessive()
                8 -> switchAutoBackup(position)
                else -> {
                }
            }
        }
    }

    private fun setBudget(position: Int) {
        val oldBudget = if (ConfigManager.budget == 0) null else ConfigManager.budget.toString()
        MaterialDialog.Builder(this)
                .title(R.string.text_set_budget)
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .inputRange(0, 8)
                .positiveText(R.string.text_affirm)
                .negativeText(R.string.text_button_cancel)
                .input(getString(R.string.hint_enter_budget), oldBudget,
                        { _, input ->
                            val text = input.toString()
                            if (!TextUtils.isEmpty(text)) {
                                ConfigManager.setBudget(Integer.parseInt(text))
                            } else {
                                ConfigManager.setBudget(0)
                            }
                            mAdapter.data[position].t.content = if (ConfigManager.budget == 0) getString(R.string.text_no_budget) else getString(R.string.text_money_symbol) + ConfigManager.budget
                            mBinding.rvSetting.itemAnimator.changeDuration = 250
                            mAdapter.notifyItemChanged(position)
                        }).show()
    }

    private fun switchFast() {
        val oldIsConfigOpen = ConfigManager.isFast
        ConfigManager.setIsFast(!oldIsConfigOpen)
    }

    private fun switchSuccessive() {
        val oldIsConfigOpen = ConfigManager.isSuccessive
        ConfigManager.setIsSuccessive(!oldIsConfigOpen)
    }

    private fun switchAutoBackup(position:Int) {
        val oldIsConfigOpen = mAdapter.data[position].t.isConfigOpen
        if (oldIsConfigOpen) {
            MaterialDialog.Builder(this)
                    .cancelable(false)
                    .title(R.string.text_close_auto_backup)
                    .content(R.string.text_close_auto_backup_tip)
                    .positiveText(R.string.text_affirm)
                    .negativeText(R.string.text_button_cancel)
                    .onNegative({ _, _ -> mAdapter.notifyDataSetChanged() })
                    .onPositive({ _, _ -> setAutoBackup(position, false) })
                    .show()
        } else {
            if (EasyPermissions.hasPermissions(this,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                setAutoBackup(position, true)
                return
            }
            EasyPermissions.requestPermissions(
                    PermissionRequest.Builder(this,11,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                            .setRationale(R.string.text_storage_content)
                            .setPositiveButtonText(R.string.text_affirm)
                            .setNegativeButtonText(R.string.text_button_cancel)
                            .build())

        }
    }

    private fun setAutoBackup(position: Int,isBackup:Boolean) {
        ConfigManager.setIsAutoBackup(isBackup)
        mAdapter.data[position].t.isConfigOpen = isBackup
        //取消item动画
        mBinding.rvSetting.itemAnimator.changeDuration = 0
        mAdapter.notifyItemChanged(position)
    }

    private fun showBackupDialog() {
        if (EasyPermissions.hasPermissions(this,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)) {
            backupDB()
            return
        }
        EasyPermissions.requestPermissions(
                PermissionRequest.Builder(this,12,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                        .setRationale(R.string.text_storage_content)
                        .setPositiveButtonText(R.string.text_affirm)
                        .setNegativeButtonText(R.string.text_button_cancel)
                        .build())
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)) {
            AppSettingsDialog.Builder(this)
                    .setRationale(R.string.text_storage_permission_tip)
                    .setTitle(R.string.text_storage)
                    .setPositiveButton(R.string.text_affirm)
                    .setNegativeButton(R.string.text_button_cancel)
                    .build()
                    .show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        when(requestCode) {
            11 -> {
                ConfigManager.setIsAutoBackup(true)
                initView()
            }
            12 -> backupDB()
            13 -> restore()
            else -> {

            }
        }
    }

    private fun backupDB() {
        MaterialDialog.Builder(this)
                .title(R.string.text_backup)
                .content(R.string.text_backup_save, backupFilepath)
                .positiveText(R.string.text_affirm)
                .negativeText(R.string.text_button_cancel)
                .onPositive({ _, _ ->
                    mDisposable.add(mViewModel.backupDB()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ ToastUtils.show(R.string.toast_backup_success) }
                            ) { throwable ->
                                ToastUtils.show(R.string.toast_backup_fail)
                                Logger.e("备份失败", throwable)
                            })
                })
                .show()
    }

    private fun showRestoreDialog(){
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            restore()
            return
        }
        EasyPermissions.requestPermissions(
                PermissionRequest.Builder(this, 13, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .setRationale(R.string.text_storage_content)
                        .setPositiveButtonText(R.string.text_affirm)
                        .setNegativeButtonText(R.string.text_button_cancel)
                        .build())
    }

    private fun restore() {
        mDisposable.add(mViewModel.backupFiles
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ backupBeans ->
                    val dialog = BackupFilesDialog(this, backupBeans)
                    dialog.mOnItemClickListener = {
                        restoreDB(it.path)
                    }
                    dialog.show()
                }
                ) { throwable ->
                    ToastUtils.show(R.string.toast_backup_list_fail)
                    Logger.e("备份文件列表获取失败", throwable)
                })
    }

    private fun restoreDB(restoreFile: String) {
        mDisposable.add(mViewModel.restoreDB(restoreFile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    ToastUtils.show(R.string.toast_restore_success)

                }
                ) { throwable ->
                    ToastUtils.show(R.string.toast_restore_fail)
                    Logger.e("恢复备份失败", throwable)
                })
    }

    companion object {
        @SuppressLint("SdCardPath")
        val backupDir = "/sdcard" + if (BuildConfig.DEBUG) "/backup_moneykeeper_debug/" else "/backup_moneykeeper/"
        @SuppressLint("SdCardPath")
        val backupFilepath = backupDir + if (BuildConfig.DEBUG) "MoneyKeeperBackupUserDebug.db" else "MoneyKeeperBackupUser.db"
    }
}