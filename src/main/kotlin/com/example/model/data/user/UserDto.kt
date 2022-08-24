package com.example.model.data.user

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class UserDto(
    val id: String? = null,
    val name : String,
    val email : String,
    val password : String
)