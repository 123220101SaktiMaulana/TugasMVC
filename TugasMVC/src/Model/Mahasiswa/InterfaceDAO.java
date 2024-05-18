/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.Mahasiswa;

import java.util.List;

/**
 *
 * @author Aibra
 */
public interface InterfaceDAO {
    public void Insert (ModelMahasiswa mahasiswa);
    
    public void Update (ModelMahasiswa mahasiswa);
    
    public void Delete (int id);
    
    public List<ModelMahasiswa> getAll();
}
