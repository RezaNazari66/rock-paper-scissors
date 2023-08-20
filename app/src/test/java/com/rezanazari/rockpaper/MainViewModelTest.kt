import androidx.compose.ui.unit.dp
import com.rezanazari.rockpaper.MainViewModel
import com.rezanazari.rockpaper.game.GameEngine
import com.rezanazari.rockpaper.model.GameState
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var mockGameEngine: GameEngine

    @Before
    fun setup() {
        mockGameEngine = mockk(relaxed = true)
        viewModel = MainViewModel()
        viewModel.state.value = GameState(gameItemList = emptyList())
        viewModel.setScreenWidthAndInitializeItems(1080.dp) // Assuming screen width is 1080dp
        viewModel.setGameEngine(mockGameEngine)
    }

    @Test
    fun `toggleGameRunningState should start or pause game engine and update game state`() {
        // Given
        viewModel.state.value = viewModel.state.value.copy(isGameStarted = false)

        // When
        viewModel.toggleGameRunningState()

        // Then
        verify { mockGameEngine.startGame() }
        assert(viewModel.state.value.isGameStarted)

        // When
        viewModel.toggleGameRunningState()

        // Then
        verify { mockGameEngine.pauseGame() }
        assert(!viewModel.state.value.isGameStarted)
    }

    @Test
    fun `restartGame should restart game engine and update game state`() {
        // When
        viewModel.restartGame()

        // Then
        verify { mockGameEngine.restartGame() }
        assert(!viewModel.state.value.isGameStarted)
    }
}