package demo.designpatterns.command;

/**
 * 
* @ClassName: RewindCommand  
* @Description: 具体命令类-倒带  
* @author gaox  
* @date 2018年5月23日 下午5:24:31
 */
public class RewindCommand implements Command{
	
	private AudioPlayer audioPlayer;
	
	public RewindCommand(AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
	}
	
	@Override
	public void excute() {
		audioPlayer.rewind();
	}
}
