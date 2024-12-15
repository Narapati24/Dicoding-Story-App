package com.dicoding.dicodingstoryapp

import com.dicoding.dicodingstoryapp.data.response.ListStoryItem

object DataDummy {
    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100){
            val story = ListStoryItem(
                photoUrl = null,
                createdAt = null,
                name = "name $i",
                description = "description $i",
                lon = null,
                id = "id $i",
                lat = null
            )
            items.add(story)
        }
        return items
    }
}