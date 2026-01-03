/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.DAO;
import java.sql.PreparedStatement;
import id.ac.unpas.goalfound.Model.Pemain;
import id.ac.unpas.goalfound.KoneksiDB;
/**
 *
 * @author Muhammad Fauzan nur
 */
public class PemainDAO {
    public void insert(Pemain p) throws Exception {
    String sql = """
        INSERT INTO pemain(id_tim, nama_pemain, npm, no_punggung)
        VALUES (?,?,?,?)
    """;
    PreparedStatement ps = KoneksiDB.configDB().prepareStatement(sql);
    ps.setInt(1, p.getIdTim());
    ps.setString(2, p.getNamaPemain());
    ps.setString(3, p.getNpm());
    ps.setInt(4, p.getNoPunggung());
    ps.executeUpdate();
}

}
