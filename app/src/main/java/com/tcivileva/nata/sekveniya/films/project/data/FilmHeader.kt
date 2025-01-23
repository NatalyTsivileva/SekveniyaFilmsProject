package com.tcivileva.nata.sekveniya.films.project.data

data class FilmHeader(
    val text:String
):IAdapterData {
    override fun getAdapterItemType(): AdapterDataType {
        return AdapterDataType.HEADER
    }

    override fun areItemsTheSame(newItem: IAdapterData): Boolean {
        if(newItem !is FilmHeader) return false
        return newItem.text == this.text
    }

    override fun areContentsTheSame(newItem: IAdapterData): Boolean {
        if(newItem !is FilmHeader) return false
        return newItem==this
    }
}