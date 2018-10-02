package com.magicalrice.adolph.jbookkeeping.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.magicalrice.adolph.jbookkeeping.BuildConfig
import com.magicalrice.adolph.jbookkeeping.Constant
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseActivity
import com.magicalrice.adolph.jbookkeeping.base.RouterTable
import com.magicalrice.adolph.jbookkeeping.databinding.ActivityAboutBinding
import com.magicalrice.adolph.jbookkeeping.utils.AndroidUtil
import com.magicalrice.adolph.jbookkeeping.utils.ToastUtils

/**
 * 关于
 *
 * @author Bakumon https://bakumon.me
 */
@Route(path = RouterTable.Url.ITEM_ABOUT, name = "关于")
class AboutActivity : BaseActivity() {
    private lateinit var mBinding: ActivityAboutBinding

    override val layoutId: Int
        get() = R.layout.activity_about

    override fun onInit(savedInstanceState: Bundle?) {
        mBinding = getDataBinding()

        initView()
    }

    private fun initView() {
        mBinding.titleBar?.btnBack?.setOnClickListener { finish() }
        mBinding.titleBar?.title = getString(R.string.text_title_about)
        mBinding.titleBar?.btnRight?.visibility = View.VISIBLE
        mBinding.titleBar?.btnRight?.setOnClickListener { share() }

        mBinding.tvVersion.text = BuildConfig.VERSION_NAME
    }

    private fun share() {
        AndroidUtil.share(this, getString(R.string.text_share_content, Constant.URL_APP_DOWNLOAD))
    }

    @Suppress("never used")
    fun market(view: View) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("market://details?id=$packageName")
            startActivity(intent)
        } catch (e: Exception) {
            ToastUtils.show(R.string.toast_not_install_market)
            e.printStackTrace()
        }
    }

    @Suppress("never used")
    fun alipay(view: View) {
//        if (AlipayZeroSdk.hasInstalledAlipayClient(this)) {
//            AlipayZeroSdk.startAlipayClient(this, Constant.ALIPAY_CODE)
//        } else {
//            ToastUtils.show(R.string.toast_not_install_alipay)
//        }
    }

    @Suppress("never used")
    fun goOpenSource(view: View) {
        ARouter.getInstance().build(RouterTable.Url.ITEM_OPEN_SOURCE).navigation()
    }

    @Suppress("never used")
    fun contactAuthor(view: View) {
        try {
            val data = Intent(Intent.ACTION_SENDTO)
            data.data = Uri.parse("mailto:" + Constant.AUTHOR_EMAIL)
            data.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.text_feedback) + getString(R.string.app_name))
            val content = "\n\n\n\n\n\n______________________________" +
                    "\n" + getString(R.string.text_phone_brand) + android.os.Build.BRAND +
                    "\n" + getString(R.string.text_phone_model) + android.os.Build.MODEL +
                    "\n" + getString(R.string.text_system_version) + android.os.Build.VERSION.RELEASE +
                    "\n" + getString(R.string.text_app_version) + BuildConfig.VERSION_NAME
            data.putExtra(Intent.EXTRA_TEXT, content)
            startActivity(data)
        } catch (e: Exception) {
            ToastUtils.show(R.string.toast_not_install_email)
            e.printStackTrace()
        }
    }

    @Suppress("never used")
    fun goPrivacy(view: View) {
        AndroidUtil.openWeb(this, Constant.URL_PRIVACY)
    }

    @Suppress("never used")
    fun goHelp(view: View) {
        AndroidUtil.openWeb(this, Constant.URL_HELP)
    }
}
