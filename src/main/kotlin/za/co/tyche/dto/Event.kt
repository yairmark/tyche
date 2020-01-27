package za.co.tyche.dto

import java.time.LocalDate

data class Event(val description: String, val startDate: LocalDate, val endDate: LocalDate) {

    companion object {
        val EMPTY_EVENT = Event("", LocalDate.MIN, LocalDate.MAX)
    }
}

