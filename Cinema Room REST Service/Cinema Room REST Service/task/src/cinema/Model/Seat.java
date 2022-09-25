package cinema.Model;

import org.springframework.stereotype.Component;

@Component
public class Seat {

    int row;
    int column;
    int price;

    public Seat() {
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = row <= 4 ? 10 : 8;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat)) return false;

        Seat seat = (Seat) o;
        if (getRow() != seat.getRow()) return false;
        return  (getColumn() == seat.getColumn()) ;
    }

    @Override
    public int hashCode() {
        int result = getRow();
        result = 31 * result + getColumn();
        result = 31 * result + getPrice();
        return result;
    }

    @Override
    public String toString() {
        return "ticket{" +
                "row=" + row +
                ", column=" + column +
                ", price=" + price +
                '}';
    }
}
