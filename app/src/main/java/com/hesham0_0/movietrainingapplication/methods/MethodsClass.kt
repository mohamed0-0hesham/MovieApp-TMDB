package com.hesham0_0.movietrainingapplication.methods


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.hesham0_0.movietrainingapplication.R
import com.hesham0_0.movietrainingapplication.domain.models.Genre
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlin.math.roundToInt

const val BASE_IMAGE_URL_ORIGINAL_API = "https://image.tmdb.org/t/p/original/"
const val BASE_IMAGE_W500_URL_API = "https://image.tmdb.org/t/p/w500/"

class MethodsClass {
    companion object {
        var genresList = listOf<Genre>()
        fun checkForInternet(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork ?: return false
                val activeNetwork =
                    connectivityManager.getNetworkCapabilities(network) ?: return false
                return when {
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            } else {
                @Suppress("DEPRECATION") val networkInfo =
                    connectivityManager.activeNetworkInfo ?: return false
                @Suppress("DEPRECATION")
                return networkInfo.isConnected
            }
        }

        fun addGenreList(list: List<Genre>) {
            genresList = list
        }

        fun createChip(genre: Genre, context: Context): Chip {
            val chip = Chip(context)
            chip.text = genre.name
            chip.id = genre.id.toInt()
            chip.setBackgroundResource(R.color.Tertiary_Color_Light_green)
            chip.isClickable = true
            chip.isCheckable = true
            return chip
        }

        @BindingAdapter("imageUrl")
        @JvmStatic
        fun getImageByPicasso(view: ImageView, image_url: String?) {
            if (image_url != null) {
                Glide.with(view.context).asBitmap()
                    .load(BASE_IMAGE_W500_URL_API + image_url)
                    .override(200, 270)
                    .placeholder(R.drawable.ic_baseline_cached_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(view)
            }
        }

        @BindingAdapter("dropBackUrl")
        @JvmStatic
        fun getImageByGlide(view: ImageView, image_url: String?) {
            Glide.with(view.context)
                .load(BASE_IMAGE_W500_URL_API + image_url)
                .placeholder(R.drawable.ic_baseline_cached_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(view)
        }

        @BindingAdapter("itsForAdult")
        @JvmStatic
        fun itsForAdult(view: ImageView, itsForAdult: Boolean) {
            if (itsForAdult) {
                view.setImageResource(R.drawable.adult)
            } else {
                view.setImageResource(R.drawable.family_4_people_svgrepo_com)
            }
        }

        @BindingAdapter("rate")
        @JvmStatic
        fun rateToInt(view: Chip, rate: Double) {
            when (rate.toInt()) {
                0, 1, 2, 3 -> view.setChipBackgroundColorResource(R.color.red)
                4, 5, 6, 7 -> view.setChipBackgroundColorResource(R.color.yellow)
                8, 9, 10 -> view.setChipBackgroundColorResource(R.color.green)
            }
            val rate2digit = (((rate * 10).roundToInt()).div(10.0))
            view.text = rate2digit.toString()
        }

        @BindingAdapter("addGenreChip")
        @JvmStatic
        fun addChip(view: ChipGroup, genreIdList: List<String>?) {
            if (genreIdList != null) {
                for (genreId in genreIdList) {
                    for (genre in genresList) {
                        if (genre.id == genreId) {
                            val chip = createChip(genre, view.context)
                            chip.isClickable = false
                            chip.isCheckable = false
                            view.addView(chip as View)
                        }
                    }
                }
            }
        }
    }
}