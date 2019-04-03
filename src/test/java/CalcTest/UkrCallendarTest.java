package CalcTest;

import managers.UkrCalendar;

public class UkrCallendarTest {
    public UkrCallendarTest(){
        System.out.println("Дата: " + UkrCalendar.getFullDate());
        System.out.println("Часы: " + UkrCalendar.getHours());
        System.out.println("Минуты: " + UkrCalendar.getMinutes());
        System.out.println("День: " + UkrCalendar.getDay());
    }

}
