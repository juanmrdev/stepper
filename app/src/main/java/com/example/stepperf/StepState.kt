package com.example.stepperf

import androidx.annotation.DrawableRes

interface StepState {
    fun onCompleted()
    fun onFocus()
    fun onPendingFill()
    fun onClick(block: (Int) -> Unit)
}

interface StepNavigation {
    fun onClickStep()
    fun navigateTo()
}

enum class StepIconEnum(
    @DrawableRes val icon: Int
) {
    SHIPPING(R.drawable.ic_shipping),
    PICKUP(R.drawable.ic_pickup),
    PAYMENT(R.drawable.ic_payment),
    REVIEW(R.drawable.ic_review);

    val defaultIconColor = R.color.colorPrimary
    val defaultDisableColor = R.color.medium_gray
    val onCompleteIcon = R.drawable.ic_completed
}

/*class ShippingStep(
    val context: Context
) : StepNavigation {
    override fun onClickStep() {

    }

    override fun navigateTo() {

    }
}

class PickupStep(
    context: Context
) : StepNavigation {
    override val stepIconEnum: StepIconEnum = StepIconEnum.PICKUP

}

class PaymentStep(
    context: Context
) : StepNavigation {
    override val stepIconEnum: StepIconEnum = StepIconEnum.PAYMENT

}

class ReviewStep(
    context: Context
) : StepNavigation {
    override val stepIconEnum: StepIconEnum = StepIconEnum.REVIEW

}*/