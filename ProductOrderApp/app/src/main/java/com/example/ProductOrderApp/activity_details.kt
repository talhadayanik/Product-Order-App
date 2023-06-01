package com.example.ProductOrderApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide

class activity_details : AppCompatActivity() {

    lateinit var img : ImageView
    lateinit var txtTitle : TextView
    lateinit var txtRate : TextView
    lateinit var txtDesc : TextView
    lateinit var txtBrand : TextView
    lateinit var txtCategory: TextView
    lateinit var txtStock : TextView
    lateinit var txtDiscount : TextView
    lateinit var txtPrice : TextView
    lateinit var btnSepeteEkle : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        img = findViewById(R.id.imgDetail)
        txtTitle = findViewById(R.id.txtDetailTitle)
        txtRate = findViewById(R.id.txtDetailRate)
        txtDesc = findViewById(R.id.txtDetailDesc)
        txtBrand = findViewById(R.id.txtDetailBrand)
        txtCategory = findViewById(R.id.txtDetailCategory)
        txtStock = findViewById(R.id.txtDetailStock)
        txtDiscount = findViewById(R.id.txtDetailDiscount)
        txtPrice = findViewById(R.id.txtDetailPrice)
        btnSepeteEkle = findViewById(R.id.btnSiparisVer)

        Glide.with(this).load(MainActivity.selectedProduct.images.get(0)).into(img)
        txtTitle.text = MainActivity.selectedProduct.title
        txtRate.text = "${MainActivity.selectedProduct.rating} / 5"
        txtDesc.text = MainActivity.selectedProduct.description
        txtBrand.text = "Marka: ${MainActivity.selectedProduct.brand}"
        txtCategory.text = "Kategori: ${MainActivity.selectedProduct.category}"
        txtStock.text = "Stok: ${MainActivity.selectedProduct.stock}"
        txtDiscount.text = "%${MainActivity.selectedProduct.discountPercentage} indirim"
        txtPrice.text = "${MainActivity.selectedProduct.price} TL"

        btnSepeteEkle.setOnClickListener {
            activity_cart.cartProducts.add(MainActivity.selectedProduct)
            Toast.makeText(this, "Sepete eklendi", Toast.LENGTH_LONG).show()
        }
    }
}