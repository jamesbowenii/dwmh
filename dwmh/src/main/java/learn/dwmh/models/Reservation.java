package learn.dwmh.models;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private int id;
    private Guest guest;
    private Host host;


    private Location location;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal total;

    public Reservation(){

    }

    public Reservation(int id, Guest guest, Host host, LocalDate startDate, LocalDate endDate, BigDecimal total) {
        this.id = id;
        this.guest = guest;
        this.host = host;
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

    public Host getHost(){return  host;}


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

    private boolean isWeekend(DayOfWeek dayOfWeek){
        boolean answer = false;
        if(dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == dayOfWeek.SATURDAY ){
            answer = true;
        }
        return answer;
    }
    public void setTotal(BigDecimal total){
        this.total = total;
    }

    public BigDecimal getTotal(){
        return total;
    }
    public void setHost(Host host) {
        this.host = host;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public BigDecimal calculateTotal(Location location) {
        BigDecimal weekdayRate = location.getStandardRate();
        BigDecimal weekendRate = location.getWeekendRate();
        LocalDate currentDate = startDate;
        BigDecimal rate = weekdayRate;
        total = BigDecimal.valueOf(0);
        DayOfWeek dayOfWeek = startDate.getDayOfWeek();
        while(currentDate.isBefore(endDate)){
           if(isWeekend(dayOfWeek)){
               rate = weekendRate;
           }else{
               rate = weekdayRate;
           }
           total = total.add(rate);
           dayOfWeek = dayOfWeek.plus(1);
           currentDate = currentDate.plus(1, ChronoUnit.DAYS);
        }
        return total;
    }


}
