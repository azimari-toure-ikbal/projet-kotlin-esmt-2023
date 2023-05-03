package com.esmt.projet.victodo.feature_synchronisation.data

import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat.startActivityForResult
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import com.esmt.projet.victodo.feature_task.domain.model.Task
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAuthIOException
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.EventDateTime
import com.google.api.services.calendar.model.Events
import java.io.IOException
import java.time.ZoneOffset
import java.util.*

private val SCOPES = listOf(CalendarScopes.CALENDAR)
const val REQUEST_AUTHORIZATION = 1001


fun getCredentials(context: Context): GoogleAccountCredential {
    return GoogleAccountCredential.usingOAuth2(
        context,
        SCOPES
    )
}

fun syncTasksWithCalendar(activity: Activity, taskList: List<TaskWithTagAndSubTask>) {
    val transport: HttpTransport = AndroidHttp.newCompatibleTransport()
    val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()

    val credential: GoogleAccountCredential = getCredentials(activity)
    val accountName = credential.selectedAccountName

    if (accountName != null) {
        val client: Calendar = Calendar.Builder(
            transport,
            jsonFactory,
            credential
        ).setApplicationName("VictoDO").build()

        for (task in taskList) {

            val event = Event().apply {
                summary = task.task.name
                description = task.task.note

            }

            try {
                val createdEvent = client.events().insert("primary", event).execute()
            } catch (e: GoogleJsonResponseException) {
                e.printStackTrace()
            } catch (e: GoogleAuthIOException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    } else {
        // Prompt the user to select an account.
        activity.startActivityForResult(
            credential.newChooseAccountIntent(),
            REQUEST_AUTHORIZATION
        )
    }
}