import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanager.R
import com.example.filemanager.databinding.ItemFileBinding
import java.io.File

class FileAdapter(
    private val files: List<File>,
    private val onItemClicked: (File) -> Unit
) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    inner class FileViewHolder(val binding: ItemFileBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val binding = ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = files[position]
        holder.binding.textFileName.text = file.name
        holder.binding.imageIcon.setImageResource(
            if (file.isDirectory) R.drawable.baseline_folder_24 else R.drawable.baseline_insert_drive_file_24
        )
        holder.binding.root.setOnClickListener { onItemClicked(file) }
    }

    override fun getItemCount(): Int = files.size
}
