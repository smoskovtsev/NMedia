package ru.netology.nmedia.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import ru.netology.nmedia.FeedFragment
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.AppActivityBinding

class AppActivity : AppCompatActivity(R.layout.app_activity)

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val binding = AppActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        if (supportFragmentManager.findFragmentByTag(FeedFragment.TAG) == null) {
//            supportFragmentManager.beginTransaction()
//                .add(R.id.fragmentContainer, FeedFragment())
//                .commit()
//        }
//
//        or
//        supportFragmentManager.commit {
//            add(R.id.fragmentContainer, FeedFragment())
//        }
//    }
//}