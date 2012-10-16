package www.indocoding.com;

import www.aplysit.com.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


public class Achat extends Activity {
	
    private ArrayAdapter<String> ChatArrayAdapter;
    private ArrayAdapter<String> friendAdapter;
    private ArrayAdapter<String> friendOff;
    private ArrayAdapter<String> menulist;
    
    //layout view
    private EditText msgOut;
    private EditText userIn;
    private EditText passIn;
    private EditText username;
    private EditText pass;
    private EditText email;
    private EditText nick;
    private EditText roomName;
    
    private AchatService chatService = null;
	
    //Message types sent from the AChatService Handler
    public static final int MESSAGE_READ = 1;
    public static final int MESSAGE_ONFRIEND = 3;
    public static final int MESSAGE_OFFFRIEND = 4;
    public static final int MESSAGE_SIGNIN_FAIL = 5;
    public static final int MESSAGE_SIGNIN_SUCC = 6;
    public static final int MESSAGE_SIGNOUT = 7;
    public static final int MESSAGE_REGISTER = 8;

    //local variable to hold temp data
    private String sendMsg;
    private String friend;
    private String pwd;
    private String user;
    private String mail;
    private String nickname;
    private boolean loginStatus = false;
    private boolean roomStatus = false;
    private String roomC;
    
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.startup);
        setupMenu();
    }
    
    @Override
    public void onStart() {
        super.onStart();
        chatService = new AchatService(this, mHandler);
       
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.mainmenu:
        	if (loginStatus == true) {
        		setContentView(R.layout.startup);
        		setupMenu2();
        	} else Toast.makeText(getApplicationContext(), "you are not sign in yet", 5).show();
        	return true;
        	}
        return false;
    }

    private void setupMenu(){
    	// Menyusun menu 
        String[] menuList = new String[]{"Login","Registrasi","Perihal","Exit"};
    	// Find and set up the ListView online friends
    	menulist = new ArrayAdapter<String>(this, R.layout.menu,menuList);
        ListView menuq = (ListView) findViewById(R.id.menu1);
        menuq.setAdapter(menulist);
        menuq.setOnItemClickListener(menuClickListener);
    }

    private void setupMenu2(){
    	// Menyusun menu 
        String[] menuList = new String[]{"Online Friends","Offline Friends","Room","Sign Out"};
    	// Find and set up the ListView online friends
    	menulist = new ArrayAdapter<String>(this, R.layout.menu,menuList);
        ListView menu2 = (ListView) findViewById(R.id.menu1);
        menu2.setAdapter(menulist);
        menu2.setOnItemClickListener(menuClickListener);
    }

    private void setupRoom(){
    	// Find and set up the chatRoom
    	friendAdapter = new ArrayAdapter<String>(this, R.layout.friend_temp);
        ListView cRoom = (ListView) findViewById(R.id.chatRoom);
        cRoom.setAdapter(friendAdapter);
        cRoom.setOnItemClickListener(roomClickListener);
    }
     
    private void setupChat() {
    	 //Initialize the array adapter for the conversation thread
        ChatArrayAdapter = new ArrayAdapter<String>(this, R.layout.message_temp);
        ListView chatView = (ListView) findViewById(R.id.listView_msg);
        chatView.setAdapter(ChatArrayAdapter);
        msgOut = (EditText) findViewById(R.id.editText_sendmsg);
        
    }
    
    private void onFriendSetup() {
    	// Find and set up the ListView online friends
    	friendAdapter = new ArrayAdapter<String>(this, R.layout.friend_temp);
        ListView online = (ListView) findViewById(R.id.online_list);
        online.setAdapter(friendAdapter);
        online.setOnItemClickListener(friendClickListener);
    }
   
    private void offFriendSetup() {
        // Find and set up the ListView offline friends
    	friendOff = new ArrayAdapter<String>(this, R.layout.friend_off);
        ListView offline = (ListView) findViewById(R.id.offline_list);
        offline.setAdapter(friendOff);
        offline.setOnItemClickListener(friendClickListener);
    }
    

    //Button handler
    public void clickHandler(View view) {
    	switch (view.getId()){
    	
    	case R.id.button_Login:
    		userIn = (EditText) findViewById (R.id.userName);
    		passIn = (EditText) findViewById (R.id.password);
    		user = userIn.getText().toString();
    		pwd = passIn.getText().toString();
    		chatService.login(user, pwd);
    		break;
    		
    	case R.id.completeReg_button:
    		username = (EditText) findViewById (R.id.regname);
    		email = (EditText) findViewById (R.id.email);
    		pass = (EditText) findViewById (R.id.password);
    		nick = (EditText) findViewById (R.id.nickName);
    		user = username.getText().toString();
    		pwd = pass.getText().toString();
    		mail = email.getText().toString();
    		nickname = nick.getText().toString();
    		chatService.regUser(user, mail, pwd, nickname,"user");
    		setContentView(R.layout.startup);
    		setupMenu();
    		break;
    		
    	case R.id.send_button:
    		String msgIn = msgOut.getText().toString();
            sendMsg = msgIn.replace(' ', '~');
            if (roomStatus == true) chatService.sendMessage(sendMsg, roomC, user, "room");
            else
            	chatService.sendMessage(sendMsg, friend, user, "");
			ChatArrayAdapter.add("Me : "+msgIn);
            sendMsg="";
            msgOut.setText("");
            break;
            
            
            
    	}
    }

    //The on-click listener for all friends in the ListViews
    private OnItemClickListener friendClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
        	setContentView(R.layout.message);
        	friend = ((TextView) v).getText().toString(); 
        	setupChat();
        	chatService.getMsg(friend, user,"person","run");
        	roomStatus = false;
        }
    };
    
    private OnItemClickListener menuClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
        	setContentView(R.layout.message);
        	String option = ((TextView) v).getText().toString(); 
        	
        	if (option.equals("Registrasi")) setContentView(R.layout.register);
    
        	else if (option.equals("Login")) setContentView(R.layout.signin);
        	
        	else if (option.equals("Exit")) {
        		finish();
        		}
        	else if (option.equals("Online Friends")) {
        		setContentView(R.layout.list);
        		onFriendSetup();
            	chatService.findFriend(user,"on");
            	}
        	else if (option.equals("Offline Friends")) {
        		setContentView(R.layout.oflist);
        		offFriendSetup();
            	chatService.findFriend(user,"off");
            }
        	else if (option.equals("Room")) {
        		setContentView(R.layout.room);
        		setupRoom();
            	chatService.findFriend(user,"room");
            }
        	else if (option.equals("Sign Out")) {
        		chatService.setlogout(user);
        		setContentView(R.layout.startup);
        		setupMenu();
        	}
        }
    };
    
  //The on-click listener for all room in the ListViews
    private OnItemClickListener roomClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
        	setContentView(R.layout.message);
        	friend = ""; 
        	roomC = ((TextView) v).getText().toString();
          	setupChat();
          	chatService.getMsg(roomC, user,"room", "run");
          	roomStatus = true;
        }
    };
    
    //The thread Handler that gets information from the Services and interact w/ UI
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_READ:
                String readMessage = new String(msg.obj.toString());
                ChatArrayAdapter.add(readMessage);
                break;
            case MESSAGE_SIGNIN_SUCC:
            	loginStatus = true;
            	setContentView(R.layout.startup);
            	setupMenu2();
            	break;
            case MESSAGE_SIGNIN_FAIL:
            	Toast.makeText(getApplicationContext(), "Sorry, your User and Password is not correct!", 15).show();
            	break;
            case MESSAGE_ONFRIEND:
            	String friendlist = new String(msg.obj.toString());
            	friendAdapter.add(friendlist);
            	break;
            case MESSAGE_OFFFRIEND:
            	String offfriendlist = new String(msg.obj.toString());
            	friendOff.add(offfriendlist);
            	break;
            case MESSAGE_REGISTER:
            	String pesan = new String(msg.obj.toString());
            	Toast.makeText(getApplicationContext(), pesan, 10).show();
            
            }
        }
    };
}