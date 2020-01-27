package za.co.tyche.service

import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.specs.FreeSpec


class EngineSpec : FreeSpec({

    "should fail" {
        false.shouldBeTrue()
    }

    "should pass" {
        false.shouldBeFalse()
    }

})
