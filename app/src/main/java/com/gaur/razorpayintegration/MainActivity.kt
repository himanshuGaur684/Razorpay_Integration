package com.gaur.razorpayintegration

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val amountEditText = findViewById<TextInputEditText>(R.id.amount)
        val process = findViewById<MaterialButton>(R.id.process_payment)



        process.setOnClickListener {

            val amount = amountEditText.text.toString().trim()

            if (amount.isEmpty()) return@setOnClickListener



            startPayment(amount.toInt())

        }


    }

    private fun startPayment(amount: Int) {
        val checkout = Checkout()
        checkout.setKeyID("your_key_here")
        try {
            val options = JSONObject()
            options.put("name", "Razorpay Integration")
            options.put("description", "Learning tutorial")

            //You can omit the image option to fetch the image from dashboard
            //   options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")

            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");

            /** Generated from backend **/
            //      options.put("order_id", "order_DBJOWzybf0sJbb");

            /** Pass in paise in INR  ( Example  Rs 5 = 500 paise ) **/
            options.put("amount", "${(amount.toInt() * 100)}")//pass amount in currency subunits


            options.put("prefill.email", "random@gmail.com")
            options.put("prefill.contact", "+919442009211")

            checkout.open(this, options)

        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_LONG)
                .show()
            e.printStackTrace()
        }

    }


    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Log.d(TAG, "onPaymentError: ${p0}")
        Log.d(TAG, "onPaymentError: ${p1}")


        Toast.makeText(this, "Payment Not Successful", Toast.LENGTH_SHORT).show()
    }
}