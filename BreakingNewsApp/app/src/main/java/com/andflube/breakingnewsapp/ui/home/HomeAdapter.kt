package com.andflube.breakingnewsapp.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andflube.breakingnewsapp.R
import com.andflube.breakingnewsapp.databinding.ItemNewsBinding
import com.andflube.breakingnewsapp.db.data.NewsDB
import com.andflube.breakingnewsapp.ui.DateFormatter
import com.andflube.breakingnewsapp.ui.details.DetailsActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class HomeAdapter(private val onBookmarkClick: (NewsDB) -> Unit): ListAdapter<NewsDB, HomeAdapter.NewsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)

        val ivBookmark = holder.binding.ivBookmark
        if (news.isBookmarked) {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_favorite))
        } else {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_favorite_border))
        }
        ivBookmark.setOnClickListener {
            onBookmarkClick(news)
        }
    }

    class NewsViewHolder(val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(news: NewsDB) {
            binding.apply {
                tvTitle.text = news.title
                tvPublishItem.text = DateFormatter.formatDate(news.publishedAt)
                Glide.with(itemView.context)
                    .load(news.urlToImage)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(imageAvatarUrl)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailsActivity::class.java)
                    intent.putExtra(DetailsActivity.EXTRA_TITLE, news.title)
                    intent.putExtra(DetailsActivity.EXTRA_PUB, DateFormatter.formatDate(news.publishedAt))
                    intent.putExtra(DetailsActivity.EXTRA_DESC, news.description)
                    intent.putExtra(DetailsActivity.EXTRA_AUTH, news.author)
                    intent.putExtra(DetailsActivity.EXTRA_URL, news.url)
                    intent.putExtra(DetailsActivity.EXTRA_IMAGE, news.urlToImage)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<NewsDB> =
            object : DiffUtil.ItemCallback<NewsDB>() {
                override fun areItemsTheSame(oldUser: NewsDB, newUser: NewsDB): Boolean {
                    return oldUser.title == newUser.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: NewsDB, newUser: NewsDB): Boolean {
                    return oldUser == newUser
                }
            }
    }
}