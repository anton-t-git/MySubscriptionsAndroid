package db

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single

@Dao
interface SubscriptionDao {
    @Query("SELECT * FROM subscription")
    fun getAll(): Single<List<Subscription>>
}
