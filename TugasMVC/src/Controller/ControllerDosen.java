/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Dosen.DAODosen;
import Model.Dosen.InterfaceDAODosen;
import Model.Dosen.ModelDosen;
import Model.Dosen.ModelTableDosen;
import View.Dosen.*;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Aibra
 */
public class ControllerDosen {
    ViewDataDosen halamanTable;
    InputDataDosen halamanInput;
    EditDataDosen halamanEdit;
    
    InterfaceDAODosen daoDosen;
    
     List<ModelDosen> daftarDosen;
     
     public ControllerDosen(ViewDataDosen halamanTable) {
        this.halamanTable = halamanTable;
        this.daoDosen = new DAODosen();
    }
    
    public ControllerDosen(InputDataDosen halamanInput) {
        this.halamanInput = halamanInput;
        this.daoDosen = new DAODosen();
    }
//    
    public ControllerDosen(EditDataDosen halamanEdit) {
        this.halamanEdit = halamanEdit;
        this.daoDosen = new DAODosen();
    }
    
    public void showAllDosen() {
        /*
          Mengambil daftar mahasiswa dari database, 
          kemudian disimpan ke dalam variabel bernama list.
         */
    
        daftarDosen = daoDosen.getAll();
       
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
        ModelTableDosen table = new ModelTableDosen(daftarDosen);
      
        // Mengisi tabel yang berada pada halaman Table Mahasiswa
        halamanTable.getTableDosen().setModel(table);
    }
     
   public void insertDosen() {
        try {
            // Membuat "mahasiswa baru" yang isinya masih kosong
            ModelDosen DosenBaru = new ModelDosen();
            
            /*
              Mengambil input nama dan nim menggunakan getter yang telah dibuat di view
              Nilai dari input kemudian disimpan ke dalam variabel "nama" dan "nim".
            */
            String nama = halamanInput.getInputNama();
            String nidn = halamanInput.getInputNidn();

            /*
              Mengecek apakah input dari nama atau nim kosong/tidak.
              Jika kosong, maka buatlah sebuah exception.
             */
            if ("".equals(nama) || "".equals(nidn)) {
                throw new Exception("Nama atau NIDN tidak boleh kosong!");
            }
            
            // Mengisi nama dan nim dari "mahasiswa baru" yang dibuat tadi.
            DosenBaru.setNama(nama);
            DosenBaru.setNidn(nidn);
            
            // Memasukkan "mahasiswa baru" ke dalam database.
            daoDosen.Insert(DosenBaru);
            
            // Menampilkan pop-up ketika berhasil mengedit data
            JOptionPane.showMessageDialog(null, "Dose baru berhasil ditambahkan.");

            halamanInput.dispose();
            new ViewDataDosen();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
   public void EditDosen(int id){
       try {
           
                ModelDosen Dosendiedit = new ModelDosen();
           
                String nama = halamanEdit.getInputNama();
                String nidn = halamanEdit.getInputNidn();

                if ("".equals(nama) || "".equals(nidn)) {
                    throw new Exception("Nama atau NIDN tidak boleh kosong!");
                }
                
                Dosendiedit.setId(id);
                Dosendiedit.setNama(nama);
                Dosendiedit.setNidn(nidn);
                
                daoDosen.Update(Dosendiedit);

                JOptionPane.showMessageDialog(null, "Berhasil mengedit data.");

                halamanEdit.dispose();
                new ViewDataDosen();
                
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage());
            }
   }

   public void deleteDosen(Integer baris){
       Integer id = (int) halamanTable.getTableDosen().getValueAt(baris, 0);
        String nama = halamanTable.getTableDosen().getValueAt(baris, 1).toString();

        int input = JOptionPane.showConfirmDialog(
                null, 
                "Hapus " + nama + "?", 
                "Hapus Dosen", 
                JOptionPane.YES_NO_OPTION
        );
        if (input == 0) {
           daoDosen.Delete(id);

            JOptionPane.showMessageDialog(null, "Berhasil menghapus data.");
            
            showAllDosen();
        }
   }
}
