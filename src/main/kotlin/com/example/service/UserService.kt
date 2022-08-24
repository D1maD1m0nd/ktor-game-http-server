package com.example.service

import com.example.model.data.user.User
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.id.toId

class PersonService {
    private val client = KMongo.createClient()
    private val database = client.getDatabase("user")
    private val userCollection = database.getCollection<User>()

    fun create(user: User): Id<User>? {
        userCollection.insertOne(user)
        return user.id
    }

    fun findAll(): List<User> =
        userCollection.find()
            .toList()

    fun findById(id: String): User? {
        val bsonId: Id<User> = ObjectId(id).toId()
        return userCollection
            .findOne(User::id eq bsonId)
    }

    fun findByName(name: String): List<User> {
        val caseSensitiveTypeSafeFilter = User::name regex name
        return userCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
    }

    fun updateById(id: String, request: User): Boolean =
        findById(id)
            ?.let { person ->
                val updateResult = userCollection.replaceOne(person.copy(name = request.name, email = request.email))
                updateResult.modifiedCount == 1L
            } ?: false

    fun deleteById(id: String): Boolean {
        val deleteResult = userCollection.deleteOneById(ObjectId(id))
        return deleteResult.deletedCount == 1L
    }
}