package com.example.utils.mapper

import com.example.model.data.user.User
import com.example.model.data.user.UserDto

fun User.toDto(): UserDto =
    UserDto(
        id = this.id.toString(),
        name = this.name,
        password = this.password,
        email = this.email
    )
fun UserDto.toUser(): User =
    User(
        name = this.name,
        email = this.email,
        password = this.password
    )