package dev.ran.usermanage.ui.main.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment.findNavController
import dev.ran.usermanage.R
import kotlinx.android.synthetic.main.activity_index.*

class IndexActivity : AppCompatActivity() {

    private var doublebacktoexit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        val getdata = intent.getStringExtra("Goto")
        if(getdata == "home"){
            findNavController(fragmentmain).navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    override fun onBackPressed() {
        if (doublebacktoexit) {
            super.onBackPressed()
            return
        }
        this.doublebacktoexit = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doublebacktoexit = false }, 2000)
    }

}