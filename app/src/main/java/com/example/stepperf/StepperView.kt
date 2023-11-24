package com.example.stepperf

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.stepperf.databinding.StepViewBinding
import com.example.stepperf.databinding.ViewStepperBinding

interface StepperViewController {
    fun initStepper(steps: List<StepIconEnum>)
}

class StepperViewControllerImpl: StepperViewController {

    private lateinit var stepperView: StepperView

    override fun initStepper(steps: List<StepIconEnum>) {
        stepperView.initStepper(steps)
    }
}


class StepperView(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), StepperViewController {

    companion object {
        const val FIRST_INDEX = 0
    }

    private val binding = ViewStepperBinding.inflate(LayoutInflater.from(context), this, true)

    private val steppersList: MutableList<StepView> = mutableListOf()

    override fun initStepper(steps: List<StepIconEnum>) {
        steps.forEach { step ->
            val stepView = StepView(context).apply {
                initIconStep(step)
            }
            steppersList.add(stepView)
            binding.container.addView(stepView)
        }

        initStep()
    }

    private fun initStep() {
        steppersList.forEach {
            it.onPendingFill()
            it.onClick(::validateStepStates)
        }
        validateStepStates(FIRST_INDEX)
        steppersList.last().lastIndexDivider()
        steppersList.first().onFocus()
    }

    private fun validateStepStates(currentIndex: Int) {
        steppersList[currentIndex].onFocus()

        steppersList.subList(FIRST_INDEX, currentIndex).forEach { stepView ->
            stepView.onCompleted()
        }

        if (currentIndex != steppersList.lastIndex) {
            steppersList.subList(currentIndex + 1, steppersList.lastIndex).forEach { stepView ->
                stepView.onPendingFill()
            }
        }
    }
}

class StepView(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), StepState {

    private val binding = StepViewBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var stepIconEnum: StepIconEnum
    private val onCompleteIcon: Int
        get() = stepIconEnum.onCompleteIcon

    private val ordinal: Int
        get() = stepIconEnum.ordinal


    fun initIconStep(step: StepIconEnum) {
        stepIconEnum = step
        setDrawable(stepIconEnum.icon)
    }

    override fun onCompleted() {
        setColor(stepIconEnum.onCompleteIcon)
        setDrawable(onCompleteIcon)
    }

    override fun onFocus() {
        binding.stepDrawableTextView.isEnabled = true
        setColor(stepIconEnum.defaultIconColor)
        setDrawable(stepIconEnum.icon)
    }

    override fun onPendingFill() {
        binding.stepDrawableTextView.isEnabled = true
        setColor(stepIconEnum.defaultDisableColor)
        setDrawable(stepIconEnum.icon)
    }

    override fun onClick(block: (Int) -> Unit) {
        binding.stepDrawableTextView.setOnClickListener {
            block(ordinal)
        }
    }

    private fun setDrawable(@DrawableRes drawable: Int) {
        binding.stepDrawableTextView.setCompoundDrawablesWithIntrinsicBounds(0, drawable, 0, 0)
    }

    private fun setDrawable(drawable: Drawable) {
        binding.stepDrawableTextView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
    }

    private fun setColor(@ColorInt color: Int) {
        val drawable: Drawable? = ContextCompat.getDrawable(context, stepIconEnum.icon)
        binding.stepDrawableTextView.setTextColor(color)
        drawable?.run {
            DrawableCompat.setTint(this, color)
            setDrawable(this)
        }

        binding.root.setBackgroundColor(color)

        binding.divider.setBackgroundColor(color)
    }

    fun lastIndexDivider() {
        binding.divider.visibility = GONE
    }
}