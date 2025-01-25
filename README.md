# Пример работы приложения
https://github.com/user-attachments/assets/eb541d8d-1133-432e-9854-122d14f2d6be

# О проекте
Android приложение для отображения списка фильмов и просмотра информации о них. Состоит из двух экранов:
* Список фильмов с фильтром;
* Подробная информация о фильме.
- Язык разработки: Kotlin
- Среда разработки: Android Studio
- Минимальная версия Android: 8.0
- Паттерн проектирования: MVVM
- Ориентация экрана: вертикальная, горизонтальная
- Библиотеки: Retrofit, OkHttp, Kotlin serialization, Room, Koin, Glide, Navigation, Timber
- Верстка: XML
  
# Работа с сетью
В проекте используется Retrofit. API-интерфейс выглядит следующим образом:
```kotlin
interface FilmsAPI {
    @GET("films.json")
    suspend fun getFilms():FilmsResponse
}
```
Для парсинга ответа с сервера используется модель данных:
```kotlin
@Serializable
data class FilmsResponse(
    val films: List<Film> = listOf()
) {
    @Serializable
    data class Film(
        val description: String = "",
        val genres: List<String> = listOf(),
        val id: Int = 0,
        @SerialName("image_url")
        val imageUrl: String = "",
        @SerialName("localized_name")
        val localizedName: String = "",
        val name: String = "",
        val rating: Double = 0.0,
        val year: Int = 0
    )
}
```
# Кэширование
Для кэширования данных используется база данных Room.
Список фильмов загружается с интернета, из списка выбирается информация о жанрах, о фильме и всё вставляется в базу данных - в 3 таблицы.
Таблица с информацией о фильме:
```kotlin
@Entity(tableName = "Film")
data class FilmEntity(
  @PrimaryKey
  val filmId:Int = 0,
  val localizedName:String = "",
  val name:String = "",
  val year:Int = 0,
  val rating:Double = 0.0,
  val image:String = "",
  val description:String = ""
)
```
Таблица с жанрами:
```kotlin
@Entity(tableName = "Genre")
data class GenreEntity (
    @PrimaryKey
    val genre: String
)
```
Кросс-таблица которая хранит связку "фильм+жанр"(многие ко многим):
```kotlin
@Entity(
    primaryKeys = ["filmId", "genre"],
    foreignKeys = [
        ForeignKey(
            entity = FilmEntity::class,
            parentColumns = ["filmId"],
            childColumns = ["filmId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
         ForeignKey(
             entity = GenreEntity::class,
             parentColumns = ["genre"],
             childColumns = ["genre"],
             onUpdate = ForeignKey.CASCADE,
             onDelete = ForeignKey.CASCADE
         )
    ]
)

data class FilmGenreCross(
    val filmId:Int,
    val genre: String
)
```
Так же используется модель данных для запроса из нескольких таблиц:
```kotlin
data class FilmWithGenres(
    @Embedded val film: FilmEntity,
    @Relation(
        parentColumn = "filmId",
        entityColumn = "genre",
        associateBy = Junction(FilmGenreCross::class)
    )
    val genres: List<GenreEntity>
)
```
# Корутины
В проекте используются корутины. Написана экстеншн-функция для Fragment, которая позволяет получить результат из StateFlow:
```kotlin
fun <T> Fragment.collectFlow(
    flow: Flow<T>,
    collector: FlowCollector<T>
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(collector)
        }
    }
}
```
# UI
В проекте используется шаблон презентационного слоя MVVM. Для UI используются модели данных Film и Genre, а так же FilmsWithHeader и GenreWithHeader. Последние две предназначены для отображения вложенных списков в адаптере.
Конвертацию данных по типу Response->Entity->Data осуществляет DataConverter. 
<br>В проекте так же используется ConcatAdapter, который объединяет адаптер для жанров и адаптер для фильмов.</br>
```kotlin
data class Film(
    val id:Int = 0,
    val name: String = "",
    val nameRu:String = "",
    val year: Int = 0,
    val rating: Double = 0.0,
    val image: String = "",
    val description: String = "",
    val genres: List<String> = listOf()
)
```
```kotlin
data class FilmsWithHeader(
    @StringRes val headerRes: Int,
    val films: List<Film>
)
```
```kotlin
data class Genre(
    val genre:String,
    val isSelected:Boolean
)
```
```kotlin
data class GenreWithHeader(
    @StringRes val headerRes:Int,
    val genres:List<Genre>
)
```
Так же в проекте выделена типографика, различные стили.
