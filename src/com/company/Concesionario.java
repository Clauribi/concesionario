package com.company;

import java.util.*;

public class Concesionario {
    private HashMap<Integer, Exposicion> listadoExposiciones;
    private HashMap<String, Coche> listadoCochesTotalesDefinitivo;
    private HashMap<String, VendedorComision> listadoVendedores;
    private HashMap<String, Cliente> listadoClientes;
    private HashMap<String, Mecanico> listadoMecanicos;

    public HashMap<String, Mecanico> getListadoMecanicos() {
        return listadoMecanicos;
    }

    public HashMap<Integer, Exposicion> getListadoExposiciones() {
        return listadoExposiciones;
    }

    public HashMap<String, Coche> getListadoCochesTotalesDefinitivo() {
        return listadoCochesTotalesDefinitivo;
    }

    public HashMap<String, VendedorComision> getListadoVendedores() {
        return listadoVendedores;
    }

    public HashMap<String, Cliente> getListadoClientes() {
        return listadoClientes;
    }

    public Concesionario() throws ExceptionParametrosInvalidos {
        this.listadoExposiciones = new HashMap<>();
        this.listadoCochesTotalesDefinitivo = new HashMap<>();
        this.listadoClientes = new HashMap<>();
        this.listadoMecanicos = new HashMap<>();
        this.listadoVendedores = new HashMap<>();
        Exposicion exposicion = new Exposicion(1, "Calle Alvarado 2", 678345629);
        this.listadoExposiciones.put(1, exposicion);
    }
    public void cambiarCocheExposicion(String matricula, int numExpo) throws ExceptionParametrosInvalidos {
        Coche c = listadoCochesTotalesDefinitivo.get(matricula);
        Exposicion exposicion = listadoExposiciones.get(numExpo);
        if (c.getExposicion().getNumExposicion() == numExpo) {
            throw new ExceptionParametrosInvalidos("El cambio no se va a realizar porque el coche ya estaba en esa exposición");
        } else {
            c.cambiarExposicion(exposicion);
        }
    }

    public ArrayList<Coche> cochesReservadosCliente(String dni) {
        Cliente cliente = listadoClientes.get(dni);
        return cliente.getReservados();
    }

    public void cocheAReparar(String matricula, TipoReparacion tipo) throws ExceptionParametrosInvalidos {
        Coche coche = listadoCochesTotalesDefinitivo.get(matricula);
        if (enVenta(matricula)) {
            Reparacion reparacion = new Reparacion(tipo, coche);
            coche.getReparaciones().add(reparacion);
        } else throw new ExceptionParametrosInvalidos("El coche no está en venta, no se puede reparar.");
    }

    public void cocheReparado(String matricula) {
        Coche coche = listadoCochesTotalesDefinitivo.get(matricula);
        for (Reparacion r : coche.getReparaciones()) {
            if (!r.isResuelta()) {
                try {
                    r.resolver(coche);
                    System.out.println("RESUELTA*************");
                } catch (ExceptionParametrosInvalidos e) {
                    System.out.println(e.getMessage());                }
            }
        }
    }

    public void existeVendedor(String dni) throws ExceptionParametrosInvalidos {
        if (!listadoVendedores.containsKey(dni)) throw new ExceptionParametrosInvalidos("No existe vendedor.");
    }

    public void existeCliente(String dni) throws ExceptionParametrosInvalidos {
        if (!listadoClientes.containsKey(dni)) throw new ExceptionParametrosInvalidos("No existe el cliente.");
    }

    public void existeMecanico(String dni) throws ExceptionParametrosInvalidos {
        if (!listadoMecanicos.containsKey(dni)) throw new ExceptionParametrosInvalidos("No existe el mecánico.");
    }

    public void existeCoche(String matricula) throws ExceptionParametrosInvalidos {
        if (!listadoCochesTotalesDefinitivo.containsKey(matricula))
            throw new ExceptionParametrosInvalidos("No existe el coche.");
    }

    public void existeExposicion(int numExpo) throws ExceptionParametrosInvalidos {
        if (!listadoExposiciones.containsKey(numExpo))
            throw new ExceptionParametrosInvalidos("No existe la exposición.");
    }

    public void addVendedorComision(String nombre, String direccion, String dni, int telefono) throws ExceptionParametrosInvalidos {
        VendedorComision v1 = new VendedorComision(nombre, direccion, dni, telefono);
        this.listadoVendedores.put(dni, v1);
    }

    public void deleteVendedorComision(String dni) throws ExceptionParametrosInvalidos {
        VendedorComision v1 = listadoVendedores.get(dni);
        if (v1.getCochesVendidos().isEmpty()) {
            this.listadoVendedores.remove(dni);
        } else throw new ExceptionParametrosInvalidos("El vendedor tiene coches vendidos. NO SE PUEDE BORRAR.");
    }

