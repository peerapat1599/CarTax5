package com.example.cartax5


import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.cartax5.databinding.FragmentCarBinding

/**
 * A simple [Fragment] subclass.
 */
class CarFragment : Fragment() {

    private lateinit var binding: FragmentCarBinding
    private lateinit var carInsertModel: CarInsertModels

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_car, container, false
        )
        carInsertModel = ViewModelProviders.of(this).get(CarInsertModels::class.java)
        setData()
        onPressDeleteButton()
        return binding.root
    }

    private fun setData() {
        var carId = arguments?.getString("carId")
        binding.txtCarIdDetail.text = carId
        carInsertModel.allCar.observe(this, Observer { t ->
            t.forEach {
                if (it.carId == carId) {
                    binding.txtCarType.text = it.carType
                    binding.txtCarCC.text = it.carCC
                    binding.txtCarWeight.text = it.carWeight
                    binding.txtCarRegister.text = it.ageCar.toString()
                    binding.txtCarExpire.text = it.dateExpired
                    onPressPayButton()
                }
            }
        })
    }

    private fun onPressPayButton() {
        binding.btnPay.setOnClickListener {
            var date = binding.txtCarExpire.text.toString().split("/")
            Log.i("Newdate1", "${date[0]}/${date[1]}/${date[2]}")
            var newDate = date[0] + "/" + date[1] + "/" + (date[2].toInt() + 1).toString()
            var newAgeCar = binding.txtCarRegister.text.toString().toInt() + 1
            Log.i("Newdate2", newDate)
            //Log.i(binding.txtCarIdDetail.text.toString())
            Handler().postDelayed({
                carInsertModel.update(binding.txtCarIdDetail.text.toString(), newDate, newAgeCar)
                Toast.makeText(activity, "แก้ไขข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.navigate(R.id.action_carFragment_to_menuFragment)
            }, 200)
        }
    }

    private fun onPressDeleteButton() {
        binding.btnDelete.setOnClickListener {
            view
            dialogAlert(view)
        }
    }

    fun dialogAlert(view: View?) {
        val mAlertDialog = AlertDialog.Builder(activity)
        mAlertDialog.setIcon(R.mipmap.ic_launcher_round)
        mAlertDialog.setTitle("ลบข้อมูล")
        mAlertDialog.setMessage("คุณแน่ใจที่จะลบข้อมูลนี้หรือไม่")
        mAlertDialog.setPositiveButton("Yes") { dialog, id ->
            carInsertModel.clear(binding.txtCarIdDetail.text.toString())
            Toast.makeText(activity, "ลบข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show()
            view?.findNavController()?.navigate(R.id.action_carFragment_to_menuFragment)
        }
        mAlertDialog.setNegativeButton("No") { dialog, id ->
        }
        mAlertDialog.show()
    }

}
