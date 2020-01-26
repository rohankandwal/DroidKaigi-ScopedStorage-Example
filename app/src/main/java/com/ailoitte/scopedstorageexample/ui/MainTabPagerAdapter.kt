package com.ailoitte.scopedstorageexample.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ailoitte.scopedstorageexample.ui.external_no_permission.ExternalStorageNoPermissionFragment
import com.ailoitte.scopedstorageexample.ui.external_no_permission_q_document_tree.QDocumentTreeFragment
import com.ailoitte.scopedstorageexample.ui.internal_storage.InternalStoragePermissionFragment
import com.ailoitte.scopedstorageexample.ui.shared_storage_no_permission_required_greater_q.SharedStorageNoPermissionRequiredGreaterQFragment
import com.ailoitte.scopedstorageexample.ui.shared_storage_permission_required_less_q.SharedStoragePermissionRequiredLessQFragment

/**
 * @author Rohan Kandwal on 2020-01-20.
 */
class MainTabPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    private val title = arrayListOf("Internal Storage", "Emulated External Storage w/o Permission", "External Shared Storage < Q", "External Shared Storage >= Q", "External Shared Storage >= Q Tree")

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> InternalStoragePermissionFragment()
            1 -> ExternalStorageNoPermissionFragment()
            2 -> SharedStoragePermissionRequiredLessQFragment()
            3 -> SharedStorageNoPermissionRequiredGreaterQFragment()
            4 -> QDocumentTreeFragment()
            else -> Fragment()
        }
    }

    override fun getCount(): Int {
        return title.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }

}