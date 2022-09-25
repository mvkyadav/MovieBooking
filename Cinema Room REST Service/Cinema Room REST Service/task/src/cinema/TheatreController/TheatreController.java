package cinema.TheatreController;

import cinema.Model.MovieTheatre;
import cinema.Model.Seat;
import cinema.Model.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


class Token {
    UUID token;

    public Token() {

    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}

@RestController
public class TheatreController {

    MovieTheatre movieTheatre;
    List<Ticket> tickets =  new ArrayList<>();

    public TheatreController() {
        movieTheatre = new MovieTheatre(9, 9);
    }

    @GetMapping("/seats")
    public MovieTheatre getAvailableSeats() {
        return movieTheatre;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@RequestBody Seat seat) {

        if(seat.getRow() > 9 || seat.getColumn() > 9 || seat.getRow() < 1 || seat.getColumn() < 1)
            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST);

        for (int i = 0; i < movieTheatre.getAvailable_seats().size(); i++) {
            if (seat.equals(movieTheatre.getAvailable_seats().get(i))) {
                Ticket ticket = new Ticket(UUID.randomUUID(),
                        movieTheatre.getAvailable_seats().get(i));
                tickets.add(ticket);
                movieTheatre.getAvailable_seats().remove(i);
                return new ResponseEntity<>(ticket, HttpStatus.OK);
            }
        }
       return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"),
               HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Token token) {
        for (int i = 0; i < tickets.size(); i++) {
            if(tickets.get(i).getToken().equals(token.getToken())){
                Seat seat = tickets.get(i).getTicket();
                tickets.remove(i);
                movieTheatre.getAvailable_seats().add(seat);
                return new ResponseEntity<>(Map.of("returned_ticket", seat), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("error", "Wrong token!"),
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getTickets")
    public ResponseEntity<?> getTickets() {
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @PostMapping("/stats")
    public ResponseEntity<?> statistics(@RequestParam(required = false, name = "password") String password) {

        if(password != null && password.equals("super_secret"))
        {
            int currentIncome = 0;
            for (Ticket ticket : tickets) currentIncome += ticket.getTicket().getPrice();
            Map<String, Integer> stats = new HashMap<>();
            stats.put("current_income", currentIncome);
            stats.put("number_of_available_seats", movieTheatre.getAvailable_seats().size());
            stats.put("number_of_purchased_tickets", tickets.size());
            return new ResponseEntity<>(stats, HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "The password is wrong!"),
                HttpStatus.valueOf(401));

    }
}
