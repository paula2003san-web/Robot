package org.iesalandalus.programacion.robot.modelo;

import java.util.Objects;

public class Robot {

    private Coordenada coordenada;
    private Orientacion orientacion;
    private Zona zona;

    public Robot (){
        this.zona = new Zona(Zona.ANCHO_MINIMO, Zona.ALTO_MINIMO);
        this.orientacion = Orientacion.NORTE;
        this.coordenada = zona.getCentro();
    }

    public Robot (Zona zona){
        setZona(zona);
        this.orientacion = Orientacion.NORTE;
        this.coordenada = zona.getCentro();
    }

    public Robot (Zona zona, Orientacion orientacion){
        setZona(zona);
        setOrientacion(orientacion);
        this.coordenada = zona.getCentro();
    }

    public Robot (Zona zona, Orientacion orientacion, Coordenada coordenada){
        setZona(zona);
        setOrientacion(orientacion);
        setCoordenada(coordenada);
    }

    public Robot (Robot robot){
        if (robot == null){
            throw new NullPointerException("El robot no puede ser nulo.");
        }

        this.zona = new Zona(robot.zona.ancho(), robot.zona.alto());
        this.orientacion = robot.orientacion;
        this.coordenada = new Coordenada(robot.coordenada.x(), robot.coordenada.y());
    }

    public Zona getZona() {
        return zona;
    }

    private void setZona (Zona zona){
        if (zona == null){
            throw new NullPointerException("La zona no puede ser nula.");
        }
        this.zona = zona;
    }

    public Orientacion getOrientacion(){
        return orientacion;
    }

    private void setOrientacion (Orientacion orientacion){
        if (orientacion == null){
            throw new NullPointerException("La orientación no puede ser nula.");
        }
        this.orientacion = orientacion;
    }

    public Coordenada getCoordenada(){
        return coordenada;
    }

    private void setCoordenada (Coordenada coordenada){
        if (coordenada == null){
            throw new NullPointerException("La coordenada no puede ser nula.");
        }
        if (!zona.pertenece(coordenada)){
            throw new IllegalArgumentException("La coordenada no pertenece a la zona.");
        }
        this.coordenada = coordenada;
    }

    public void avanzar (){
        int x = coordenada.x();
        int y = coordenada.y();

        Coordenada nueva = switch (orientacion){
            case NORTE -> new Coordenada(x,y + 1);
            case NORESTE -> new Coordenada(x+1, y +1);
            case ESTE -> new Coordenada(x+1, y);
            case SURESTE -> new Coordenada(x+1, y-1);
            case SUR -> new Coordenada(x, y-1);
            case SUROESTE -> new Coordenada(x-1,y-1);
            case OESTE -> new Coordenada(x-1, y);
            case NOROESTE -> new Coordenada(x-1, y+1);
        };

        if (!zona.pertenece(nueva)){
            throw new RobotExcepcion("No se puede avanzar, ya que se sale de la zona.");
        }
        this.coordenada = nueva;
    }

    public void girarALaDerecha (){
        orientacion = switch (orientacion){
            case NORTE -> Orientacion.NORESTE;
            case NORESTE -> Orientacion.ESTE;
            case ESTE -> Orientacion.SURESTE;
            case SURESTE -> Orientacion.SUR;
            case SUR -> Orientacion.SUROESTE;
            case SUROESTE -> Orientacion.OESTE;
            case OESTE -> Orientacion.NOROESTE;
            case NOROESTE -> Orientacion.NORTE;
        };
    }

    public void girarALaIzquierda (){
        orientacion = switch (orientacion){
            case NORTE -> Orientacion.NOROESTE;
            case NORESTE -> Orientacion.NORTE;
            case ESTE -> Orientacion.NORESTE;
            case SURESTE -> Orientacion.ESTE;
            case SUR -> Orientacion.SURESTE;
            case SUROESTE -> Orientacion.SUR;
            case OESTE -> Orientacion.SUROESTE;
            case NOROESTE -> Orientacion.OESTE;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Robot robot = (Robot) o;
        return Objects.equals(coordenada, robot.coordenada) && orientacion == robot.orientacion && Objects.equals(zona, robot.zona);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordenada, orientacion, zona);
    }

    @Override
    public String toString() {
        return "Robot{" +
                "coordenada=" + coordenada +
                ", orientacion=" + orientacion +
                ", zona=" + zona +
                '}';
    }
}