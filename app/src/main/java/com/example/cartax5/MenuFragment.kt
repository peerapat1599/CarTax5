package com.example.cartax5


import android.annotation.SuppressLint
import android.app.Notification
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.cartax5.databinding.FragmentMenuBinding
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
@SuppressLint("ByteOrderMark")
class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    private lateinit var carInsertModel: CarInsertModels
    lateinit var notificationManager: NotificationManager
    var carIdNotification = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_menu, container, false
        )

        binding.floatingActionButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_menuFragment_to_intoFragment)
        }
        carInsertModel = ViewModelProviders.of(this).get(CarInsertModels::class.java)
        setData()
        //carInsertModel.clearAll()

        setHasOptionsMenu(true)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) { view
        super.onActivityCreated(savedInstanceState)
        notificationManager =
            this.context?.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        createNotificationChannel(
            "com.example.cartax5",
            "NotifyDemo",
            "FirstNotify"
        )

        view?.let { checkDateExpired(it) }
    }
    private fun createNotificationChannel(
        id: String, name: String,
        description: String
    ) {

        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(id, name, importance)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern =
            longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        notificationManager?.createNotificationChannel(channel)

    }

    private fun sendNotification(view: View) {
        val channelID = "com.example.cartax5"
        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(
                activity?.applicationContext,
                channelID
            )
                .setContentTitle("แจ้งเตือนการชำระเงิน")
                .setContentText("อีก 7 วันเลขทะเบียน ${carIdNotification} จะต้องชำระเงิน")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setChannelId(channelID)
                .build()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        notificationManager?.notify(101, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkDateExpired(view: View) {
        var array: ArrayList<HomeList> = ArrayList()
        Handler().postDelayed({
            carInsertModel.allCar.observe(this, Observer { t ->
                t.forEach {
                    array.add(HomeList(it.carId, it.dateExpired, it.price.toString()))
                }
            })
            array.forEach {
                val dateCurrent = getCurrentDateTime()
                val dateInString = dateCurrent.toString("yyyy/MM/dd")
                var a = LocalDate.of(
                    it.dateExpire.split("/")[2].toInt()-543, it.dateExpire.split("/")[1].toInt(),
                    it.dateExpire.split("/")[0].toInt()
                )
                var b =LocalDate.of(
                    dateInString.split("/")[0].toInt(), dateInString.split("/")[1].toInt(),
                    dateInString.split("/")[2].toInt()
                )
//                Log.i("date1", a.toString())
//                Log.i("date2", b.toString())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                   // Log.i("date3", (ChronoUnit.DAYS.between(b, a).toString()))
                    var calDate = (ChronoUnit.DAYS.between(b, a).toInt())
                    if(calDate < 7){
                        carIdNotification = it.carId
                       sendNotification(view)
                    }
                }
            }
        }, 1000)
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    private fun setData() {
        var array: ArrayList<HomeList> = ArrayList()
        var count = 0
        Handler().postDelayed({
            carInsertModel.allCar.observe(this, Observer { t ->
                t.forEach {
                    array.add(HomeList(it.carId, it.dateExpired, it.price.toString()))
                    count++
                }
                if (count == t.size) {
                    Log.i("array", array.toString())
                    binding.listMain.adapter =
                        ListViewAdpter(getActivity()?.applicationContext, array);
                }
            })
        }, 200)
        binding.listMain.setOnItemClickListener { parent, view, position, id ->
            var carIdSelected = bundleOf("carId" to array[position].carId)
            view.findNavController()
                .navigate(R.id.action_menuFragment_to_carFragment, carIdSelected)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item!!,
            view!!.findNavController()
        )
                || super.onOptionsItemSelected(item)
    }

}
