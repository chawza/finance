import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.nabeelkm.finance.Database
import java.io.File
import kotlin.math.log

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        val file = File("database.db")
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:${file.absolutePath}")
        if (!file.exists()) {
            Database.Schema.create(driver)
        }
        return driver
    }
}