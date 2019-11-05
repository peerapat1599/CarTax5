package com.example.cartax5


import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.cartax5.database.CarDatabaseModels
import com.example.cartax5.databinding.FragmentIntoBinding


/**
 * A simple [Fragment] subclass.
 */
class IntoFragment : Fragment() {

    private lateinit var binding: FragmentIntoBinding
    private lateinit var carInsertModel: CarInsertModels
    var txtSpinerValue = ""
    var price = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_into, container, false
        )
        carInsertModel = ViewModelProviders.of(this).get(CarInsertModels::class.java)
        setSpinner()

        onSave()
        return binding.root
    }

    private fun onEditDateChange() {
        if (binding.editText6.text.toString().split('/').size == 3
        ) {
            if (setTax()) {
                Handler().postDelayed({
                    carInsertModel.insert(
                        CarDatabaseModels(
                            binding.editText1.text.toString(),
                            txtSpinerValue,
                            binding.editText3.text.toString(),
                            binding.editText4.text.toString(),
                            binding.editText5.text.toString().toInt(),
                            binding.editText6.text.toString(),
                            price
                        )
                    )
                    Toast.makeText(getActivity(), "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_LONG).show()
                    view?.findNavController()?.navigate(R.id.action_intoFragment_to_menuFragment)
                }, 100)
            }
        } else {
            Toast.makeText(getActivity(), "กรุณากรอกวันที่ (xx/xx/xxxx)", Toast.LENGTH_LONG).show()
        }
    }

    private fun onSave() {
        binding.btnSave.setOnClickListener {
            //Log.i("value","${txtId},${txtSpinerValue},${txtCC},${txtWeight},${txtDateRegister},${txtDateExpire},")
            if (!binding.editText1.text.isEmpty() && txtSpinerValue !== "" && !binding.editText3.text.isEmpty() &&
                !binding.editText4.text.isEmpty() && !binding.editText5.text.isEmpty() && !binding.editText6.text.isEmpty()
            ) {
                if (binding.editText3.text.toString().matches("-?\\d+(\\.\\d+)?".toRegex()) && binding.editText4.text.toString().matches(
                        "-?\\d+(\\.\\d+)?".toRegex()
                    ) && binding.editText5.text.toString().matches("-?\\d+(\\.\\d+)?".toRegex())
                ) {
                    onEditDateChange()
                } else {
                    Toast.makeText(getActivity(), "กรอกเฉพาะตัวเลขเท่านั้น", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setTax(): Boolean {
        if (txtSpinerValue == "ดำ") {
            if (binding.editText3.text.toString().toInt() in 1..600) {
                price = binding.editText3.text.toString().toInt() * 0.5
            } else if (binding.editText3.text.toString().toInt() in 601..1800) {
                price = binding.editText3.text.toString().toInt() * 1.5
            } else {
                price = binding.editText3.text.toString().toInt() * 4.0
            }
            Handler().postDelayed({
                if (binding.editText5.text.toString().toInt() == 6) {
                    price -= (price * 0.1)
                } else if (binding.editText5.text.toString().toInt() == 7) {
                    price -= (price * 0.2)
                } else if (binding.editText5.text.toString().toInt() == 8) {
                    price -= (price * 0.4)
                } else if (binding.editText5.text.toString().toInt() == 9) {
                    price -= (price * 0.5)
                } else if (binding.editText5.text.toString().toInt() >= 10) {
                    price -= (price * 0.6)
                }
            }, 100)
            return true
        } else if (txtSpinerValue == "เขียว") {
            if (binding.editText4.text.toString().toInt() in 501..750) {
                price = 450.0
            } else if (binding.editText4.text.toString().toInt() in 751..1000) {
                price = 600.0
            } else if (binding.editText4.text.toString().toInt() in 1001..1250) {
                price = 750.0
            } else if (binding.editText4.text.toString().toInt() in 1251..1500) {
                price = 900.0
            } else if (binding.editText4.text.toString().toInt() in 1501..1750) {
                price = 1050.0
            } else if (binding.editText4.text.toString().toInt() in 1751..2000) {
                price = 1350.0
            } else {
                price = 1650.0
            }
            return true
        } else {
            if (binding.editText4.text.toString().toInt() in 1..1800) {
                price = 1300.0
            } else if (binding.editText4.text.toString().toInt() > 1800) {
                price = 1600.0
            }
            return true
        }
    }

    private fun setSpinner() {
        val colors = arrayOf("ดำ", "เขียว", "น้ำเงิน")
        val adapter = getActivity()?.applicationContext?.let {
            ArrayAdapter(
                it, // Context
                android.R.layout.simple_spinner_item,
                colors // Array
            )
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                txtSpinerValue = parent?.getItemAtPosition(position).toString()

            }
        }
    }

}
