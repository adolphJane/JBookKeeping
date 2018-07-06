package com.magicalrice.adolph.jbookkeeping.ui.home

import android.os.Bundle
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseActivity
import com.magicalrice.adolph.jbookkeeping.databinding.ActivityHomeBinding
import pub.devrel.easypermissions.EasyPermissions

class HomeActivity : BaseActivity(), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    private lateinit var mBinding: ActivityHomeBinding
    private lateinit var mViewModel: HomeViewModel
    private lateinit var mAdapter: HomeAdapter
    private var isUserFirst: Boolean = false
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRationaleDenied(requestCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRationaleAccepted(requestCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val layoutId: Int
        get() = R.layout.activity_home

    override fun onInit(savedInstanceState: Bundle?) {
        mBinding = getDataBinding()
        val viewModelFactory = 
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}
