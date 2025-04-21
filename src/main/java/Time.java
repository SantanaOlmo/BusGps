import java.time.Instant;

public class Time {

    public static String getISOTime(){
        return Instant.now().toString();
    }



}
