package hu.bozgab.megabackend.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class EmailDto(
    val subject: String,
    val message: String,
    @JsonProperty("send_to")
    val sendTo: String
)
