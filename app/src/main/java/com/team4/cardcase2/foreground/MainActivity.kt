package com.team4.cardcase2.foreground

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.team4.cardcase2.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val fragmentList= mutableListOf<Fragment>(
        WalletFragment(),
        GroupFragment(),
        WrenchFragment(),
        NavFragment()
    )
    private val titleList= mutableListOf<String>("Wallet", "Group", "Wrench", "Settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main_window)

        val adapter= DataAdapter(this, fragmentList, titleList)
        val viewPager2: ViewPager2 =findViewById(R.id.viewPager2)
        viewPager2.adapter=adapter
        val tabs: TabLayout =findViewById(R.id.tabLayout)

        TabLayoutMediator(tabs, viewPager2) { tab, position ->
            tab.text = adapter.getPageTitle(position)
            tab.setIcon(adapter.getPageIcon(position))
        }.attach()

        val db=openOrCreateDatabase("sqlite.db", MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS SQLTable(uid INTEGER, cid INTEGER, gid TEXT)")
        db.close()
    }
}