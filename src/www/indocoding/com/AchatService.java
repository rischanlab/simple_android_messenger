package www.indocoding.com;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

//public class AchatService extends Activity{
public class AchatService {
	
	//gunakan ini jika Anda ingin mencoba secara localhost
	private String setURL = "http://10.0.2.2/si";
	
	//gunakan ini jika Anda ingin mencoba secara online
	//private String setURL = "http://www.aplysit.com/achat";
	
	private friendList fl;
	private sendMsg sndMsg;
	private getMsg getmsg;
	private register reg;
	private signIn si;
	private signOut so;
	private Handler mhandler;
	
	public AchatService (Context context, Handler handler){
		mhandler = handler;
	}
	
	private InputStream OpenHttpConnection(String urlString) throws IOException
    {
        InputStream in = null;
        int response = -1;
               
        URL url = new URL(urlString); 
        URLConnection conn = url.openConnection();
    	//Toast.makeText(getBaseContext(), url.toString(), Toast.LENGTH_SHORT).show();
    	  
        if (!(conn instanceof HttpURLConnection))                     
            throw new IOException("Not an HTTP connection");
        
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect(); 

            response = httpConn.getResponseCode();                 
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();                                 
            }                     
        }
        catch (Exception ex)
        {
            throw new IOException("Error connecting");            
        }
        return in;     
    }
    
    //register new user
    private String registerPHP(String n, String e,String p, String k, String s) 
    {
    	String url = setURL+"/Messenger.php?c=r&n="+n+"&k="+k+"&p="+p+"&e="+e+"&s="+s;
    	int BUFFER_SIZE = 10;
    	InputStream in = null;
    	int charRead;
        String str = "";
    	
    	try {
            in = OpenHttpConnection(url);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return "";
        }
        InputStreamReader isr = new InputStreamReader(in);
        
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
        	while ((charRead = isr.read(inputBuffer))>0) {
        		//--convert chars to string
        		String readString = String.copyValueOf(inputBuffer, 0, charRead);
        		str += readString;
        		inputBuffer = new char [BUFFER_SIZE];
        	}
        	in.close();
        }
        catch (IOException ex) {
        	ex.printStackTrace();
        	return "";
        }
        return str;
    }

    //set status sign in
    private String signinPHP(String k,String p ) 
    {
    	String url = setURL+"/Messenger.php?c=s&k="+k+"&p="+p;
    	int BUFFER_SIZE = 10;
    	InputStream in = null;
    	int charRead;
        String str = "";
    	
    	try {
            in = OpenHttpConnection(url);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return "";
        }
        InputStreamReader isr = new InputStreamReader(in);
        
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
        	while ((charRead = isr.read(inputBuffer))>0) {
        		//--convert chars to string
        		String readString = String.copyValueOf(inputBuffer, 0, charRead);
        		str += readString;
        		inputBuffer = new char [BUFFER_SIZE];
        	}
        	in.close();
        }
        catch (IOException ex) {
        	ex.printStackTrace();
        	return "";
        }
        return str;
    }

    //find other user
    private String friendPHP(String k, String z) 
    {
    	String url;
    	if (z.equals("room"))url = setURL+"/Messenger.php?c=a&k="+k;
    	else
    		url = setURL+"/Messenger.php?c=f&k="+k+"&z="+z;
    	int BUFFER_SIZE = 10;
    	InputStream in = null;
    	int charRead;
        String str = "";
    	
    	try {
            in = OpenHttpConnection(url);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return "";
        }
        InputStreamReader isr = new InputStreamReader(in);
        
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
        	while ((charRead = isr.read(inputBuffer))>0) {
        		//--convert chars to string
        		String readString = String.copyValueOf(inputBuffer, 0, charRead);
        		str += readString;
        		inputBuffer = new char [BUFFER_SIZE];
        	}
        	in.close();
        }
        catch (IOException ex) {
        	ex.printStackTrace();
        	return "";
        }
        return str;
    }
    
    //set status logout
    private String logout(String k) 
    {
    	String url = setURL+"/Messenger.php?c=o&k="+k;
    	int BUFFER_SIZE = 10;
    	InputStream in = null;
    	int charRead;
        String str = "";
    	
    	try {
            in = OpenHttpConnection(url);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return "";
        }
        InputStreamReader isr = new InputStreamReader(in);
        
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
        	while ((charRead = isr.read(inputBuffer))>0) {
        		//--convert chars to string
        		String readString = String.copyValueOf(inputBuffer, 0, charRead);
        		str += readString;
        		inputBuffer = new char [BUFFER_SIZE];
        	}
        	in.close();
        }
        catch (IOException ex) {
        	ex.printStackTrace();
        	return "";
        }
        return str;
    }
    
    //send message
    private String sendMessagePHP(String m, String t, String n, String r) 
    {
    	String url;
    	
    	if (r.equals("room")) url = setURL+"/Messenger.php?c=m&u="+n+"&r="+t+"&m="+m;
    	else url = setURL+"/Messenger.php?c=k&n="+n+"&t="+t+"&m="+m;
    	
    
    	int BUFFER_SIZE = 10;
    	InputStream in = null;
    	int charRead;
        String str = "";
    	
    	try {
            in = OpenHttpConnection(url);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return "";
        }
        InputStreamReader isr = new InputStreamReader(in);
        
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
        	while ((charRead = isr.read(inputBuffer))>0) {
        		//--convert chars to string
        		String readString = String.copyValueOf(inputBuffer, 0, charRead);
        		str += readString;
        		inputBuffer = new char [BUFFER_SIZE];
        	}
        	in.close();
        }
        catch (IOException ex) {
        	ex.printStackTrace();
        	return "";
        }
        return str;
    }
    
    //get incoming message
    private String getMessagePHP(String a, String n, String r) 
    {
    	String url = setURL+"/Messenger.php?c=p&n="+n+"&a="+a+"&r="+r;
    	int BUFFER_SIZE = 10;
    	InputStream in = null;
    	int charRead;
        String str = "";
    	
    	try {
            in = OpenHttpConnection(url);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return "";
        }
        InputStreamReader isr = new InputStreamReader(in);
        
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
        	while ((charRead = isr.read(inputBuffer))>0) {
        		//--convert chars to string
        		String readString = String.copyValueOf(inputBuffer, 0, charRead);
        		str += readString;
        		inputBuffer = new char [BUFFER_SIZE];
        	}
        	in.close();
        }
        catch (IOException ex) {
        	ex.printStackTrace();
        	return "";
        }
        return str;
    }

    
    //Achat Services that access by main program
    public void sendMessage(String msg, String to, String from, String r){
    	sndMsg = new sendMsg(msg,to,from,r);
    	sndMsg.start();
    }
    
    public void login(String usr, String pwd) {
    	si = new signIn(usr,pwd);
    	si.start();
    }
    
    public void findFriend (String usr, String cnd) {
    	fl = new friendList (usr, cnd);
    	fl.start();
    }
    
    public void getMsg (String friend, String user,String type, String run) {
    	getmsg = new getMsg (friend, user, type);
    	if (run.equals("run")) {
    		getmsg.setRunning(1);		// will always run in background
    		getmsg.start();
    		
    	}
    	else if (run.equals("stop")) {}
			
			
    		
    }
    
    public void regUser (String n, String e, String p, String k, String s) {
    	reg = new register (n,e,p,k,s);
    	reg.start();
    }
    
    public void setlogout (String u) {
    	so = new signOut(u);
    	so.start();
    }
   
    
    /*
     * All connection to server will be handle by thread,
     * to avoid a death lock
     */
    
    //handle connection of sending message
	private class sendMsg extends Thread {
		private String msg;
		private String to;
		private String from;
		private String r;
		
		public sendMsg (String m, String t, String n, String r){
			msg = m;
			to = t;
			from = n;
			this.r = r;
		}
		
		public void run() {
			try {
    			//Panggil method
    			sendMessagePHP(msg,to,from, r);
    		}
    		catch(Exception err) { }
		}
	}
	
	//handle connection of checking incoming message
	private class getMsg extends Thread {
		private String to;
		private String from;
		private int running = 0;
		private String msge="";
		private String type;
		
		
		public getMsg (String a, String n, String r){
			to = n;
			from = a;
			type=r;
		}
		
		void setRunning (int b) {
			this.running = b;
		}
		
		public void run() {
			String rs = "";
			while (running != 0) {
				try {
					//Panggil method
					rs = getMessagePHP(from,to,type);
					System.out.println("isi rs: "+rs);
					char[] msg = rs.toCharArray();
					//share msg to UI
					if (msg.length > 0) {
						for (int i = 0; i < msg.length; i++) {
						//seperate msg from server based on its order
						if (msg[i] == '_') {
							mhandler.obtainMessage(Achat.MESSAGE_READ, msge).sendToTarget();
							msge ="";
						}else msge +=msg[i];
						} 
					}
					//sleep time
					sleep(2500);
				}
				catch(Exception err) {}
			}
		}
	}
	
	//handle conection of register new user
	private class register extends Thread {
		private String name;
		private String email;
		private String password;
		private String nick;
		private String status;
		
		public register (String n, String e, String p, String k, String r) {
			name = n;
			email = e;
			password = p;
			nick = k;
			status = r;
		}
		
		public void run() {
			String rs = "";
       		try {
       			rs=registerPHP(name,email,password, nick,status);
       			mhandler.obtainMessage(Achat.MESSAGE_REGISTER, rs).sendToTarget();
       		}
       		catch(Exception err) {}
		}
	}
	
	//handle connection of signIn process
	private class signIn extends Thread {
		private String pwd;
		private String user;
		
		public signIn (String usr, String pass) {
			pwd = pass;
			user = usr;
		}
		public void run() {
			String rs="";
    		try {
    			// Panggil method siginPHP
    			rs = signinPHP(user,pwd);
    			System.out.println("isi dari rs "+rs);
    			
    			if(rs.equals("1") ){
    				mhandler.obtainMessage(Achat.MESSAGE_SIGNIN_SUCC).sendToTarget();
    			}
    			else {
    				mhandler.obtainMessage(Achat.MESSAGE_SIGNIN_FAIL).sendToTarget();
    			}
    		}
    		catch(Exception err) {
    			
    		}
    	}
	}
	
	//handle connection of signOut process
	private class signOut extends Thread {
		private String user;
		
		public signOut (String u) {
			user = u;
		}
		
		public void run() {
			try {
				logout(user);
				mhandler.obtainMessage(Achat.MESSAGE_SIGNOUT);
			}
			catch(Exception err) {}
		}
	}
	
	//handle connection of looking online friend
	private class friendList extends Thread {
		private String user;
		private String friend ="";
		private String find;
		
		public friendList (String usrName, String fnd) {
			user = usrName;
			find = fnd;
		}
		
		public void run() {
			String rs="";
        	try {
        		//Panggil method friendPHP
        		rs = friendPHP(user, find);
        		char[] listFriend = rs.toCharArray();
        		if (listFriend[0]=='1') {
        			for (int i =1; i < listFriend.length; i++) {
        				if (listFriend[i] == '_') {
        					mhandler.obtainMessage(Achat.MESSAGE_ONFRIEND, friend).sendToTarget();
        					friend = "";
        				}
        				else friend += listFriend[i];
        			}
        		} else {
        			for (int i =1; i < listFriend.length; i++) {
        				if (listFriend[i] == '_') {
        					mhandler.obtainMessage(Achat.MESSAGE_OFFFRIEND, friend).sendToTarget();
        					friend = "";
        				}
        				else friend += listFriend[i];
        			}
        		}
        	}
        	catch(Exception err) {
        	}
        }
	}
	
}