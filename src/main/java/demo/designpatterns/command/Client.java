package demo.designpatterns.command;

/**
 * 
* @ClassName: Client  
* @Description: 客户端  
* @author gaox  
* @date 2018年5月24日 上午9:53:18
 */
public class Client {

	public static void main(String[] args) {
		
		// 创建请求接受者
		AudioPlayer audioPlayer = new AudioPlayer();
		
		// 创建具体命令
		Command play = new PlayCommand(audioPlayer);
		Command rewind = new RewindCommand(audioPlayer);
		Command stop = new StopCommand(audioPlayer);
		
		// 创建请求者
		Keypad keypad = new Keypad();
		keypad.setPlayCommand(play);
		keypad.setRewindCommand(rewind);
		keypad.setStopCommand(stop);
		
		// 测试
		keypad.play();
		keypad.rewind();
		keypad.stop();
	}
}
