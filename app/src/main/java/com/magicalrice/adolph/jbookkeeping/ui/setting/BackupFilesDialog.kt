package com.magicalrice.adolph.jbookkeeping.ui.setting

import android.content.Context
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.magicalrice.adolph.jbookkeeping.BR
import com.magicalrice.adolph.jbookkeeping.R
import com.magicalrice.adolph.jbookkeeping.base.BaseDataBindingAdapter
import java.io.File

/**
 * Created by Adolph on 2018/7/11.
 */
class BackupFilesDialog(private val mContext: Context,private val mBackupBeans: List<BackupBean>) {
    private lateinit var mDialog: BottomSheetDialog
    var mOnItemClickListener: ((File) -> Unit)? = null

    init {
        setupDialog()
    }

    private fun setupDialog() {
        val layoutInflater = LayoutInflater.from(mContext)
        val contentView = layoutInflater.inflate(R.layout.dialog_backup_files,null,false)
        val rvFiles = contentView.findViewById<RecyclerView>(R.id.rv_files)
        rvFiles.layoutManager = LinearLayoutManager(mContext)
        val adapter = FilesAdapter(null)
        rvFiles.adapter = adapter
        adapter.setOnItemClickListener {
            _,_,position ->
            mDialog.dismiss()
            mOnItemClickListener?.invoke(adapter.data[position].file)
        }
        adapter.setNewData(mBackupBeans)

        mDialog = BottomSheetDialog(mContext)
        mDialog.setContentView(contentView)
    }

    fun show() {
        mDialog.show()
    }

    internal inner class FilesAdapter(data: List<BackupBean>?) : BaseDataBindingAdapter<BackupBean>(R.layout.item_backup_files,data) {
        override fun convert(helper: DataBindingViewHolder, item: BackupBean) {
            val binding = helper.binding
            binding.setVariable(BR.backupBean, item)
            binding.executePendingBindings()
        }
    }
}