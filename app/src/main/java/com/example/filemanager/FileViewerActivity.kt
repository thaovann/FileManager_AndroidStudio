package com.example.filemanager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.filemanager.databinding.ActivityFileViewerBinding
import java.io.File

class FileViewerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFileViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lấy đường dẫn file từ Intent
        val filePath = intent.getStringExtra(EXTRA_FILE_PATH)
        if (filePath == null) {
            finish()
            return
        }

        val file = File(filePath)
        if (file.exists() && file.isFile) {
            // Hiển thị nội dung file
            val content = file.readText()
            binding.textContent.text = content
        } else {
            binding.textContent.text = "Không thể đọc file"
        }
    }

    companion object {
        private const val EXTRA_FILE_PATH = "file_path"

        fun start(context: Context, file: File) {
            val intent = Intent(context, FileViewerActivity::class.java)
            intent.putExtra(EXTRA_FILE_PATH, file.path)
            context.startActivity(intent)
        }
    }
}