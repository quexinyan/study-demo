package demo.designpatterns.command;

/**
 * 
* @ClassName: AudioPlayer  
* @Description: 命令接受类  
* @author gaox  
* @date 2018年5月23日 下午5:26:19
 */
public class AudioPlayer {

	public void play() {
		System.out.println("播放...");
	}
	
	public void rewind() {
		System.out.println("倒带...");
	}
	
	public void stop() {
		System.out.println("暂停...");
	}
}