    public void changeVendedorComision(String nombre, String direccion, String dni, int telefono) throws ExceptionParametrosInvalidos {
        VendedorComision vendedor = listadoVendedores.get(dni);
        vendedor.updateInfo(nombre, direccion, telefono);
    }

    public void addExposicion(int numExpo, String direccion, int telefono) throws ExceptionParametrosInvalidos {
        Exposicion ex1 = new Exposicion(numExpo, direccion, telefono);
        this.listadoExposiciones.put(numExpo, ex1);
    }

    public void deleteExposicion(int numExpo) {
        this.listadoExposiciones.remove(numExpo);
    }

    public void changeExposicion(int numExpo, String direccion, int telefono) throws ExceptionParametrosInvalidos {
        Exposicion exposicion = listadoExposiciones.get(numExpo);
        exposicion.updateInfo(direccion, telefono);
    }

    public ArrayList<Coche> listadoCochesExposicion(int numExpo) {
        ArrayList<Coche> listaCochesExpo = new ArrayList<>();
        for (Coche c : listadoCochesTotalesDefinitivo.values()) {
            if (c.getExposicion().getNumExposicion() == numExpo) {
                listaCochesExpo.add(c);
            }
        }
        return listaCochesExpo;
    }

    public void changeCocheExposicion(String matricula, int numExpo) throws ExceptionParametrosInvalidos {
        Coche coche = listadoCochesTotalesDefinitivo.get(matricula);
        Exposicion expo = listadoExposiciones.get(numExpo);
        coche.cambiarExposicion(expo);
    }

    public void addCoche(String marca, String modelo, String matricula, double compra, double venta, TipoCoche t, Exposicion exposicion) throws ExceptionParametrosInvalidos {
        Coche cocheNew = new Coche(marca, modelo, matricula, compra, venta, t, exposicion);
        this.listadoCochesTotalesDefinitivo.put(cocheNew.getMatricula(), cocheNew);
    }

    public void deleteCoche(String matricula) {
        this.listadoCochesTotalesDefinitivo.remove(matricula);
    }

    public void changeCoche(String marca, String modelo, String matricula, double compra, double venta, TipoCoche t, Exposicion exposicion) throws ExceptionParametrosInvalidos {
        Coche c = listadoCochesTotalesDefinitivo.get(matricula);
        c.updateInfo(marca, modelo, compra, venta, t, exposicion);
    }

    public void addCliente(String dni, String nombre, String direccion, int telefono) throws ExceptionParametrosInvalidos {
        Cliente c1 = new Cliente(nombre, direccion, dni, telefono);
        listadoClientes.put(dni, c1);
    }

    public void deleteCliente(String dni) throws ExceptionParametrosInvalidos {
        Cliente c1 = listadoClientes.get(dni);
        if (c1.getComprados().isEmpty() && c1.getReservados().isEmpty()) {
            this.listadoClientes.remove(dni);
        } else
            throw new ExceptionParametrosInvalidos("El cliente tiene coches reservados o comprados. NO SE PUEDE BORRAR.");
    }

    public void changeCliente(String nombre, String direccion, String dni, int telefono) throws ExceptionParametrosInvalidos {
        Cliente c = listadoClientes.get(dni);
        c.updateInfo(nombre, direccion, telefono);
    }

    public void addMecanico(String dni, String nombre, String direccion, int telefono) throws ExceptionParametrosInvalidos {
        Mecanico m1 = new Mecanico(nombre, direccion, dni, telefono);
        listadoMecanicos.put(dni, m1);
    }

    public void deleteMecanico(String dni) {
        this.listadoMecanicos.remove(dni);
    }

    public void changeMecanico(String nombre, String direccion, String dni, int telefono) throws ExceptionParametrosInvalidos {
        Mecanico m = listadoMecanicos.get(dni);
        m.updateInfo(nombre, direccion, telefono);
    }

    public String mostrarCliente(String matricula) {
        Coche coche = listadoCochesTotalesDefinitivo.get(matricula);
        return coche.getCliente().getInfo();
    }

    public String verListaClientes() {
        ArrayList<String> verLista = new ArrayList<>();
        for (Cliente c : listadoClientes.values()) {
            verLista.add(c.getInfo());
        }
        return verLista.toString();
    }

    public String verListaMecanicos() {
        ArrayList<String> verLista = new ArrayList<>();
        for (Mecanico m : listadoMecanicos.values()) {
            verLista.add(m.getInfo());
        }
        return verLista.toString();
    }

    public String verCochesExpo(int numExpo) {
        ArrayList<String> datosCoches = new ArrayList<>();
        for (Coche c : listadoCochesExposicion(numExpo)) {
            datosCoches.add(c.getInfo());
        }
        return datosCoches.toString();
    }

    public String verExpo(int numExpo) {
        Exposicion expo = listadoExposiciones.get(numExpo);
        return expo.getInfo();
    }

