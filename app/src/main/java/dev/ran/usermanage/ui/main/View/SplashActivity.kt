package dev.ran.usermanage.ui.main.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dev.ran.usermanage.R
import dev.ran.usermanage.data.Repository.Session
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        checkUserlog()
    }
    private fun checkUserlog(){
        val sessionCheck = Session(this)
        val user = sessionCheck.getUser()
        logosplash.visibility = View.VISIBLE
        logosplash.alpha = 0f
        if(user.isEmpty()){
            logosplash.animate().setDuration(1000).alpha(1f).withEndAction {
                startActivity(Intent(this, IndexActivity::class.java).apply { putExtra("Goto", "login") })
                finish()
            }
        }else{
            logosplash.animate().setDuration(1000).alpha(1f).withEndAction {
                startActivity(Intent(this, IndexActivity::class.java).apply { putExtra("Goto", "home") })
                finish()
            }
        }
    }
}