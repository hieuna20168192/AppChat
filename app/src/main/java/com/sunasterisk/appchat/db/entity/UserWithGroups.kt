package com.sunasterisk.appchat.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithGroups(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "group_id",
        associateBy = Junction(UserGroupCrossRef::class)
    )
    val chats: List<Group>
)

data class GroupWithUsers(
    @Embedded val group: Group,
    @Relation(
        parentColumn = "group_id",
        entityColumn = "user_id",
        associateBy = Junction(UserGroupCrossRef::class)
    )
    val users: List<User>
)
