# Hillary-Java-
MAC Address Changer
This Java application, Hillary, periodically changes the MAC address of an active network interface on a machine running Windows, MacOS, or Linux/Unix. It uses system-specific commands to disable the network interface, change the MAC address, and then re-enable the interface.

Features

Cross-Platform Support: Automatically detects the operating system and applies the correct commands for Windows, MacOS, or Linux/Unix.
Dynamic MAC Address Generation: Generates random, valid MAC addresses to be applied to the network interface.
Automated Task Scheduling: Uses a Timer to schedule MAC address changes at regular intervals.

How It Works

MAC Address Generation: The application generates a unique MAC address using the generateAddress method. This ensures the new MAC address is valid and hasn't been used in the current session.
Interface Management: The getInterfaceName method retrieves the name of an active, non-loopback network interface.
MAC Address Change: The setAddress method constructs and executes system commands to change the MAC address based on the operating system.
Periodic Execution: A Timer schedules the TimerTask to change the MAC address every two seconds (this interval can be modified).

Requirements

Java 8 or higher
Administrative or root privileges are required to execute commands that change the MAC address.

Compatible Operating Systems:

Windows 10 or later
MacOS
Linux/Unix distributions

Notes

Safety: Ensure you understand the implications of changing your MAC address, especially if you are connected to a network with MAC address filtering.
Testing: Some output and debug statements are included for testing purposes. These can be refined or replaced with proper logging mechanisms in a production environment.

Contribution

Feel free to fork this project and submit pull requests for improvements or additional features.

Disclaimer

Modifying the MAC address can have implications for network access and security. Use this tool responsibly and in compliance with local laws and regulations