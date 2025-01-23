package com.tcivileva.nata.sekveniya.films.project.data

interface IAdapterData {
    fun getAdapterItemType():AdapterDataType
    fun areItemsTheSame(newItem: IAdapterData): Boolean
    fun areContentsTheSame(newItem: IAdapterData): Boolean
}

enum class AdapterDataType {
    HEADER,
    GENRE,
    SKELETON
}