package za.co.tyche.dto

import java.time.LocalDateTime

data class Event(val description: String, val startDate: LocalDateTime, val endDate: LocalDateTime) {

    init {
        require(startDate < endDate) { "Start date must be before end date. StartDate: [${startDate}], EndDate: [${endDate}]" }
        require(description.isNotBlank()) { "Description cannot be empty" }
    }

}

