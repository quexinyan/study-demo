package demo.designpatterns.command;

/**
 * 
* @ClassName: PlayCommand  
* @Description: 具体命令类-播音  
* @author gaox  
* @date 2018年5月23日 下午5:23:28
 */
public class PlayCommand implements Command{
	
	private AudioPlayer audioPlayer;
	
	public PlayCommand(AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

	@Override
	public void excute() {
		audioPlayer.play();
	}

}
