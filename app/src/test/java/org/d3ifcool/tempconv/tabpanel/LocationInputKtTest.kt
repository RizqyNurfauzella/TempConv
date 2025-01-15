import androidx.compose.runtime.mutableStateOf
import org.junit.Assert.*
import org.junit.Test

class LocationInputTest {

    @Test
    fun testLocationInput() {
        val inputCity = mutableStateOf("")

        // Simulasi pengubahan input
        inputCity.value = "Bandung"
        assertEquals("Bandung", inputCity.value)

        // Simulasi pencarian
        var searchTriggered = false
        val onSearch = { searchTriggered = true }

        // Trigger pencarian
        onSearch()
        assertTrue(searchTriggered)
    }
}
