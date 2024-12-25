import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Hillary
{
    public static void main(String[]args)
    {
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            int counter = 10;
            @Override
            public void run()
            {
                if(counter > 0)
                {
                    String newAddress = generateAddress();
                    System.out.println(newAddress);
                    counter--;
                }
                else
                {
                timer.cancel();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 2 * 1000);
    }

    public static String generateAddress()
    {
        StringBuilder address = new StringBuilder();
        Random rand = new Random();
        
        int firstByte = rand.nextInt(256); 
        firstByte = firstByte | 0b00000010;
        address.append(String.format("%02x", firstByte));
        
        for(int i = 1; i < 6; i++)
        {
            int nextByte = rand.nextInt(256);
            address.append(":").append(String.format("%02x", nextByte));
        }
        
        return address.toString();
    }
}