package demo.designpatterns.command;

/**
 * 
* @ClassName: Keypad  
* @Description: 请求发送者-键盘  
* @author gaox  
* @date 2018年5月23日 下午5:33:15
 */
public class Keypad {

	private Command playCommand;
	private Command rewindCommand;
	private Command stopCommand;
	
	public void setPlayCommand(Command playCommand) {
		this.playCommand = playCommand;
	}
	public void setRewindCommand(Command rewindCommand) {
		this.rewindCommand = rewindCommand;
	}
	public void setStopCommand(Command stopCommand) {
		this.stopCommand = stopCommand;
	}
	
	public void play() {
		playCommand.excute();
	}
	
	public void rewind() {
		rewindCommand.excute();
	}
	
	public void stop() {
		stopCommand.excute();
	}
}
