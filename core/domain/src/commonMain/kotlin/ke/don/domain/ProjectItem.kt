package ke.don.domain

data class ProjectItem(
    val id: String,
    val title: String,
    val category: String,
    val dataSizeMb: Double,
    val status: String
)
