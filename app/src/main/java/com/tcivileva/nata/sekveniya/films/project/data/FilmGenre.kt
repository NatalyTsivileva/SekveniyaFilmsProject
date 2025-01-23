package com.tcivileva.nata.sekveniya.films.project.data


data class FilmGenre(
    val genre:String,
    val isSelected:Boolean
):IAdapterData{

    override fun getAdapterItemType(): AdapterDataType {
        return AdapterDataType.GENRE
    }

    override fun areItemsTheSame(newItem: IAdapterData): Boolean {
        if(newItem !is FilmGenre) return false

        return newItem.isSelected==this.isSelected &&
                newItem.genre==this.genre
    }

    override fun areContentsTheSame(newItem: IAdapterData): Boolean {
        if(newItem !is FilmGenre) return false
        return this==newItem
    }

}