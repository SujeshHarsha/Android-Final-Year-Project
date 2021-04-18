package com.example.myfirst.assignment

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Vibrator
import android.provider.AlarmClock
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myfirst.assignment.bll.LoginBll
import com.example.myfirst.assignment.strictMode.StrictModeClass
import com.example.myfirst.assignment.ui.registration.ForgetActivity
import com.example.myfirst.assignment.ui.registration.RegistrationActivity

class LoginActivity : AppCompatActivity() {
    lateinit var Username: EditText
    lateinit var Password: EditText
    lateinit var Login: Button
    lateinit var tvSignup: TextView
    lateinit var forget: TextView
    var user = ""
    var pass = ""
    lateinit var myLayout: LinearLayout
    var animationDrawable: AnimationDrawable? = null
    var loginCheck: CheckBox? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Gyro()
        myLayout = findViewById<View>(R.id.myLayout) as LinearLayout
        animationDrawable = myLayout!!.background as AnimationDrawable
        animationDrawable!!.setEnterFadeDuration(4500)
        animationDrawable!!.setExitFadeDuration(4500)
        animationDrawable!!.start()
        Username = findViewById(R.id.username)
        Password = findViewById(R.id.password)
        loginCheck = findViewById(R.id.loginCheck)
        tvSignup = findViewById(R.id.tvSignup)
        forget = findViewById(R.id.forget)
        StrictModeClass.StrictMode()
        Proximity()
        chekckSharedPreferences()
        Login = findViewById(R.id.login)

        forget.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@LoginActivity, ForgetActivity::class.java)
            startActivity(intent)
        })


        tvSignup.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        })
        Login.setOnClickListener(View.OnClickListener {
            user = Username.getText().toString().trim { it <= ' ' }
            pass = Password.getText().toString().trim { it <= ' ' }
            if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)) {
                login()
            } else {
                if (TextUtils.isEmpty(user)) {
                    Username.setError("Enter Username")
                    vibrate()
                }
                if (TextUtils.isEmpty(pass)) {
                    Password.setError("Enter Password")
                    vibrate()
                }
                return@OnClickListener
            }
        })
    }

    fun Gyro() {
        val sensorManager =
            getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor =
            sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        val gyroEventListener: SensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.values[2] > 1.5f) {        // anticlockwise
                    Log.d("gyro", "tilted left")
                    Toast.makeText(this@LoginActivity, "tilted left", Toast.LENGTH_SHORT).show()
                } else if (event.values[2] < -1.5f) {     // clockwise
                    Toast.makeText(this@LoginActivity, "right tilted", Toast.LENGTH_SHORT).show()
                    val intent =
                        Intent(this@LoginActivity, RegistrationActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onAccuracyChanged(
                sensor: Sensor,
                accuracy: Int
            ) {
            }
        }
        //    register listener
        sensorManager.registerListener(gyroEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun vibrate() {
        val vibrator =
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(3000)
    }

    private fun setPreferences() {
        val sharedPreferences =
            getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", Username!!.text.toString().trim { it <= ' ' })
        editor.putString("password", Password!!.text.toString().trim { it <= ' ' })
        editor.commit()
        Toast.makeText(this, "Login Sucessfull", Toast.LENGTH_SHORT).show()
    }

    private fun login() {
        val loginBll = LoginBll()
        StrictModeClass.StrictMode()
        val res: Boolean = loginBll.checkUser(user, pass)
        Toast.makeText(this, "" + res, Toast.LENGTH_SHORT).show()
        if (loginBll.checkUser(user, pass)) {
            setPreferences()
            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, user)
            startActivity(intent)
            vibrate()
            finish()
        } else {
            Toast.makeText(this, "Either PhoneNumber or password is incorrect", Toast.LENGTH_SHORT)
                .show()
            Username!!.requestFocus()
            vibrate()
        }
    }

    private fun chekckSharedPreferences() {
        val sharedPreferences =
            getSharedPreferences("User", Context.MODE_PRIVATE)
        println("Username " + sharedPreferences.getString("username", ""))
        if (sharedPreferences.contains("username")) {
            println(sharedPreferences.getString("username", ""))
            Username!!.setText("" + sharedPreferences.getString("username", ""))
        }
        if (sharedPreferences.contains("password")) {
            Password!!.setText("" + sharedPreferences.getString("password", ""))
        }
    }

    fun Proximity() {
        val proximitySensorManager =
            getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val proximoty =
            proximitySensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        val proximityEventListener: SensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val distance = event.values[0]
                if (distance <= 2) {
                    // Toast.makeText(LoginActivity.this, "Please Keep The Device Far From You", Toast.LENGTH_SHORT).show();
                }
            }

            override fun onAccuracyChanged(
                sensor: Sensor,
                accuracy: Int
            ) {
            }
        }
        //    register listener
        proximitySensorManager.registerListener(
            proximityEventListener,
            proximoty,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }
}