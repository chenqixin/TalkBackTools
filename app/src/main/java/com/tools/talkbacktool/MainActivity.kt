package com.tools.talkbacktool

import android.content.Intent
import android.net.Uri
import android.os.Bundle

import com.tools.talkbacktool.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initData(savedInstanceState: Bundle?) {
        binding.tvOpenDouyin.setOnClickListener {
           startActivity(Intent().apply {
               setData(Uri.parse("snssdk1128://aweme/detail/7317568597163904319"))
           })
        }
    }
}