package com.sunasterisk.appchat.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class GroupMessages(
    @Embedded
    val group: Group,
    @Relation(
        parentColumn = "group_id",
        entityColumn = "from_group_id",
    )
    val groupMessages: List<Message>
)
