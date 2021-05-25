package com.kartikaymahajan.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_b_m_i.*
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    val METRIC_UNITS_VIEW="METRIC_UNITS_VIEW"
    val US_UNITS_VIEW="US_UNITS_VIEW"
    var currentVisibleView:String=METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i)

        setSupportActionBar(toolbar_bmi_activity)
        val actionBar=supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title="CALCULATE BMI"
        }
        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateUnits.setOnClickListener{
            if(currentVisibleView.equals(METRIC_UNITS_VIEW)){
                if(validateMetricsUnits()){
                    val heightValue: Float=etMetricUnitHeight.text.
                    toString().toFloat()/100
                    val weightValue:Float=etMetricUnitWeight.text.
                    toString().toFloat()

                    val bmi = weightValue/(heightValue*heightValue)
                    displayBMIResult(bmi)
                }else{
                    Toast.makeText(this@BMIActivity,
                        "Please enter valid values",Toast.LENGTH_SHORT).show()
                }
            }else{
                if(validateUsUnits()){
                    val usUnitHeightValueFeet:String=etUsUnitHeightFeet.text.toString()
                    val usUnitHeightValueInch:String=etUsUnitHeightInch.text.toString()
                    val usUnitWeightValue:Float=etUsUnitWeight.text.toString().toFloat()

                    val heightValue=usUnitHeightValueFeet.toFloat()*12 +
                            usUnitHeightValueInch.toFloat()

                    val bmi=703*(usUnitWeightValue/(heightValue*heightValue))
                    displayBMIResult(bmi)

                }else{
                    Toast.makeText(this@BMIActivity,
                        "Please enter valid values",Toast.LENGTH_SHORT).show()
                }

            }
        }

        makeVisibleMetricUnitsView()
        rgUnits.setOnCheckedChangeListener{group, checkedId ->
            if(checkedId==R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUsUnitsView()
            }
        }
    }

    private fun makeVisibleUsUnitsView(){
        currentVisibleView=US_UNITS_VIEW
        tilMetricUnitWeight.visibility=View.GONE
        tilMetricUnitHeight.visibility=View.GONE

        etUsUnitWeight.text!!.clear()
        etUsUnitHeightFeet.text!!.clear()
        etUsUnitHeightInch.text!!.clear()

        tilUsUnitWeight.visibility=View.VISIBLE
        llUsUnitsHeight.visibility=View.VISIBLE

        llDisplayBmiResult.visibility=View.INVISIBLE
    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView=METRIC_UNITS_VIEW
        tilMetricUnitWeight.visibility=View.VISIBLE
        tilMetricUnitHeight.visibility=View.VISIBLE

        etMetricUnitWeight.text!!.clear()
        etMetricUnitHeight.text!!.clear()

        tilUsUnitWeight.visibility=View.GONE
        llUsUnitsHeight.visibility=View.GONE

        llDisplayBmiResult.visibility=View.INVISIBLE
    }

    private fun displayBMIResult(bmi:Float){
        var bmiLabel:String=""
        var bmiDescription:String=""

        if(bmi.compareTo(15f)<=0){
            bmiLabel="very severly underweight"
            bmiDescription="Oops! you really need to take care of yourself.Eat More"
        }else if(bmi.compareTo(15f)>0 && bmi.compareTo(16f)<=0){

            bmiLabel="severly underweight"
            bmiDescription="Oops! you really need to take care of yourself"
        }else if(bmi.compareTo(16f)>0 && bmi.compareTo(18.5f)<=0){

            bmiLabel="underweight"
            bmiDescription="Oops! you really need to take care of yourself"
        }else if (bmi.compareTo(18.5f)>0 && bmi.compareTo(25f)<=0){

            bmiLabel="Normal"
            bmiDescription="Congratulations! you are in a good shape"
        }else if (bmi.compareTo(25f)>0 && bmi.compareTo(30f)<=0){
            bmiLabel="Overweight"
            bmiDescription="Oops! you really need to take care of yourself.Workout"
        }else if(bmi.compareTo(30f)>0 && bmi.compareTo(35f)<=0){
            bmiLabel="Moderately obese"
            bmiDescription="Oops! you really need to take care of yourself.Workout"
        }else if(bmi.compareTo(35f)>0 && bmi.compareTo(40f)<=0){
            bmiLabel="Severely obese"
            bmiDescription="Oops! you really need to take care of yourself.Workout"
        }else if(bmi.compareTo(40f)>0){
            bmiLabel="Very severely obese"
            bmiDescription="Oops! you really need to take care of yourself.Workout"
        }

        llDisplayBmiResult.visibility=View.VISIBLE

        val bmiValue=BigDecimal(bmi.toDouble())
            .setScale(2,RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text=bmiValue
        tvBMIType.text=bmiLabel
        tvBMIDescription.text=bmiDescription

    }

    private fun validateMetricsUnits():Boolean{
        var isValid=true

        if(etMetricUnitWeight.text.toString().isEmpty())
            isValid=false
        else if(etMetricUnitHeight.text.toString().isEmpty())
            isValid=false

        return isValid
    }

    private fun validateUsUnits():Boolean{
        var isValid=true

        when {
            etUsUnitWeight.text.toString().isEmpty() -> isValid=false
            etUsUnitHeightFeet.text.toString().isEmpty() -> isValid=false
            etUsUnitHeightInch.text.toString().isEmpty() -> isValid=false
        }

        return isValid
    }
}