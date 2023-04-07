import com.loco.course.service.CourseService
import com.loco.course.ui.CourseController
import com.loco.course.ui.dto.CourseResponseDto
import com.loco.course.ui.dto.CoursesResponseDto
import com.loco.course.ui.dto.FullCourseRequestDto
import com.loco.course.ui.dto.OneCourseRequestDto
import com.loco.place.ui.dto.PlaceResponseDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class CourseControllerTest {

    @InjectMocks
    private lateinit var courseController: CourseController

    @Mock
    private lateinit var courseService: CourseService

    private lateinit var coursesResponseDto: CoursesResponseDto
    private lateinit var courseResponseDto: CourseResponseDto
    private lateinit var placeResponseDto: PlaceResponseDto

    @BeforeEach
    fun setUp() {
        placeResponseDto = PlaceResponseDto(
            "Sample Address", "Sample Address", "Sample Description",
            "Sample Phone", "Sample Place", "Sample Place", "Sample Road Address",
            "x", "y", "Sample Image")
        courseResponseDto = CourseResponseDto("Sample Course", "Sample Description", placeResponseDto,
            10000, "Sample Description")
        coursesResponseDto = CoursesResponseDto("강남/역삼/선릉", listOf(courseResponseDto), 10000)
    }

    @Test
    fun `createCourse should return CoursesResponseDto`() {
        val fullCourseRequestDto = FullCourseRequestDto("Sample Question", LocalDateTime.now(),
            LocalDateTime.now().plusHours(2), 10000, "강남/역삼/선릉")

        Mockito.`when`(courseService.createFullCourse(fullCourseRequestDto)).thenReturn(coursesResponseDto)

        val response: ResponseEntity<CoursesResponseDto> = courseController.createCourse(fullCourseRequestDto)

        assert(response.statusCode == HttpStatus.OK)
        assert(response.body == coursesResponseDto)

        Mockito.verify(courseService).createFullCourse(fullCourseRequestDto)
    }

    @Test
    fun `updateOneCourse should return CourseResponseDto`() {
        val oneCourseRequestDto = OneCourseRequestDto("Sample Course", "Sample Description",
            LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Sample Question", mutableListOf("Sample Place")
        )

        Mockito.`when`(courseService.createOneCourse(oneCourseRequestDto)).thenReturn(courseResponseDto)

        val response: ResponseEntity<CourseResponseDto> = courseController.updateOneCourse(oneCourseRequestDto)

        assert(response.statusCode == HttpStatus.OK)
        assert(response.body == courseResponseDto)

        Mockito.verify(courseService).createOneCourse(oneCourseRequestDto)
    }
}
