package pe.com.vencedor.historiasdefamilias.entities;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Asahel on 24/08/2015.
 */
public class User  implements Parcelable {

    private String userId;
    private String nombres;
    private String email;
    private String password;
    private String telefono;
    private String facebookId;
    private int puntos;
    private int posicion;
    private Integer ganador;
    private String fechaCreacion;
    private int nivel;
    
    private Bitmap foto;

    public User(){};

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public Integer getGanador() {
        return ganador;
    }

    public void setGanador(Integer ganador) {
        this.ganador = ganador;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(nombres);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(telefono);
        dest.writeString(facebookId);
        dest.writeInt(puntos);
        dest.writeInt(posicion);
        dest.writeInt(ganador==null?0:ganador);
        dest.writeString(fechaCreacion);
        dest.writeInt(nivel);
    }

    private User(Parcel in){
        userId = in.readString();
        nombres = in.readString();
        email = in.readString();
        password = in.readString();
        telefono = in.readString();
        facebookId = in.readString();
        puntos = in.readInt();
        posicion = in.readInt();
        ganador = in.readInt();
        fechaCreacion = in.readString();
        nivel = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
