// Import necessary libraries for Google Calendar API
import android.content.Context
import com.esmt.projet.victodo.feature_task.domain.model.Task
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.Events
import java.io.IOException
import java.util.*

private val SCOPES = listOf(CalendarScopes.CALENDAR)

fun getCredentials(context: Context): GoogleAccountCredential {
    return GoogleAccountCredential.usingOAuth2(
        context,
        SCOPES
    )
}

fun syncTasksWithCalendar(context: Context, taskList: List<Task>) {
    val transport: HttpTransport = AndroidHttp.newCompatibleTransport()
    val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()

    val credential: GoogleAccountCredential = getCredentials(context)

    val client: Calendar = Calendar.Builder(
        transport,
        jsonFactory,
        credential
    ).setApplicationName("VictoDO").build()

    for (task in taskList) {
        val event = Event().apply {
            summary = task.name
            description = task.note
//            start = DateTime(task.dueTime.time)
//            end = DateTime(task.endDate.time)
        }

        try {
            val createdEvent = client.events().insert("primary", event).execute()
        } catch (e: GoogleJsonResponseException) {
        } catch (e: IOException) {
        }
    }
}
