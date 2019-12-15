package com.livingcode.test.celotest.storage.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val roomId: Int,
    val cell: String? = null,
    @Embedded(prefix = "dob_") val dob: Dob? = null,
    val email: String? = null,
    val gender: String? = null,
    @Embedded(prefix = "id_") val id: Id? = null,
    @Embedded(prefix = "loc_") val location: Location? = null,
    @Embedded(prefix = "login_") val login: Login? = null,
    @Embedded(prefix = "name_") val name: Name? = null,
    val nat: String? = null,
    val phone: String? = null,
    @Embedded(prefix = "pic_") val picture: Picture? = null,
    @Embedded(prefix = "reg_") val registered: Registered? = null
)

data class Dob(
    val age: Int? = null,
    val date: String? = null
)

data class Id(
    val name: String? = null,
    val value: String? = null
)

data class Location(
    val city: String? = null,
    @Embedded(prefix = "coords_") val coordinates: Coordinates? = null,
    val postcode: String? = null,
    val state: String? = null,
    @Embedded(prefix = "street_") val street: Street? = null,
    @Embedded(prefix = "zone_") val timezone: Timezone? = null
)

data class Street(
    val number: Int? = null,
    val name: String? = null
)

data class Coordinates(
    val latitude: String? = null,
    val longitude: String? = null
)

data class Timezone(
    val description: String? = null,
    val offset: String? = null
)

data class Login(
    val md5: String? = null,
    val password: String? = null,
    val salt: String? = null,
    val sha1: String? = null,
    val sha256: String? = null,
    val username: String? = null,
    val uuid: String? = null
)

data class Name(
    val first: String? = null,
    val last: String? = null,
    val title: String? = null
)

data class Picture(
    val large: String? = null,
    val medium: String? = null,
    val thumbnail: String? = null
)

data class Registered(
    val age: Int? = null,
    val date: String? = null
)