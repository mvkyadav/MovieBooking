package cinema.Model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MovieTheatre {

    int total_rows;
    int total_columns;

    ArrayList<Seat> available_seats = new ArrayList<>();

    public MovieTheatre() {
    }

    public MovieTheatre(int rows, int cols) {
        this.total_rows = rows;
        this.total_columns = cols;

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                available_seats.add(new Seat(i, j));
            }
        }
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public void setTotal_columns(int total_columns) {
        this.total_columns = total_columns;
    }

    public ArrayList<Seat> getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(ArrayList<Seat> available_seats) {
        this.available_seats = available_seats;
    }

    @Override
    public String toString() {
        return "MovieTheatre{" +
                "rows=" + total_rows +
                ", cols=" + total_columns +
                ", availableSeats=" + available_seats +
                '}';
    }
}
