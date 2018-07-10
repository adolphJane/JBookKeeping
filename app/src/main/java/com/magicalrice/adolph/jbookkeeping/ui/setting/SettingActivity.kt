package com.magicalrice.adolph.jbookkeeping.ui.setting

import android.os.Bundle
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseActivity
import com.magicalrice.adolph.jbookkeeping.databinding.ActivitySettingBinding
import pub.devrel.easypermissions.EasyPermissions

/**
 * Created by Adolph on 2018/7/10.
 */
class SettingActivity : BaseActivity(),EasyPermissions.PermissionCallbacks {
    private lateinit var mBinding: ActivitySettingBinding
    private lateinit var mViewModel: SettingViewModel
    private lateinit var mAdapter: SettingAdapter
    override val layoutId: Int
        get() = R.layout.activity_setting

    override fun onInit(savedInstanceState: Bundle?) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}