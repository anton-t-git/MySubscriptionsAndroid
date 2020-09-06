package db

import androidx.room.*
import java.util.*

//@Entity
//data class User(
//    @PrimaryKey val uid: Int,
//    @ColumnInfo(name = "first_name") val firstName: String?,
//    @ColumnInfo(name = "last_name") val lastName: String?
//)

@Entity
data class Subscription(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "price_in_dollars") val priceInDollars: Short,
    @ColumnInfo(name = "pay_date") val payDate: Long
)
