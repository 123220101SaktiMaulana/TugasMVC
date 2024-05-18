/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Mahasiswa.DAO;
import Model.Mahasiswa.InterfaceDAO;
import Model.Mahasiswa.ModelMahasiswa;
import Model.Mahasiswa.ModelTable;
import View.Mahasiswa.EditData;
import View.Mahasiswa.InputData;
import View.Mahasiswa.ViewData;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Aibra
 */
public class Controllermahasiswa {
    ViewData halamanTable;
    InputData halamanInput;
    EditData halamanEdit;
    
    InterfaceDAO daoMahasiswa;
    
     List<ModelMahasiswa> daftarMahasiswa;
     
     public Controllermahasiswa(ViewData halamanTable) {
        this.halamanTable = halamanTable;
        this.daoMahasiswa = new DAO();
    }
    
    public Controllermahasiswa(InputData halamanInput) {
        this.halamanInput = halamanInput;
        this.daoMahasiswa = new DAO();
    }
    
    public Controllermahasiswa(EditData halamanEdit) {
        this.halamanEdit = halamanEdit;
        this.daoMahasiswa = new DAO();
    }
    
    public void showAllMahasiswa() {
        /*
          Mengambil daftar mahasiswa dari database, 
          kemudian disimpan ke dalam variabel bernama list.
         */
    
        daftarMahasiswa = daoMahasiswa.getAll();
       
        /* 
          Supaya daftarMahasiswa dapat dimasukkan ke dalam tabel, kita perlu 
          mengubah daftarMahasiswa yang memiliki tipe data List menjadi 
          tipe data Array Object. Jika pada pertemuan kemarin kita melakukannya
          secara manual menggunakan looping, kali ini kita tidak perlu melakukan hal tersebut,
          karena class ModelTabel sudah otomatis mengubahnya menjadi tipe data yang sesuai.
          
          Caranya kita hanya perlu membuat sebuah instance dari class ModelTable,
          kemudian kita masukkan variabel daftarMahasiswa sebagai parameter constructor
          supaya dapat diubah menjadi sebuah isi table yang dapat dimasukkan ke dalam tabel.
         */
        ModelTable table = new ModelTable(daftarMahasiswa);
      
        // Mengisi tabel yang berada pada halaman Table Mahasiswa
        halamanTable.getTableMahasiswa().setModel(table);
    }
     
   public void insertMahasiswa() {
        try {
            // Membuat "mahasiswa baru" yang isinya masih kosong
            ModelMahasiswa mahasiswaBaru = new ModelMahasiswa();
            
            /*
              Mengambil input nama dan nim menggunakan getter yang telah dibuat di view
              Nilai dari input kemudian disimpan ke dalam variabel "nama" dan "nim".
            */
            String nama = halamanInput.getInputNama();
            String nim = halamanInput.getInputNim();

            /*
              Mengecek apakah input dari nama atau nim kosong/tidak.
              Jika kosong, maka buatlah sebuah exception.
             */
            if ("".equals(nama) || "".equals(nim)) {
                throw new Exception("Nama atau NIM tidak boleh kosong!");
            }
            
            // Mengisi nama dan nim dari "mahasiswa baru" yang dibuat tadi.
            mahasiswaBaru.setNama(nama);
            mahasiswaBaru.setNim(nim);
            
            // Memasukkan "mahasiswa baru" ke dalam database.
            daoMahasiswa.Insert(mahasiswaBaru);
            
            // Menampilkan pop-up ketika berhasil mengedit data
            JOptionPane.showMessageDialog(null, "Mahasiswa baru berhasil ditambahkan.");
            
            // Terakhir, program akan pindah ke halaman Table Mahasiswa()
            halamanInput.dispose();
            new ViewData();
        } catch (Exception e) {
            // Menampilkan pop-up ketika terjadi error
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
   public void EditMahasiswa(int id){
       try {
           
                ModelMahasiswa mahasiswadiedit = new ModelMahasiswa();
           
                // Mengambil input nama dan nim dan disimpan ke dalam variabel
                String nama = halamanEdit.getInputNama();
                String nim = halamanEdit.getInputNim();

                /*
                  Mengecek apakah input dari nama atau nim kosong/tidak.
                  Jika kosong, maka buatlah sebuah exception.
                */
                if ("".equals(nama) || "".equals(nim)) {
                    throw new Exception("Nama atau NIM tidak boleh kosong!");
                }
                
                mahasiswadiedit.setId(id);
                mahasiswadiedit.setNama(nama);
                mahasiswadiedit.setNim(nim);
                
                daoMahasiswa.Update(mahasiswadiedit);

                // Menampilkan pop-up ketika berhasil mengedit data
                JOptionPane.showMessageDialog(null, "Berhasil mengedit data.");

                // Terakhir, program akan pindah ke halaman ViewData()
                halamanEdit.dispose();
                new ViewData();
                
            } catch (Exception exception) {
                // Menampilkan pop-up ketika terjadi error
                JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage());
            }
   }
   
   public void deleteMahasiswa(Integer baris){
       Integer id = (int) halamanTable.getTableMahasiswa().getValueAt(baris, 0);
        String nama = halamanTable.getTableMahasiswa().getValueAt(baris, 1).toString();

        // Membuat Pop-Up untuk mengonfirmasi apakah ingin menghapus data
        int input = JOptionPane.showConfirmDialog(
                null, 
                "Hapus " + nama + "?", 
                "Hapus Mahasiswa", 
                JOptionPane.YES_NO_OPTION
        );

        // Jika user memilih opsi "yes", maka hapus data.
        if (input == 0) {
            /* 
          Memanggil method delete() untuk menghaous data dari DB
          berdasarkan id yang dipilih.
             */
           daoMahasiswa.Delete(id);

            JOptionPane.showMessageDialog(null, "Berhasil menghapus data.");
            
            showAllMahasiswa();
        }
   }
    
}
