package kh.farrukh.progee.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import dagger.hilt.android.AndroidEntryPoint
import kh.farrukh.progee.R
import kh.farrukh.progee.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    private val mainDestinations by lazy {
        listOf(
            R.id.languagesFragment,
            R.id.playgroundFragment,
            R.id.createDataFragment,
            R.id.profileFragment
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupUi(savedInstanceState)
    }

    private fun setupUi(savedInstanceState: Bundle?) {
//        makeStatusBarTransparent(binding.root)
        window.navigationBarColor = Color.BLACK

        binding.bottomNav.apply {

            add(MeowBottomNavigation.Model(mainDestinations[0], R.drawable.ic_home))
            add(MeowBottomNavigation.Model(mainDestinations[1], R.drawable.ic_playground))
            add(MeowBottomNavigation.Model(mainDestinations[2], R.drawable.ic_create_data))
            add(MeowBottomNavigation.Model(mainDestinations[3], R.drawable.ic_profile))

            setOnClickMenuListener { menu ->
                if (navController.currentDestination?.id != menu.id) {
                    navController.navigate(menu.id)
                }
            }

            navController.addOnDestinationChangedListener { _, destination, _ ->

                if (destination.id in mainDestinations && !isShowing(destination.id)) {
                    show(destination.id)
                }
                isVisible = mainDestinations.contains(destination.id)
            }

            if (savedInstanceState == null) show(mainDestinations[0])
        }
    }

//    private fun findNavController() =
//        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
//                as NavHostFragment).navController

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}