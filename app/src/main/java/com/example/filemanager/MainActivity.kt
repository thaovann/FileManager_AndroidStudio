package com.example.filemanager
import FileAdapter
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filemanager.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentDirectory: File = Environment.getExternalStorageDirectory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        loadDirectory(currentDirectory)
        binding.buttonRoot.setOnClickListener {
            val rootDirectory = Environment.getExternalStorageDirectory()
            if (currentDirectory != rootDirectory) {
                loadDirectory(rootDirectory)
            } else {
                Toast.makeText(this, "Bạn đang ở thư mục gốc!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun loadDirectory(directory: File) {
        if (!directory.isDirectory) {
            Toast.makeText(this, "Không phải là thư mục!", Toast.LENGTH_SHORT).show()
            return
        }

        currentDirectory = directory
        binding.textPath.text = directory.absolutePath

        val files = directory.listFiles()?.sortedBy { it.isFile } ?: emptyList()
        val adapter = FileAdapter(files) { file ->
            if (file.isDirectory) {
                loadDirectory(file)
            } else if (file.isFile) {
                openFile(file)
            }
        }
        binding.recyclerView.adapter = adapter
    }

    private fun openFile(file: File) {
        if (file.extension == "txt") {
            val content = file.readText()
            Toast.makeText(this, content, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Không hỗ trợ định dạng này", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onBackPressed() {
        val rootDirectory = Environment.getExternalStorageDirectory()
        if (currentDirectory.parentFile != null && currentDirectory != rootDirectory) {
            loadDirectory(currentDirectory.parentFile!!)
        } else {
            super.onBackPressed()
        }
    }


}
