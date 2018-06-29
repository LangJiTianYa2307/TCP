import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
	private int port;

	public TcpServer(int port) {
		super();
		this.port = port;
		Server server = new Server();
		Thread t = new Thread(server);
		t.start();
	}
	
	class Server implements Runnable{
		
		@Override
		public void run() {
			ServerSocket ss = null;
			BufferedReader br = null;
			try {
				ss = new ServerSocket(port);
				System.out.println(port + "服务器已经启动");
				Socket s = ss.accept();
				InetAddress ia = s.getInetAddress();
				InputStream in = s.getInputStream();
				br = new BufferedReader(new InputStreamReader(in));
				String line = null;
				while((line = br.readLine()) != null) {
					if("exit".equals(line)) {
						break;
					}
					System.out.println("ip地址:" + ia.getHostAddress() + ",接收的内容:" + line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(ss != null) {
					try {
						ss.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if( br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		new TcpServer(10001);
	}
}
