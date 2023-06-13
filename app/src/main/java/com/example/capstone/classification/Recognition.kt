package com.example.capstone.classification

data class Recognition(
    var id: String = "",
    var title: String = "",
    var confidence: Float = 0F,
    var calories: Int = 0
) {
    override fun toString(): String {
        return "Title = $title, Confidence = $confidence, Calories = $calories)"
    }
}
