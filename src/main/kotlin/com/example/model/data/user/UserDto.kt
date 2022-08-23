package com.example.model.data.user

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class UserDto(
    @BsonId
    val id: String? = null,
    val name : String,
    val email : String,
    val password : String
)