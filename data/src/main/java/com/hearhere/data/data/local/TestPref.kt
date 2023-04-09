package com.hearhere.data.data.local

import com.hearhere.domain.model.TestModel
import kotlinx.serialization.Serializable


@Serializable
data class TestPref(val value : String="")

fun TestPref.toDomain() = TestModel(this.value)
fun TestModel.toPref() = TestPref(this.token)