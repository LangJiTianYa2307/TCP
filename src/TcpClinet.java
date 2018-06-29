import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpClinet {
	private String ip;
	private int port;
	public TcpClinet(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
		Client client = new Client();
		Thread t = new Thread(client);
		t.start();
	}
	
	class Client implements Runnable{

		@Override
		public void run() {
			Socket socket = null;
			BufferedReader br = null;
			BufferedWriter bw = null;
			try {
				socket = new Socket(ip,port);
				OutputStream output = socket.getOutputStream();
				bw = new BufferedWriter(new OutputStreamWriter(output));
				br = new BufferedReader(new InputStreamReader(System.in));
				String line = null;
				while((line = br.readLine()) != null) {
					System.out.println("向服务器发送数据" + line );
					bw.write(line);
					bw.newLine();
					bw.flush();
					if("exit".equals(line)) {
						break;
					}
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(bw != null) {
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
	}
	
	public static void main(String[] args) {
		new TcpClinet("192.168.1.108", 10000);
	}
}
