import com.example.yournexthome.Model.City

data class CitiesResult(
    val help: String,
    val success: Boolean,
    val result: Result
)

data class Result(
    val include_total: Boolean,
    val limit: Int,
    val records_format: String,
    val resource_id: String,
    val total_estimation_threshold: Any?, // Can be null
    val records: List<City>
)