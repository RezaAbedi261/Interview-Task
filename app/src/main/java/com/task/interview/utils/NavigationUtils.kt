package com.task.interview.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavAction
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.task.interview.R

object NavigationAnimations{
    val leftToRightAnim get() = NavOptions.Builder()
            .setPopEnterAnim(R.anim.fragment_animation_left_to_right_pop_enter)
            .setPopExitAnim(R.anim.fragment_animation_left_to_right_pop_exit)
            .setEnterAnim(R.anim.fragment_animation_right_to_left_enter)
            .setExitAnim(R.anim.fragment_animation_right_to_left_exit)

    val leftToRight get() = leftToRightAnim.build()


    val bottomToTopAnim get() = NavOptions.Builder()
            .setPopEnterAnim(R.anim.fragment_animation_top_to_bottom_pop_enter)
            .setPopExitAnim(R.anim.fragment_animation_top_to_bottom_pop_exit)
            .setEnterAnim(R.anim.fragment_animation_bottom_to_top_enter)
            .setExitAnim(R.anim.fragment_animation_bottom_to_top_exit)
    val bottomToTop get() = bottomToTopAnim.build()


    fun noAnim(): NavOptions {
        return NavOptions.Builder()
            .setExitAnim(0)
            .setPopEnterAnim(0)
            .setPopExitAnim(0)
            .setEnterAnim(0).build()
    }
}

object NavigationUtils {

    fun navigateSafe(navController: NavController?, @IdRes id: Int, args: Bundle?, navOptions: NavOptions?): Boolean {
        if (navController == null) return false
        val action: NavAction?
        val currentDestination = navController.currentDestination
        action = if (currentDestination != null) {
            currentDestination.getAction(id)
        } else {
            navController.graph.getAction(id)
        }
        if (currentDestination != null) {
            if (action != null) {
                if (currentDestination.id != action.destinationId) {
                    navController.navigate(id, args, navOptions)
                    return true
                }
            } else {
                val destination = navController.graph.findNode(id)
                if (destination != null && currentDestination.id != destination.id) {
                    navController.navigate(id, args, navOptions)
                    return true
                }
            }
        }
        return false
    }

}
