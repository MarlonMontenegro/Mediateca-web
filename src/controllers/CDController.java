package controllers;

import dao.CDDAO;
import model.CDdeAudio;
import java.util.List;

public class CDController  {

    private CDDAO CDdao = new CDDAO();

    public boolean agregarCD(CDdeAudio cd) {
        return CDdao.agregar(cd);
    }

    public boolean actualizarCD(CDdeAudio cd) {
        return CDdao.actualizar(cd);
    }

    public boolean eliminarCD(int id) {
        return CDdao.eliminar(id);
    }

    public CDdeAudio buscarCDPorID(String id) {
        return CDdao.buscarPorID(id);
    }

    public List<CDdeAudio> listarTodosLosCDs() {
        return CDdao.listarTodos();
    }





}