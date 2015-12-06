package pe.com.vencedor.historiasdefamilias.entities;

/**
 * Created by cesar on 16/08/2015.
 */
public class Family {
    private int id;
    private String name;
    private String titleSong;

    public String getTitleSong() {
        return titleSong;
    }

    public void setTitleSong(String titleSong) {
        this.titleSong = titleSong;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    private int imagen;

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    private boolean complete;

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    private int nivel;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    private boolean status;

    public Family() {
    }

    public Family(int id, String name,int nivel,boolean status,boolean complete) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.nivel = nivel;
        this.complete = complete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
