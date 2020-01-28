package za.co.tyche.stub

import java.time.LocalDateTime
import java.time.Month

object FixedDates {
    val now = LocalDateTime.of(2020, Month.JANUARY, 28, 0, 0, 0, 0)
    val nowPlus10Days = now.plusDays(10)
}