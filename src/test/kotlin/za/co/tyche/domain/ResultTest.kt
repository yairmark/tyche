package za.co.tyche.domain

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class ResultTest : StringSpec({
    "toString_validInputs_formatsCorrectly" {
        val result = Result("Some result", 10.2, 5.6)

        "$result".shouldBe("Some result: Payout = R10.2, Takings = R5.6")
    }
})