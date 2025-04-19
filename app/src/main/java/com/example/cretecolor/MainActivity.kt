package com.example.cretecolor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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

        val currentColor = (binding.colorView.background as? ColorDrawable)?.color ?: Color.WHITE
        val r = Color.red(currentColor)
        val g = Color.green(currentColor)
        val b = Color.blue(currentColor)

        dialogBinding.seekRed.progress = r
        dialogBinding.seekGreen.progress = g
        dialogBinding.seekBlue.progress = b
        dialogBinding.textRed.text = r.toString()
        dialogBinding.textGreen.text = g.toString()
        dialogBinding.textBlue.text = b.toString()
        dialogBinding.preview.setBackgroundColor(currentColor)

       //обновил ползунки
        val updatePreview = {
            val rVal = dialogBinding.seekRed.progress
            val gVal = dialogBinding.seekGreen.progress
            val bVal = dialogBinding.seekBlue.progress

            dialogBinding.textRed.text = rVal.toString()
            dialogBinding.textGreen.text = gVal.toString()
            dialogBinding.textBlue.text = bVal.toString()
            dialogBinding.preview.setBackgroundColor(Color.rgb(rVal, gVal, bVal))
        }

        dialogBinding.seekRed.setOnSeekBarChangeListener(seekBarListener(updatePreview))
        dialogBinding.seekGreen.setOnSeekBarChangeListener(seekBarListener(updatePreview))
        dialogBinding.seekBlue.setOnSeekBarChangeListener(seekBarListener(updatePreview))

        val dialog = AlertDialog.Builder(this)
            .setTitle("Выберите цвет")
            .setView(dialogBinding.root)
            .setPositiveButton("OK") { _, _ ->
                val selectedColor = Color.rgb(
                    dialogBinding.seekRed.progress,
                    dialogBinding.seekGreen.progress,
                    dialogBinding.seekBlue.progress
                )
                binding.colorView.setBackgroundColor(selectedColor)
            }
            .setNegativeButton("Отмена", null)
            .create()

        dialog.show()
    }
    //обновление бара
    private fun seekBarListener(onChanged: () -> Unit) =
        object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                onChanged()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        }
}
