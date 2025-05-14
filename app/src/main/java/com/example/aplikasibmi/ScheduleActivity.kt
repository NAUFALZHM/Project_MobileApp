package com.example.aplikasibmi

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ScheduleActivity : AppCompatActivity() {

    private lateinit var daySpinner: Spinner
    private lateinit var timeText: TextView
    private lateinit var setReminderButton: Button
//  jam dan menit
    private var selectedHour = 0
    private var selectedMinute = 0
//  nama hari
    private fun getDayOfWeek(dayName: String): Int {
        return when (dayName) {
            "Minggu" -> Calendar.SUNDAY
            "Senin" -> Calendar.MONDAY
            "Selasa" -> Calendar.TUESDAY
            "Rabu" -> Calendar.WEDNESDAY
            "Kamis" -> Calendar.THURSDAY
            "Jumat" -> Calendar.FRIDAY
            "Sabtu" -> Calendar.SATURDAY
            else -> Calendar.MONDAY
        }
    }
//  set alarm
    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    private fun setWeeklyAlarm(calendar: Calendar) {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        daySpinner = findViewById(R.id.spinnerDay)
        timeText = findViewById(R.id.textTime)
        setReminderButton = findViewById(R.id.buttonSetReminder)

        // Isi spinner dengan hari-hari
        val days = arrayOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, days)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        daySpinner.adapter = adapter

        timeText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePicker = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                this.selectedHour = selectedHour
                this.selectedMinute = selectedMinute
                timeText.text = String.format("%02d:%02d", selectedHour, selectedMinute)
            }, hour, minute, true)

            timePicker.show()
        }

        setReminderButton.setOnClickListener {
            val selectedDay = daySpinner.selectedItem.toString()
            val dayOfWeek = getDayOfWeek(selectedDay)

            val calendar = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_WEEK, dayOfWeek)
                set(Calendar.HOUR_OF_DAY, selectedHour)
                set(Calendar.MINUTE, selectedMinute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)

                // Kalau waktu yang dipilih sudah lewat minggu ini, jadwalkan minggu depan
                if (before(Calendar.getInstance())) {
                    add(Calendar.WEEK_OF_YEAR, 1)
                }
            }

            Toast.makeText(
                this,
                "Alarm diset untuk $selectedDay pukul %02d:%02d".format(selectedHour, selectedMinute),
                Toast.LENGTH_SHORT
            ).show()

            // TODO: panggil fungsi untuk set AlarmManager
        }

    }
}
