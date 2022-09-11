package com.udacity.asteroidradar

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

private const val TAG = "BindingAdapters"

@BindingAdapter("livePicture")
fun bindPictureOfTheDay(imageView: ImageView, pictureOfDay: PictureOfDay?) {
    Log.d(TAG, "bindPictureOfTheDay: is Called\n${pictureOfDay?.url}")
    pictureOfDay?.let {
        Picasso.with(imageView.context)
            .load(pictureOfDay.url)
            .placeholder(R.drawable.placeholder_picture_of_day)
            .into(imageView)
        imageView.contentDescription =
            imageView.context.getString(
                R.string.nasa_picture_of_day_content_description_format,
                pictureOfDay.title
            )
    }

}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
