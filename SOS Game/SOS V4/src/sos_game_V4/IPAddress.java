package sos_game_V4;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class IPAddress {
	private String localHost;
	
	String getIPAddress() {
		try {
			Enumeration e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration ee = n.getInetAddresses();
				while (ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();
					if (i.getHostAddress().startsWith("10.33")) {
						localHost = i.getHostAddress();
						break;
					}
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localHost;
	}
}
