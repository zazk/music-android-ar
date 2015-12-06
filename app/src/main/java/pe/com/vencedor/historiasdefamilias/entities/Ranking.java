package pe.com.vencedor.historiasdefamilias.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cesar on 8/19/15.
 */
public class Ranking implements Parcelable{
    private int id;
    private int type;
    private String username;
    private int points;

    public Ranking(){

    }

    public Ranking(int id, int type, String username, int points) {
        this.id = id;
        this.type = type;
        this.username = username;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(type);
        parcel.writeString(username);
        parcel.writeInt(points);
    }

    private Ranking(Parcel in) {
        id = in.readInt();
        type = in.readInt();
        username = in.readString();
        points = in.readInt();
    }

    public static final Creator<Ranking> CREATOR = new Creator<Ranking>() {
        public Ranking createFromParcel(Parcel source) {
            return new Ranking(source);
        }

        public Ranking[] newArray(int size) {
            return new Ranking[size];
        }
    };
}
