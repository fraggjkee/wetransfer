package com.fraggjkee.wetransfer.features.rooms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraggjkee.recycleradapter.RecyclerItem
import com.fraggjkee.wetransfer.data.RoomsRepository
import com.fraggjkee.wetransfer.domain.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val repository: RoomsRepository,
    private val itemViewModelProvider: Provider<RoomItemViewModel>
) : ViewModel() {

    private val _recyclerItems = MutableLiveData<List<RecyclerItem>>()
    val recyclerItems: LiveData<List<RecyclerItem>> = _recyclerItems

    init {
        viewModelScope.launch {
            loadRooms()
        }
    }

    private suspend fun loadRooms() {
        runCatching { repository.getRooms() }
            .onFailure { error ->
                // TODO handle error, show toast/snackbar, etc
            }
            .onSuccess { rooms -> onRoomsLoaded(rooms) }
    }

    private fun onRoomsLoaded(rooms: List<Room>) {
        _recyclerItems.value = rooms
            .map { room -> createItemViewModel(room) }
            .map { viewModel -> viewModel.toRecyclerItem() }
    }

    private fun createItemViewModel(room: Room): RoomItemViewModel {
        return itemViewModelProvider.get().apply {
            this.room = room
            this.bookClickListener = { room -> bookRoom(room) }
        }
    }

    private fun bookRoom(room: Room) = viewModelScope.launch {
        runCatching { repository.bookRoom(room) }
            .onFailure { error ->
                // TODO handle error, show toast/snackbar, etc
            }
            .onSuccess { onRoomBooked(room) }
    }

    private fun onRoomBooked(room: Room) {
        recyclerItems.value.orEmpty()
            .map { recyclerItem -> recyclerItem.data }
            .filterIsInstance<RoomItemViewModel>()
            .first { viewModel -> viewModel.room == room }
            .setBooked(true)
    }
}