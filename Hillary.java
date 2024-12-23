import java.util.Random;
public class Hillary
{
    public static void main(String[]args)
    {
        String newAddress = generateAddress();
        System.out.println(newAddress);
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