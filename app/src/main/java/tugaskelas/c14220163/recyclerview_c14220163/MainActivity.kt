package tugaskelas.c14220163.recyclerview_c14220163

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.content.Intent
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        _rvWayanng = findViewById <RecyclerView>(R.id.rvWayang)
        SiapkanData()
        TambahData()
        TampilData()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun SiapkanData(){
        _nama = resources.getStringArray(R.array.namaWayang).toMutableList()
        _deskripsi = resources.getStringArray(R.array.deskripsiWayang).toMutableList()
        _karakter = resources.getStringArray(R.array.karakterUtamaWayang).toMutableList()
        _gambar = resources.getStringArray(R.array.gambarWayang).toMutableList()
    }
    fun TambahData(){
        arWayang.clear()
        for (position in _nama.indices){
            val data =wayang(
                _gambar[position],
                _nama[position],
                _karakter[position],
                _deskripsi[position]
            )
            arWayang.add(data)
        }
    }
    fun TampilData(){
        _rvWayanng.layoutManager = LinearLayoutManager(this)
//        _rvWayanng.layoutManager = GridLayoutManager(this,2)
//        _rvWayanng.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        _rvWayanng.adapter = adapterRecView(arWayang)

        val adapterWayang = adapterRecView(arWayang)
        _rvWayanng.adapter = adapterWayang

        adapterWayang.setOnItemClickCallback(object : adapterRecView.OnItemClickCallback {
            override fun onItemClicked(data: wayang) {
                Toast.makeText(this@MainActivity, data.nama, Toast.LENGTH_SHORT).show()

                val intent = Intent (this@MainActivity,detWayang::class.java)
                intent.putExtra("kirimData",data)
                startActivity(intent)
            }

            override fun delData(pos: Int) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("HAPUS DATA")
                    .setMessage("Apakah benar data"+ _nama[pos] + "akan dihapus?")
                    .setPositiveButton(
                        "HAPUS", { dialog, which ->
                            _gambar.removeAt(pos)
                            _nama.removeAt(pos)
                            _deskripsi.removeAt(pos)
                            _karakter.removeAt(pos)
                            TambahData()
                            TampilData()
                        }
                    )
                    .setNegativeButton(
                        "BATAL", { dialog, which ->
                            Toast.makeText(
                                this@MainActivity,
                                "Data Batal Dihapus",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    ).show()
            }
        })
    }
}
//private lateinit var _nama : Array<String>
//private lateinit var _karakter : Array<String>
//private lateinit var _deskripsi : Array<String>
//private lateinit var _gambar : Array<String>

private lateinit var _nama : MutableList<String>
private lateinit var _karakter : MutableList<String>
private lateinit var _deskripsi : MutableList<String>
private lateinit var _gambar : MutableList<String>


private var arWayang = arrayListOf<wayang>()
private lateinit var _rvWayanng : RecyclerView