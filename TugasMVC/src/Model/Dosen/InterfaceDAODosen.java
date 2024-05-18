/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.Dosen;

import java.util.List;

/**
 *
 * @author Aibra
 */
public interface InterfaceDAODosen {
    public void Insert (ModelDosen dosen);
    
    public void Update (ModelDosen dosen);
    
    public void Delete (int id);
    
    public List<ModelDosen> getAll();
}
