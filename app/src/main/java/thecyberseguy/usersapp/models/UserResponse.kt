package thecyberseguy.usersapp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") var id: Int = -1,
    @SerialName("name") var name: String = "",
    @SerialName("username") var username: String = "",
    @SerialName("email") var email: String = "",
    @SerialName("address") var address: Address = Address(),
    @SerialName("phone") var phone: String = "",
    @SerialName("website") var website: String = "",
    @SerialName("company") var company: Company = Company()
) : java.io.Serializable {
    val formattedId = "ID: $id"
    val formattedName = "Name: $name"
    val formattedUsername = "Username: $username"
    val formattedEmail = "Email: ${email.lowercase()}"
    val formattedPhone = "Phone: $phone"
    val formattedWebsite = "Website: $website"
}

@Serializable
data class Address(
    @SerialName("street") var street: String = "",
    @SerialName("suite") var suite: String = "",
    @SerialName("city") var city: String = "",
    @SerialName("zipcode") var zipcode: String = "",
    @SerialName("geo") var geo: Geo = Geo()
) {
    val fullAddress = "Address: $street, $suite, $city, $zipcode"
}

@Serializable
data class Geo(
    @SerialName("lat") var lat: String = "",
    @SerialName("lng") var lng: String = ""
)

@Serializable
data class Company(
    @SerialName("name") var name: String = "",
    @SerialName("catchPhrase") var catchPhrase: String = "",
    @SerialName("bs") var bs: String = ""
) {
    val fullCompany = "Company: $name, $catchPhrase, $bs"
}