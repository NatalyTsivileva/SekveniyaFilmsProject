package com.tcivileva.nata.sekveniya.films.project.data

class FilmSkeleton:IAdapterData {

    override fun getAdapterItemType(): AdapterDataType {
        return AdapterDataType.SKELETON
    }

    override fun areItemsTheSame(newItem: IAdapterData): Boolean {
       return false
    }

    override fun areContentsTheSame(newItem: IAdapterData): Boolean {
        return false
    }
}