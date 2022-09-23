package com.andflube.breakingnewsapp.ui.details

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andflube.breakingnewsapp.R
import com.andflube.breakingnewsapp.databinding.ActivityDetailsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_PUB = "extra_publish"
        const val EXTRA_DESC = "extra_description"
        const val EXTRA_AUTH = "extra_author"
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_URL = "extra_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null) {
            binding.apply {
                tvTitleDetail.text = extras.getString(EXTRA_TITLE) ?: "Not Title"
                tvPublish.text = extras.getString(EXTRA_PUB) ?: "Not Publish"
                tvDescription.text = extras.getString(EXTRA_DESC) ?: "Not Description"
                tvAuthor.text = extras.getString(EXTRA_AUTH) ?: "Not Author"

                Glide.with(this@DetailsActivity)
                    .load(extras.getString(EXTRA_IMAGE))
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
                    .into(imgNews)

                btnUrlWeb.setOnClickListener{
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(extras.getString(EXTRA_URL))
                    it.context.startActivity(intent)
                }
            }
        }
    }
}
