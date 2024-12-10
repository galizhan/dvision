package com.rayneo.arsdk.android.demo.ui.activity

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.rayneo.arsdk.android.demo.R
import com.rayneo.arsdk.android.demo.databinding.LayoutFragmentDemoBinding
import com.rayneo.arsdk.android.demo.ui.fragment.FragmentDemo
import com.rayneo.arsdk.android.demo.ui.wedget.OnTitleSelectListener
import com.rayneo.arsdk.android.focus.IFocusable
import com.rayneo.arsdk.android.focus.releaseFocus
import com.rayneo.arsdk.android.focus.reqFocus
import com.rayneo.arsdk.android.ui.activity.BaseEventActivity

class FragmentDemoActivity : BaseEventActivity() {
    private lateinit var binding: LayoutFragmentDemoBinding
    private lateinit var fragmentList: List<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutFragmentDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.phoneTitleView.apply {
            setTitles(
                arrayOf(
                    "fragment1",
                    "fragment2",
                    "fragment3",
                )
            )
            watchAction(this@FragmentDemoActivity, templeActionViewModel)
            hasFocus = true
        }
        initFragments()
        initEvent()
    }

    private fun initEvent() {
        binding.phoneTitleView.onTitleSelectListener = object : OnTitleSelectListener {
            override fun onTitleSelect(pos: Int, titleView: TextView) {
                val fragment = fragmentList[pos]
                showFragment(pos)
                binding.phoneTitleView.releaseFocus()
                if (fragment is IFocusable) {
                    fragment.reqFocus(binding.phoneTitleView)
                }
            }
        }
    }

    private fun showFragment(pos:Int) {
        supportFragmentManager.commit(true) {
            fragmentList.forEachIndexed { index, fragment ->
                if (pos == index) {
                    show(fragment)
                } else {
                    hide(fragment)
                }
            }
        }
    }

    private fun initFragments() {
        val titleView = binding.phoneTitleView
        supportFragmentManager.commit(true) {
            fragmentList = arrayListOf(
                FragmentDemo.newInstance("content1").apply { focusParent = titleView },
                FragmentDemo.newInstance("content2").apply { focusParent = titleView },
                FragmentDemo.newInstance("content3").apply { focusParent = titleView },
            )
            fragmentList.forEachIndexed { index, fragment ->
                add(R.id.flContainer, fragment, buildFragmentTag("tab_$index"))
                if (index != 0) {
                    hide(fragment)
                } else {
                    show(fragment)
                }
            }
        }
    }

    companion object {

        fun buildFragmentTag(suffix: String): String {
            return "frag_$suffix"
        }
    }
}