package demo.designpatterns.command;

/**
 * 
* @ClassName: ClientMacro  
* @Description: 宏命令测试 
* @author gaox  
* @date 2018年5月24日 上午11:22:02
 */
public class ClientMacro {

	public static void main(String[] args) {
		
		// 创建命令接受者
		AudioPlayer audioPlayer = new AudioPlayer();
		
		// 创建具体命令
		Command playCommand = new PlayCommand(audioPlayer);
		Command rewindCommand = new RewindCommand(audioPlayer);
		Command stopCommand = new StopCommand(audioPlayer);
		
		// 将具体命令加入宏命令
		MacroCommand macroCommand = new MacroAudioCommand();
		macroCommand.add(playCommand);
		macroCommand.add(rewindCommand);
		macroCommand.add(stopCommand);
		
		// 通过宏命令去执行
		macroCommand.excute();
	}
}
