package Model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class User():RealmObject() {
    @PrimaryKey
    var id : Long = 0
    var firstName : String ?= null
    var lastName : String ?= null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 24 * result + lastName.hashCode()
        return result
    }

    override fun toString(): String {
        return "User $firstName $lastName"
    }
}
