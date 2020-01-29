package za.co.tyche.domain

import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FreeSpec
import za.co.tyche.stub.FixedDates

class EventTest : FreeSpec({

    "init_startDateAfterEndDate_throwsValidationError" {
        val errorMessage = shouldThrow<IllegalArgumentException> {
            Event(description = "foo", startDate = FixedDates.nowPlus10Days, endDate = FixedDates.now)
        }.message

        errorMessage.shouldBe("Start date must be before end date. StartDate: [${FixedDates.nowPlus10Days}], EndDate: [${FixedDates.now}]")
    }

    "init_blankDescription_throwsValidationError" - {
        listOf(
                "",
                "        ",
                "\t\t\n\t\n"
        )
                .map { invalidDescription ->
                    val errorMessage = shouldThrow<IllegalArgumentException> {
                        Event(description = invalidDescription, startDate = FixedDates.now, endDate = FixedDates.nowPlus10Days)
                    }.message

                    errorMessage.shouldBe("Description cannot be empty")
                }
    }

    "init_validParameters_buildsWithoutIssues"  {
        val event = Event(description = "foo bar", startDate = FixedDates.now, endDate = FixedDates.nowPlus10Days)

        event.shouldNotBeNull()
    }
})


