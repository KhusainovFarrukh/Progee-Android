package kh.farrukh.progee.utils

import android.graphics.Color
import android.os.Build
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat


/**
 *Created by farrukh_kh on 4/16/22 6:11 PM
 *kh.farrukh.movix.utils
 **/
@Suppress("DEPRECATION")
fun AppCompatActivity.makeStatusBarTransparent(parentViewGroup: ConstraintLayout) {
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
        window.statusBarColor = Color.TRANSPARENT
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.decorView.systemUiVisibility =
            SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or SYSTEM_UI_FLAG_LAYOUT_STABLE
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.statusBarColor = Color.TRANSPARENT
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // TODO: test in API_VERSION > 30
        ViewCompat.setOnApplyWindowInsetsListener(parentViewGroup) { view, windowInsets ->

            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.layoutParams = (view.layoutParams as ConstraintLayout.LayoutParams).apply {
                leftMargin = insets.left
                bottomMargin = insets.bottom
                rightMargin = insets.right
            }

            WindowInsetsCompat.CONSUMED
        }
    }
}