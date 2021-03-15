package com.example.newsappt1

fun main() {

    val database = Database()

    database.upsert(
        Contact("Bruno", "Mendonca", "BrunoMend", "999999999", 4)
    )
    database.upsert(
        Contact("André", "Mendonca", null, "989999999", 2)
    )
    database.upsert(
        Contact("Lucia", "Ferreira", "Lucinha", "988999999", 1)
    )

    println(database.getContact(1))
    println(database.getContact(2))
    println(database.getContact(3))

    val bruno = database.getContact(1)!!

    database.upsert(
        Contact(bruno.firstName, bruno.lastName, null, bruno.phone, bruno.id)
    )

    println(database.getContact(1))

    database.deleteContact(2)

    println(database.getContact(2) ?: "Contato não localizado")

    println(database.getContacts("Mendonca"))

    println(database.getAllContacts())

    val bruno1 = Contact("Bruno", "Mendonça", phone = "999999")
}

data class Contact(
    val firstName: String,
    val lastName: String,
    val nickName: String? = null,
    val phone: String,
    val id: Int? = null
)

class Database() {
    private val contacts: MutableMap<Int, Contact> = mutableMapOf()

    fun upsert(contact: Contact) {
        val newContact: Contact

        if(contact.id == null){
            newContact = contact.copy(id = incrementedId())
        } else {
            newContact = contact
        }

        contacts[contact.id ?: incrementedId()] = newContact
    }

    private fun incrementedId(): Int {
        var lastId: Int = contacts.keys.maxOrNull() ?: 0
        return ++lastId
    }

    fun getAllContacts(): List<Contact> = contacts.values.toList()

    fun getContact(id: Int): Contact? = contacts[id]

    fun deleteContact(id: Int) {
        contacts.remove(id)
    }

    fun getContacts(search: String): List<Contact> {
        return contacts.values.filter { contact ->
            contact.firstName.contains(search) ||
                    contact.lastName.contains(search) ||
                    contact.nickName?.contains(search) ?: false
        }
    }

}