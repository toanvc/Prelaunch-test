package prelaunch.testapp.toanvu.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*
import android.provider.Settings.Secure



class MainActivity : AppCompatActivity() {
    private  var mFirebaseAnalytics: FirebaseAnalytics? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val name = android.os.Build.MANUFACTURER + android.os.Build.MODEL
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show()
        when (item.itemId) {

            R.id.navigation_home -> {
                logEvent("clickNavigation", "home")
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                logEvent("clickNavigation", "home")
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                logEvent("clickNavigation", "home")
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    @SuppressLint("HardwareIds")
    fun onClickTest(v: View) {
        val android_id = Secure.getString(getContentResolver(),
                Secure.ANDROID_ID)
        logEvent("testClick", android_id)
        Toast.makeText(this, "FrameLayoutTest $android_id", Toast.LENGTH_SHORT).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        setContentView(R.layout.activity_main)

        val name = android.os.Build.MANUFACTURER + android.os.Build.MODEL
        logEvent("openapp", name)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        device.text = "Test device? : ${isTestDevice()}"
    }

    private fun logEvent(eventName: String, content: String){
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, eventName)
        bundle.putString(FirebaseAnalytics.Param.CONTENT, content)
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "text")
        mFirebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    private fun isTestDevice(): Boolean {
        val testLabSetting = Settings.System.getString(contentResolver, "firebase.test.lab")
        return "true" == testLabSetting
    }
}
