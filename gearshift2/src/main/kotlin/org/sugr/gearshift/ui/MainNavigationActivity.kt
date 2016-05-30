package org.sugr.gearshift.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.view.View
import org.sugr.gearshift.R
import org.sugr.gearshift.databinding.MainNavigationActivityBinding
import org.sugr.gearshift.ui.path.*
import org.sugr.gearshift.ui.view.util.asSequence
import org.sugr.gearshift.viewmodel.MainNavigationViewModel
import org.sugr.gearshift.viewmodel.viewModelFrom

class MainNavigationActivity : AppCompatActivity(),
        MainNavigationViewModel.Consumer,
        View.OnClickListener,
        PathViewBridge {

    lateinit private var binding: MainNavigationActivityBinding
    lateinit private var viewModel: MainNavigationViewModel
    lateinit private var toolbarToggle: DrawerArrowDrawable
    lateinit private var pathNavigator: PathNavigator

    override val defaultPath = TorrentListPath()

    private var toolbarToggleAnimatorReversed = false
    private val toolbarToggleAnimator = lazy {
        ValueAnimator.ofFloat(0f, 1f).apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    if (toolbarToggleAnimatorReversed) {
                        toolbarToggle.progress = 0f
                        toolbarToggle.setVerticalMirror(true);
                    } else {
                        toolbarToggle.progress = 1f
                        toolbarToggle.setVerticalMirror(true);
                    }
                }
            })
            addUpdateListener { a ->
                toolbarToggle.progress = a.animatedValue as? Float ?: 1f
            }

            duration = resources.getInteger(android.R.integer.config_longAnimTime).toLong()
            startDelay = 100
        }
    }

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        pathNavigator = PathNavigator(this)
        pathNavigator.onRestoreInstanceState(state)

        viewModel = viewModelFrom(fragmentManager) { tag, prefs ->
            MainNavigationViewModel(tag, prefs)
        }
        binding = DataBindingUtil.setContentView<MainNavigationActivityBinding>(this, R.layout.main_navigation_activity)
        binding.viewModel = viewModel

        toolbarToggle = DrawerArrowDrawable(binding.appBar.toolbar.getContext())
        binding.appBar.toolbar.navigationIcon = toolbarToggle
        binding.appBar.toolbar.setNavigationOnClickListener(this)

        pathNavigator.restorePath()

        viewModel.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.unbind()
    }

    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        pathNavigator.onSaveInstanceState(state)
    }

    override fun onBackPressed() {
        if (pathNavigator.navigateUp()) {
            onNavigateUp(true)
        }
    }

    override fun closeDrawer() {
        binding.drawer.closeDrawer(GravityCompat.START)
    }

    override fun createProfile() {
        pathNavigator.setPath(FirstTimeProfileEditorPath())
    }

    override fun onClick(v: View?) {
        if (pathNavigator.navigateUp()) {
            onNavigateUp(false)
        }
    }

    override fun getContentView(): Any? {
        return getContentViewWithIndex().first
    }

    override fun onSetContent(newPath: Path, oldPath: Path) {
        val pair = getContentViewWithIndex()
        val container = binding.appBar.viewContainer
        if (pair.second != -1) {
            container.removeViewAt(pair.second)
        }

        val inflater = getLayoutInflater()
        val view = inflater.inflate(newPath.layout, container, false)

        view.setTag(R.id.view_content, true)
        container.addView(view)

        if (newPath.menu == 0) {
            binding.appBar.toolbar.menu.clear();
        } else {
            binding.appBar.toolbar.inflateMenu(newPath.menu)
        }

        if (newPath.title == 0) {
            binding.appBar.toolbar.title = ""
        } else {
            binding.appBar.toolbar.setTitle(newPath.title)
        }

        binding.drawer.setDrawerLockMode(
                if (!newPath.isTopLevel()) DrawerLayout.LOCK_MODE_LOCKED_CLOSED
                else DrawerLayout.LOCK_MODE_UNLOCKED,
                GravityCompat.START
        )

        toggleDrawable(!newPath.isTopLevel())
    }

    private fun getContentViewWithIndex(): Pair<View?, Int> {
        for ((i, v) in binding.appBar.viewContainer.asSequence().withIndex()) {
            if (v.getTag(R.id.view_content) != null) {
                return v to i
            }
        }

        return null to -1
    }

    private fun onNavigateUp(fromBackButton: Boolean) {
        val drawer = binding.drawer
        if (drawer.isDrawerVisible(GravityCompat.START) || !fromBackButton) {
            toggleDrawer()
        } else {
            super.onBackPressed()
        }
    }

    private fun toggleDrawer() {
        val lock = binding.drawer.getDrawerLockMode(GravityCompat.START)
        if (binding.drawer.isDrawerVisible(GravityCompat.START) && lock != DrawerLayout.LOCK_MODE_LOCKED_OPEN) {
            binding.drawer.closeDrawer(GravityCompat.START)
        } else if (lock != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            binding.drawer.openDrawer(GravityCompat.START)
        }
    }

    private fun toggleDrawable(toArrow : Boolean) {
        toolbarToggleAnimator.value.cancel()
        if (toolbarToggle.progress != (if (toArrow) 1f else 0f)) {
            toolbarToggleAnimatorReversed = !toArrow
            if (toArrow) {
                toolbarToggleAnimator.value.start()
            } else {
                toolbarToggleAnimator.value.reverse()
            }
        }
    }

}