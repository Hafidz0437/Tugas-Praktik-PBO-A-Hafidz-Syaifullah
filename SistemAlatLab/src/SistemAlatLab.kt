// Program Peminjaman Alat Laboratorium

class AlatLab(
    val nama: String, // Public Get (karena 'val', tidak bisa diubah nilainya setelah dibuat)
    stokAwal: Int,
    kondisiAwal: String
) {
    // Syarat 1 & 2: Public Get, Private Set
    // Praktikan bisa mengecek stok dan kondisi, tapi TIDAK BISA merubahnya secara langsung
    var stok: Int = stokAwal
        private set

    var kondisi: String = kondisiAwal
        private set

    // Method resmi untuk cek ketersediaan
    fun tersedia(): Boolean {
        return stok > 0 && kondisi == "Baik"
    }

    // Syarat 3: Validasi Method (Peminjaman hanya bisa jika stok > 0 dan kondisi "Baik")
    fun pinjamAlat(): Boolean {
        if (stok > 0 && kondisi == "Baik") {
            stok-- // Stok dikurangi dari dalam class
            return true
        } else {
            return false
        }
    }

    // Method resmi untuk memperbaiki kondisi alat (mengubah dari dalam class)
    fun perbaikiAlat() {
        if (kondisi == "Rusak") {
            kondisi = "Baik"
            println("[Sistem] Alat '$nama' berhasil diperbaiki.")
        } else {
            println("[Sistem] Alat '$nama' sudah dalam kondisi baik.")
        }
    }
}

class Praktikan(val nama: String)

class Laboran(val nama: String) {
    fun verifikasiPeminjaman(alat: AlatLab): Boolean {
        // Menggunakan alat.nama (Public Get), bukan lagi alat.getNama()
        println("[Laboran] $nama sedang memverifikasi alat '${alat.nama}'...")
        Thread.sleep(2000) // Delay 2000 milidetik (2 detik) untuk simulasi verifikasi

        return if (alat.pinjamAlat()) {
            println("[Laboran] Peminjaman alat '${alat.nama}' disetujui.")
            true
        } else {
            println("[Laboran] Peminjaman ditolak. Alat '${alat.nama}' tidak tersedia atau rusak.")
            false
        }
    }
}

// --- Simulasi Program ---
fun main() {
    // Daftar alat laboratorium
    val daftarAlat = listOf(
        AlatLab("Osiloskop", 2, "Baik"),
        AlatLab("Multimeter", 0, "Baik"),
        AlatLab("Power Supply", 1, "Rusak")
    )

    // Input dari console
    print("Masukkan nama praktikan: ")
    val namaPraktikan = readlnOrNull() ?: ""
    val praktikan = Praktikan(namaPraktikan)

    println("\nDaftar Alat:")
    daftarAlat.forEachIndexed { i, alat ->
        // Menggunakan Public Get ala Kotlin: alat.nama, alat.stok, alat.kondisi
        println("${i + 1}. ${alat.nama} | Stok: ${alat.stok} | Kondisi: ${alat.kondisi}")
    }

    print("\nPilih nomor alat yang ingin dipinjam: ")
    val pilihanInput = readlnOrNull()?.toIntOrNull()

    if (pilihanInput != null && (pilihanInput - 1) in daftarAlat.indices) {
        val pilihan = pilihanInput - 1
        val alatDipilih = daftarAlat[pilihan]
        val laboran = Laboran("Pak Budi")

        println("\n[Praktikan] ${praktikan.nama} ingin meminjam '${alatDipilih.nama}'")

        // Coba merubah stok secara langsung (Uncomment baris di bawah ini untuk melihat Error Enkapsulasi)
        // alatDipilih.stok = 10 // ERROR: Cannot assign to 'stok': the setter is private in 'AlatLab'

        val status = laboran.verifikasiPeminjaman(alatDipilih)

        if (status) {
            println("[Sistem] Status pinjam: Disetujui")
            println("[Sistem] Sisa stok '${alatDipilih.nama}' sekarang: ${alatDipilih.stok}")
        } else {
            println("[Sistem] Status pinjam: Ditolak")
        }
    } else {
        println("Pilihan tidak valid.")
    }
}