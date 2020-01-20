package com.ailoitte.scopedstorageexample.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ailoitte.scopedstorageexample.ui.external_no_permission.ExternalStorageNoPermissionFragment
import com.ailoitte.scopedstorageexample.ui.internal_storage.InternalStoragePermissionFragment
import com.ailoitte.scopedstorageexample.ui.shared_storage_permission_required_greater_q.SharedStoragePermissionRequiredGreaterQFragment
import com.ailoitte.scopedstorageexample.ui.shared_storage_permission_required_less_q.SharedStoragePermissionRequiredLessQFragment

/**
 * @author Rohan Kandwal on 2020-01-20.
 */
class MainTabPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    private val title = arrayListOf("Internal Storage", "External Storage w/o Permission", "Shared Storage < Q", "Sharge Storage >= Q")
    private val count = 4

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> InternalStoragePermissionFragment()
            1 -> ExternalStorageNoPermissionFragment()
            2 -> SharedStoragePermissionRequiredLessQFragment()
            3 -> SharedStoragePermissionRequiredGreaterQFragment()
            else -> Fragment()
        }
    }

    override fun getCount(): Int {
        return count
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }

}