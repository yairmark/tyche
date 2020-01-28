package za.co.tyche.dto

import java.time.LocalDate

data class Event(val description: String, val startDate: LocalDate, val endDate: LocalDate) {

    init {
        require(startDate < endDate) { "Start date must be before end date. StartDate: [${startDate}], EndDate: [${endDate}]" }
        require(description.isNotBlank()) { "Description cannot be empty" }
    }

}

