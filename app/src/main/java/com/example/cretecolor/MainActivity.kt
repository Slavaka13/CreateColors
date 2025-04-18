package com.example.cretecolor
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.cretecolor.databinding.ActivityMainBinding
import com.example.cretecolor.databinding.DialogColorPickerBinding


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.openDialogButton.setOnClickListener {
            showColorDialog()
        }
    }

    private fun showColorDialog() {
        val dialogBinding = DialogColorPickerBinding.inflate(LayoutInflater.from(this))

        val dialog = AlertDialog.Builder(this)
            .setTitle("Выберите цвет")
            .setView(dialogBinding.root)
            .setPositiveButton("OK") { _, _ ->
                val color = getColorFromSeekBars(dialogBinding)
                binding.colorView.setBackgroundColor(color)
            }
            .setNegativeButton("Отмена", null)
            .create()

        val updatePreview = {
            val color = getColorFromSeekBars(dialogBinding)
            dialogBinding.preview.setBackgroundColor(color)
            dialogBinding.textRed.text = dialogBinding.seekRed.progress.toString()
            dialogBinding.textGreen.text = dialogBinding.seekGreen.progress.toString()
            dialogBinding.textBlue.text = dialogBinding.seekBlue.progress.toString()
        }

        dialogBinding.seekRed.setOnSeekBarChangeListener(simpleListener(updatePreview))
        dialogBinding.seekGreen.setOnSeekBarChangeListener(simpleListener(updatePreview))
        dialogBinding.seekBlue.setOnSeekBarChangeListener(simpleListener(updatePreview))

        dialog.show()
    }

    private fun getColorFromSeekBars(binding: DialogColorPickerBinding): Int {
        val r = binding.seekRed.progress
        val g = binding.seekGreen.progress
        val b = binding.seekBlue.progress
        return Color.rgb(r, g, b)
    }

    private fun simpleListener(onChanged: () -> Unit) =
        object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                onChanged()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        }
}
