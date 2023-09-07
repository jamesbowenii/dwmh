package learn.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {
    private int id;
    private Guest guest;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal total;

    public Reservation(){

    }

    public Reservation(int id, Guest guest, LocalDate startDate, LocalDate endDate, BigDecimal total) {
        this.id = id;
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }



    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
