import time

# Program Peminjaman Alat Laboratorium

class AlatLab:
    def __init__(self, nama, stok, kondisi):
        self.__nama = nama
        self.__stok = stok
        self.__kondisi = kondisi  # "Baik" atau "Rusak"

    # Getter resmi
    def get_nama(self):
        return self.__nama

    def get_stok(self):
        return self.__stok

    def get_kondisi(self):
        return self.__kondisi

    # Method resmi untuk cek ketersediaan
    def tersedia(self):
        return self.__stok > 0 and self.__kondisi == "Baik"

    # Method resmi untuk mengurangi stok (peminjaman)
    def pinjam_alat(self):
        if self.__stok > 0 and self.__kondisi == "Baik":
            self.__stok -= 1
            return True
        else:
            return False

    # Method resmi untuk memperbaiki kondisi alat
    def perbaiki_alat(self):
        if self.__kondisi == "Rusak":
            self.__kondisi = "Baik"
            print(f"[Sistem] Alat '{self.__nama}' berhasil diperbaiki.")
        else:
            print(f"[Sistem] Alat '{self.__nama}' sudah dalam kondisi baik.")


class Praktikan:
    def __init__(self, nama):
        self.nama = nama


class Laboran:
    def __init__(self, nama):
        self.nama = nama

    def verifikasi_peminjaman(self, alat: AlatLab):
        print(f"[Laboran] {self.nama} sedang memverifikasi alat '{alat.get_nama()}'...")
        time.sleep(2)  # Delay 2 detik untuk simulasi verifikasi
        if alat.pinjam_alat():
            print(f"[Laboran] Peminjaman alat '{alat.get_nama()}' disetujui.")
            return True
        else:
            print(f"[Laboran] Peminjaman ditolak. Alat '{alat.get_nama()}' tidak tersedia.")
            return False


# --- Simulasi Program ---
if __name__ == "__main__":
    # Daftar alat laboratorium
    daftar_alat = [
        AlatLab("Osiloskop", 2, "Baik"),
        AlatLab("Multimeter", 0, "Baik"),
        AlatLab("Power Supply", 1, "Rusak")
    ]

    # Input dari serial monitor (PyCharm console)
    nama_praktikan = input("Masukkan nama praktikan: ")
    praktikan = Praktikan(nama_praktikan)

    print("\nDaftar Alat:")
    for i, alat in enumerate(daftar_alat):
        print(f"{i+1}. {alat.get_nama()} | Stok: {alat.get_stok()} | Kondisi: {alat.get_kondisi()}")

    pilihan = int(input("\nPilih nomor alat yang ingin dipinjam: ")) - 1
    if 0 <= pilihan < len(daftar_alat):
        alat_dipilih = daftar_alat[pilihan]
        laboran = Laboran("Pak Budi")

        print(f"\n[Praktikan] {praktikan.nama} ingin meminjam '{alat_dipilih.get_nama()}'")
        status = laboran.verifikasi_peminjaman(alat_dipilih)

        if status:
            print(f"[Sistem] Status pinjam: Disetujui")
        else:
            print(f"[Sistem] Status pinjam: Ditolak")
    else:
        print("Pilihan tidak valid.")
