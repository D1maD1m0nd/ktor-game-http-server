package com.example.route

import com.example.model.data.user.User
import com.example.model.data.user.UserDto
import com.example.service.PersonService
import com.example.utils.ErrorResponse
import com.example.utils.mapper.toDto
import com.example.utils.mapper.toUser
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val service = PersonService()
fun Route.userRouting() {
    route("/user") {
        post {
            val request = call.receive<UserDto>()
            val user = request.toUser()
            service.create(user)
                ?.let { userId ->
                    call.response.headers.append("My-User-Id-Header", userId.toString())
                    call.respond(HttpStatusCode.Created)
                } ?: call.respond(HttpStatusCode.BadRequest, ErrorResponse.BAD_REQUEST_RESPONSE)
        }

        get {
            val userList =
                service.findAll()
                    .map(User::toDto)
            call.respond(userList)
        }

        get("/{id}") {
            val id = call.parameters["id"].toString()
            service.findById(id)
                ?.let { foundUser -> call.respond(foundUser.toDto()) }
                ?: call.respond(HttpStatusCode.NotFound, ErrorResponse.NOT_FOUND_RESPONSE)
        }

        get("/search") {
            val name = call.request.queryParameters["name"].toString()
            val foundUser = service.findByName(name).map(User::toDto)
            call.respond(foundUser)
        }

        put("/{id}") {
            val id = call.parameters["id"].toString()
            val userRequest = call.receive<UserDto>()
            val user = userRequest.toUser()
            val updatedSuccessfully = service.updateById(id, user)
            if (updatedSuccessfully) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse.BAD_REQUEST_RESPONSE)
            }
        }
        delete("/{id}") {
            val id = call.parameters["id"].toString()
            val deletedSuccessfully = service.deleteById(id)
            if (deletedSuccessfully) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound, ErrorResponse.NOT_FOUND_RESPONSE)
            }
        }
    }
}
