package com.fraggjkee.wetransfer.features.rooms

import android.content.res.Resources
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.fraggjkee.recycleradapter.RecyclerItem
import com.fraggjkee.wetransfer.BR
import com.fraggjkee.wetransfer.R
import com.fraggjkee.wetransfer.domain.Room
import javax.inject.Inject

class RoomItemViewModel @Inject constructor(
    private val resources: Resources
) {
    var room: Room? = null
        set(value) {
            field = value
            value?.let { updateWithData(it) }
        }

    var bookClickListener: ((Room) -> Unit)? = null

    val title = ObservableField("")
    val subtitle = ObservableField("")
    val imageUrl = ObservableField("")
    val bookButtonTitle = ObservableField("")
    val isBooked = ObservableBoolean(false)

    init {
        setBooked(false)
    }

    private fun updateWithData(room: Room) = with(room) {
        title.set(name)
        imageUrl.set(thumbnailUrl)
        subtitle.set(
            resources.getQuantityString(R.plurals.remaining_spots, spotsCount, spotsCount)
        )
        isBooked.set(
            room.spotsCount == 0
        )
    }

    fun setBooked(isBooked: Boolean) {
        this.room = room?.let { it.copy(spotsCount = it.spotsCount.dec()) }
        val titleResId =
            if (isBooked) R.string.booked
            else R.string.book
        this.isBooked.set(isBooked)
        bookButtonTitle.set(
            resources.getString(titleResId)
        )
    }

    fun onBookButtonClick() {
        bookClickListener?.invoke(
            requireNotNull(room)
        )
    }
}

fun RoomItemViewModel.toRecyclerItem() = RecyclerItem(
    data = this,
    layoutId = R.layout.item_room_cell,
    variableId = BR.viewModel
)