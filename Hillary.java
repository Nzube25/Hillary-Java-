import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Hillary
{
    public static void main(String[]args)
    {
        ArrayList<String> usedMac = new ArrayList<>();
        
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
            {
                int counter = 10; //take out the counter and change to a boolean
                @Override
                public void run()
                {
                    if(counter > 0)
                    {
                        String newAddress;
                        do
                        {
                        newAddress = generateAddress();
                        }
                        while(usedMac.contains(newAddress));
                        usedMac.add(newAddress);
                        counter--;
                        setAddress(newAddress);  //remove comment signs when code is complete
                    }
                    else
                    {
                        timer.cancel();
                        System.out.println(usedMac);//testing purposes
                    }
                }
            };
        timer.scheduleAtFixedRate(task, 0, 2 * 1000); //change it upon completion of code
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

    public static void setAddress(String address)
    {
        String os = System.getProperty("os.name").toLowerCase();
        String interName = getInterfaceName();
        System.out.println(os + interName + "isUp");//testing purposes
        try
        {
            if(os.contains("win"))
            {
                String [] commands = {
                "powershell -Command \""
                + "$adapter = Get-WmiObject Win32_NetworkAdapter | Where-Object { $_.NetConnectionID -eq '" + interName + "' }; "
                + "$adapter.Disable(); "
                + "$adapter.SetMACAddress('" + address + "'); "
                + "$adapter.Enable();\""
                };
                
                for(String s : commands){
                    executeCommand(s);
                }
                //turn the interface down
                //change the mac address
                //turn the interface up
            }
            else if(os.contains("mac"))
            {
                String [] commands = {
                    "sudo ifconfig " + interName + " down",
                    "sudo ifconfig " + interName + " ether " + address,
                    "sudo ifconfig " + interName + " up",
                    };
                
                for(String s : commands){
                    executeCommand(s);
                }
                //turn the interface down
                //change the mac address
                //turn the interface up
            }
            else if(os.contains("nix") || os.contains("nux"))
            {
                String [] commands = {
                "sudo ifconfig " + interName + " down",
                "sudo ifconfig " + interName + " hw ether " + address,
                "sudo ifconfig " + interName + " up",
                };
                
                for(String s : commands){
                    executeCommand(s);
                }
                //turn the interface down
                //change the mac address
                //turn the interface up
            }
        }
        catch(Exception e )
        {
            e.printStackTrace();
        }
    }

    public static String getInterfaceName()
    {
        String name = "";
        try{
            // Get all network interfaces
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface netInterface = interfaces.nextElement();

                // Get the interface name
                String testName = netInterface.getName();
                //System.out.println("Interface Name: " + name);//testing purposes

                // Check if the interface is active
                if (netInterface.isUp()) {
                    //System.out.println("  Status: Up");//testing purposes

                    // Display the MAC address (if available) , for testing purposes
                    byte[] mac = netInterface.getHardwareAddress();
                    if (mac != null) {
                        /*for testing purposes
                        System.out.print("  MAC Address: ");
                        for (int i = 0; i < mac.length; i++) {
                            System.out.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : "");
                        }
                        System.out.println();
                        **/
                        if(netInterface.isLoopback() != true){
                            return testName;
                        }
                    }
                    
                    /*  Display the IP addresses associated with this interface
                    netInterface.getInetAddresses().asIterator().forEachRemaining(ip -> {
                                System.out.println("  IP Address: " + ip.getHostAddress());
                        });
                        
                } else {
                    System.out.println("  Status: Down");
                }
                System.out.println();*/
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } 
        return name;
    }
    
    private static void executeCommand(String command) throws IOException, InterruptedException
    {
        try {
            // Execute the command
            Process process = Runtime.getRuntime().exec(command);
            
            // Read the output from the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            
            // Wait for the process to complete
            int exitCode = process.waitFor();
            System.out.println("Command exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}