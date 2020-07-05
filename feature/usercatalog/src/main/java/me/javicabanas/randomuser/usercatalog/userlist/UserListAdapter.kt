package me.javicabanas.randomuser.usercatalog.userlist

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_user.view.*
import kotlin.properties.Delegates
import me.javicabanas.randomuser.androidcommons.view.AutoUpdatableAdapter
import me.javicabanas.randomuser.androidcommons.view.inflate
import me.javicabanas.randomuser.androidcommons.view.setCircularImageUrl
import me.javicabanas.randomuser.androidcommons.view.setImageUrl
import me.javicabanas.randomuser.usercatalog.R

typealias ClickCallback = (String) -> Unit

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserViewHolder>(),
    AutoUpdatableAdapter {
    var onItemClick: ClickCallback? = null
    var items: List<UserUiItem> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { oldItem, newItem -> oldItem.id == newItem.id }
    }

    override fun getItemCount(): Int = items.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(parent.inflate(R.layout.item_user))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: UserUiItem) {
            with(itemView) {
                name.text = user.name
                city.text = user.city
                backgroundImage.setImageUrl(user.background)
                avatar.setCircularImageUrl(user.avatar)
                this.setOnClickListener { onItemClick?.invoke(user.id) }
            }
        }
    }
}
