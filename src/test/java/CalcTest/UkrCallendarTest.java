package CalcTest;

import managers.UkrCalendar;

public class UkrCallendarTest {
    public UkrCallendarTest(){
        System.out.println("Дата: " + UkrCalendar.getCurrentFullDate());
        System.out.println("Часы: " + UkrCalendar.getCurrentHours());
        System.out.println("Минуты: " + UkrCalendar.getCurrentMinutes());
        System.out.println("День: " + UkrCalendar.getCurrentDay());
    }

}
