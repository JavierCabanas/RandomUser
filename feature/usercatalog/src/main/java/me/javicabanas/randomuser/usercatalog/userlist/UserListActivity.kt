package me.javicabanas.randomuser.usercatalog.userlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import me.javicabanas.randomuser.usercatalog.R

class UserListActivity : AppCompatActivity() {

    val vm: UserListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
    }
}