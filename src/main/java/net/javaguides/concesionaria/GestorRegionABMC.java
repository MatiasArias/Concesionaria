package net.javaguides.concesionaria;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.javaguides.hibernate.dao.GestorHibernate;
import net.javaguides.hibernate.model.Region;

public class GestorRegionABMC {

    private List<Region> listaRegiones;
    RegionABMC pantallaRegion;
    GestorHibernate gestorHibernate = new GestorHibernate();
    GestorPaisABMC gestorPais;

    
    public GestorRegionABMC(GestorPaisABMC gestorPais) {
        pantallaRegion = new RegionABMC(this);
    }
    public void conocerGestorPais(GestorPaisABMC gestorPais) {
        this.gestorPais = gestorPais;
    }
    
    public void registrarRegion() {
        String nombre = pantallaRegion.getTxtNombre();
        Double porcentaje = pantallaRegion.getTxtPorcentaje(); 
        Region regionObject = new Region( nombre, porcentaje);
        //ps.setString(4, cboPais.getSelectedItem().toString());
        if (((nombre.length() != 0))) {
            gestorHibernate.saveObject(regionObject);
            JOptionPane.showMessageDialog(null, "DATOS GUARDADOS CORRECTAMENTE");
        } else {
            JOptionPane.showMessageDialog(null, "DEBE COMPLETAR TODOS LOS CAMPOS");
        }
        
         if (((porcentaje != 0))) {
            gestorHibernate.saveObject(regionObject);
            JOptionPane.showMessageDialog(null, "DATOS GUARDADOS CORRECTAMENTE");
        } else {
            JOptionPane.showMessageDialog(null, "DEBE COMPLETAR TODOS LOS CAMPOS");
        }
    }
    
    
    public void modificarRegion() {
        Region regionObject;
        regionObject = gestorHibernate.getObjectById("Region",Integer.parseInt(pantallaRegion.getTxtId()));
        regionObject.setNombre(pantallaRegion.getTxtNombre());
        regionObject.setPorcentaje(pantallaRegion.getTxtPorcentaje());
        
        
        if (true) {
            JOptionPane.showMessageDialog(null, "DATOS ACTUALIZADOS CORRECTAMENTE");
            gestorHibernate.updateObject(regionObject);
            mostrarDatos();
        } else {
            JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR DATOS");
        } 
    };
    
    public void conocerRegiones(){
        listaRegiones = gestorHibernate.getAllObjects("Region");
    }
    public List<Region> conocerListRegiones(){
        conocerRegiones();
        return listaRegiones;
    }
    public DefaultTableModel mostrarDatos() {
        this.conocerRegiones();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Porcentaje Recargo");
        String data[] = new String[3];
        try {for(Region region : listaRegiones){
                data[0] = Integer.toString(region.getId());
                data[1] = region.getNombre();
                data[2] = String.valueOf(region.getPorcentaje()); 
                modelo.addRow(data);
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar datos" + e);
        }
        return modelo;
    }
    
    public void eliminarRegion(){
        String id = pantallaRegion.getTxtId();
        int pantallaConfirmarEliminacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar esta region?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (pantallaConfirmarEliminacion == 0) {
            gestorHibernate.deleteObject("Region",Integer.parseInt(id));
            // si selecciona SI (primer boton) ejecuta la eliminacion
        } else {
            //No hace nada
        }
    }
    public void notificarGestor(GestorPaisABMC gestorSubscrito){
        gestorPais = gestorSubscrito;
    }
    public synchronized void notificarSubscriptores(){
        if (!(gestorPais == null))
            {        
                gestorPais.notificarActualizacionRegiones();
            }
    }
    public void mostrarPantalla() {
        pantallaRegion.setVisible(true);
    }

   
}
