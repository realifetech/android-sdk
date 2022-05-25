package com.realifetech.sample.campaignAutomation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.realifetech.sample.R
import com.realifetech.sample.utils.loadImage
import com.realifetech.sdk.campaignautomation.data.model.BannerDataModel
import com.realifetech.sdk.campaignautomation.data.model.RLTItem
import kotlinx.android.synthetic.main.banner_view.view.*


class CampaignAdapter(private val list: List<RLTItem?>) :
    RecyclerView.Adapter<CampaignAdapter.ViewHolder>() {


    private var context: Context? = null

    //    /**
//     * Provide a reference to the type of views that you are using
//     * (custom ViewHolder).
//     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView = view.findViewById(R.id.title)
        val subtitle: TextView = view.findViewById(R.id.subtitle)
        val bannerImage: ImageView = view.findViewById(R.id.bannerImage)

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.banner_view, viewGroup, false)
        context = viewGroup.context
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val bannerDataModel = list[position]?.data as BannerDataModel
        viewHolder.title.text = bannerDataModel.title
        viewHolder.subtitle.text = bannerDataModel.subtitle
        viewHolder.subtitle.isVisible = !bannerDataModel.subtitle.isNullOrEmpty()
        viewHolder.title.isVisible = !bannerDataModel.title.isNullOrEmpty()
        viewHolder.bannerImage.loadImage(context, bannerDataModel.imageUrl)

        viewHolder.bannerImage.setOnClickListener {
            val uriUrl = Uri.parse(bannerDataModel.imageUrl)
            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
            context?.startActivity(launchBrowser)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = list.size

}