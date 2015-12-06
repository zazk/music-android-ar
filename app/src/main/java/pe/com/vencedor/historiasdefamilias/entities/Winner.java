package pe.com.vencedor.historiasdefamilias.entities;

/**
 * Created by cesar on 18/08/2015.
 */
public class Winner {
    private int id;
    private String name;
    private int points;
    private String priceType;

    public Winner() {
    }

    public Winner(int id, String name, int points, String priceType) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.priceType = priceType;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }
}