    public String verListaExposiciones() {
        ArrayList<String> verLista = new ArrayList<>();
        for (Exposicion expo : listadoExposiciones.values()) {
            verLista.add(expo.getInfo());
        }
        return verLista.toString();
    }

    public String verListaVendedores() {
        ArrayList<String> verLista = new ArrayList<>();
        for (VendedorComision v : listadoVendedores.values()) {
            verLista.add(v.getInfo());
        }
        return verLista.toString();
    }

    public String verCochesVenta() {
        ArrayList<String> cochesVenta = new ArrayList<>();
        for (Coche c : listadoCochesTotalesDefinitivo.values()) {
            if (c.getEstado() == EstadoCoche.enVenta) {
                cochesVenta.add(c.getInfo());
            }
        }
        return cochesVenta.toString();
    }

    public String verCochesReservados() {
        ArrayList<String> cochesReservados = new ArrayList<>();
        for (Coche c : listadoCochesTotalesDefinitivo.values()) {
            if (c.getEstado() == EstadoCoche.reservado) {
                cochesReservados.add(c.getInfo());
            }
        }
        return cochesReservados.toString();
    }

    public String verCochesReparacion() {
        ArrayList<String> cochesReparacion = new ArrayList<>();
        for (Coche c : listadoCochesTotalesDefinitivo.values()) {
            if (c.getEstado() == EstadoCoche.reparando) {
                cochesReparacion.add(c.getInfo());
            }
        }
        return cochesReparacion.toString();
    }

    public String verCochesVendidos() {
        ArrayList<String> cochesVendidos = new ArrayList<>();
        for (Coche c : listadoCochesTotalesDefinitivo.values()) {
            if (c.getEstado() == EstadoCoche.vendido) {
                cochesVendidos.add(c.getInfo());
            }
        }
        return cochesVendidos.toString();
    }

    public String verCochesConcesionario() {
        ArrayList<String> totalCoches = new ArrayList<>();
        for (Coche coche : listadoCochesTotalesDefinitivo.values()) {
            totalCoches.add(coche.getInfo());
        }
        return totalCoches.toString();
    }

    public void reservasCliente(Cliente cliente){
        for (Coche coche : cliente.getReservados()){
            coche.getInfo();
        }
    }

    public ArrayList<VendedorComision> listaVendedores() {
        Collection<VendedorComision> valores = this.listadoVendedores.values();
        ArrayList<VendedorComision> listadoTotalVendedores = new ArrayList<>(valores);
        return listadoTotalVendedores;
    }

    public ArrayList<Mecanico> listaMecanicos() {
        Collection<Mecanico> valores = this.listadoMecanicos.values();
        ArrayList<Mecanico> listadoTotalMecanicos = new ArrayList<>(valores);
        return listadoTotalMecanicos;
    }

    public ArrayList<Cliente> listaClientes() {
        Collection<Cliente> valores = this.listadoClientes.values();
        ArrayList<Cliente> listadoTotalClientes = new ArrayList<>(valores);
        return listadoTotalClientes;
    }

    public ArrayList<Coche> listaCochesReservados() {
        ArrayList<Coche> cochesReservados = new ArrayList<>();
        for (Coche coche : listadoCochesTotalesDefinitivo.values()) {
            if (coche.getEstado() == EstadoCoche.reservado) {
                cochesReservados.add(coche);
            }
        }
        return cochesReservados;
    }

    public ArrayList<Coche> listaCochesStock() {
        ArrayList<Coche> cochesStock = new ArrayList<>();
        for (Coche coche : listadoCochesTotalesDefinitivo.values()) {
            if (coche.getEstado() == EstadoCoche.enVenta) {
                cochesStock.add(coche);
            }
        }
        return cochesStock;
    }

    public ArrayList<Coche> listaCochesReparacion() {
        ArrayList<Coche> cochesReparacion = new ArrayList<>();
        for (Coche coche : listadoCochesTotalesDefinitivo.values()) {
            if (coche.getEstado() == EstadoCoche.reparando) {
                cochesReparacion.add(coche);
            }
        }
        return cochesReparacion;
    }

    public ArrayList<Coche> listaCochesVendidos() {
        ArrayList<Coche> cochesVendidos = new ArrayList<>();
        for (Coche coche : listadoCochesTotalesDefinitivo.values()) {
            if (coche.getEstado() == EstadoCoche.vendido) {
                cochesVendidos.add(coche);
            }
        }
        return cochesVendidos;
    }

    public ArrayList<Exposicion> listaExposiciones() {
        Collection<Exposicion> valores = this.listadoExposiciones.values();
        ArrayList<Exposicion> listadoTotalExposiciones = new ArrayList<>(valores);
        return listadoTotalExposiciones;
    }

    public boolean enVenta(String matricula) {
        Coche c = listadoCochesTotalesDefinitivo.get(matricula);
        if (c.getEstado() == EstadoCoche.enVenta) {
            return true;
        }
        return false;
    }
}


