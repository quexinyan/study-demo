package demo.designpatterns.command;

/**
 * 
* @ClassName: StopCommand  
* @Description: 具体命令类-暂停  
* @author gaox  
* @date 2018年5月23日 下午5:25:21
 */
public class StopCommand implements Command{
	
	private AudioPlayer audioPlayer;
	
	public StopCommand(AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

	@Override
	public void excute() {
		audioPlayer.stop();
	}

}
