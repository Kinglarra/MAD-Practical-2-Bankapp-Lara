package com.example.activity2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

open class BankAccount(private var balance: Double) {
    fun deposit(amount: Double): Double {
        if (amount > 0) balance += amount
        return balance
    }

    fun withdraw(amount: Double): Double {
        if (amount > 0 && amount <= balance) balance -= amount
        return balance
    }

    fun getBalance(): Double = balance
}

class SavingsAccount(balance: Double, private val interestRate: Double) : BankAccount(balance) {
    fun applyInterest(): Double {
        val interest = getBalance() * interestRate / 100
        deposit(interest)
        return getBalance()
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var balanceText: TextView
    private lateinit var amountInput: EditText
    private lateinit var depositButton: Button
    private lateinit var withdrawButton: Button
    private lateinit var interestButton: Button

    private val account = SavingsAccount(1000.0, 5.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        balanceText = findViewById(R.id.balanceText)
        amountInput = findViewById(R.id.amountInput)
        depositButton = findViewById(R.id.depositButton)
        withdrawButton = findViewById(R.id.withdrawButton)
        interestButton = findViewById(R.id.interestButton)

        updateBalance(account.getBalance())

        depositButton.setOnClickListener {
            updateBalance(account.deposit(getAmount()))
        }
        withdrawButton.setOnClickListener {
            updateBalance(account.withdraw(getAmount()))
        }
        interestButton.setOnClickListener {
            updateBalance(account.applyInterest())
        }
    }

    private fun getAmount(): Double = amountInput.text.toString().toDoubleOrNull() ?: 0.0

    private fun updateBalance(newBalance: Double) {
        balanceText.text = "Balance: $${"%.2f".format(newBalance)}"
    }
}
