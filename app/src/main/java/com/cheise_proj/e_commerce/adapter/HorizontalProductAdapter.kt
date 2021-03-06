package com.cheise_proj.e_commerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cheise_proj.e_commerce.R
import com.cheise_proj.e_commerce.di.module.glide.GlideApp
import com.cheise_proj.e_commerce.model.Product
import com.cheise_proj.e_commerce.utils.HorizontalAdapterOption
import com.cheise_proj.e_commerce.utils.ItemClickListener
import kotlinx.android.synthetic.main.list_horizontal_product.view.*

class HorizontalProductAdapter :
    ListAdapter<Product, HorizontalProductAdapter.ProductVh>(ProductDiff()) {
    private var itemClickListener: ItemClickListener<Pair<HorizontalAdapterOption, String?>>? = null

    fun setItemCallback(callback: ItemClickListener<Pair<HorizontalAdapterOption, String?>>?) {
        itemClickListener = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVh {
        return ProductVh(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_horizontal_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductVh, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }

    class ProductVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val discount = kotlin.math.floor(Math.random() * 40).toInt()
        fun bind(
            item: Product?,
            itemClickListener: ItemClickListener<Pair<HorizontalAdapterOption, String?>>?
        ) {
            with(itemView) {
                GlideApp.with(context).load(item?.imageUrl).centerCrop().into(img_item)
                tv_discount_banner.text =
                    context.getString(R.string.percentage_text_placeholder, -discount, "%")
                tv_rating_number.text = context.getString(R.string.bracket_text_placeholder, 10)
                tv_item_1.text = item?.productName
                tv_item_2.text = item?.productName
                tv_item_3.text = item?.unitPrice
                tv_item_4.text = item?.unitPrice
                this.setOnClickListener {
                    itemClickListener?.onClick(
                        Pair(
                            HorizontalAdapterOption.VIEW,
                            item?.productID
                        )
                    )
                }
                applyFavorite(itemClickListener, item?.productID)
            }
        }

        private fun View.applyFavorite(
            itemClickListener: ItemClickListener<Pair<HorizontalAdapterOption, String?>>?,
            productID: String?
        ) {
            fab_fav.setOnClickListener {
                fab_fav.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_favorite
                    )
                )
                itemClickListener?.onClick(Pair(HorizontalAdapterOption.FAVORITE, productID))
            }
        }
    }

}

