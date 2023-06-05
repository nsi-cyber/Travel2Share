package com.nsicyber.travel2share.Customs

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.nsicyber.travel2share.models.NoteModel

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nsicyber.travel2share.R


class Utils {}

fun handleClick(fragment: Fragment?, destinationFragment: Int, data: NoteModel?) {
    val navController = fragment?.findNavController()

    val navOpt = navOptions {
        anim {
            exit = R.anim.slide_out
            enter = R.anim.slide_in
            popExit = R.anim.slide_pop_out
            popEnter = R.anim.slide_pop_in
        }

    }

    val bundle = Bundle()


    data?.let {


        bundle.putSerializable("data", parse<HashMap<*,*>>(data))

        navController?.navigate(
            destinationFragment, bundle, navOpt
        )
        return
    }

    navController?.navigate(
        destinationFragment, null, navOpt
    )

}


inline fun <reified T> parse(src: Any?): T? {
    src?.let {
        return Gson().fromJson<T>(
            Gson().toJson(src), object : TypeToken<T>() {}.type
        )
    }
    return null
}
