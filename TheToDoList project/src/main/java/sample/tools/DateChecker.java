package sample.tools;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateChecker {
    public static boolean isBehindCurrentDay(Timestamp timestamp, Timestamp currentTimestamp){
        if(timestamp.compareTo(currentTimestamp) < 0){
//            System.out.println(timestamp.compareTo(currentTimestamp));
            if(timestamp.toLocalDateTime().toLocalDate().getDayOfMonth() == currentTimestamp.toLocalDateTime().toLocalDate().getDayOfMonth() &&
                    timestamp.toLocalDateTime().toLocalDate().getMonth().equals(currentTimestamp.toLocalDateTime().toLocalDate().getMonth()) &&
                    timestamp.toLocalDateTime().toLocalDate().getYear() == currentTimestamp.toLocalDateTime().toLocalDate().getYear()){
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isBehindCurrentHour(int hour, int minutes, boolean currentDay){
        LocalTime now = LocalTime.now();
//        System.out.println(now.getHour() +", " + hour + ":" + now.getMinute() +", " + minutes);
        if(currentDay && (now.getHour() > hour || (now.getHour() == hour && now.getMinute() > minutes))){
            return true;
        }
        return false;
    }

    public static boolean checkCurrentDay(int month, int day, int year) {
        LocalDate now = LocalDate.now();
        if (now.getMonthValue()-1 == month && now.getDayOfMonth() == day && now.getYear() == year) {
            return true;
        }else{
            return false;
        }
    }
}
