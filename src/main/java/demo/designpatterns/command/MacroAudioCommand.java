package demo.designpatterns.command;

import java.util.ArrayList;
import java.util.List;

/**
 * 
* @ClassName: MacroAudioCommand  
* @Description: 具体的宏命令  
* @author gaox  
* @date 2018年5月24日 上午11:19:46
 */
public class MacroAudioCommand implements MacroCommand{
	
	private List<Command> list = new ArrayList<Command>();

	// 执行方法
	@Override
	public void excute() {
		// TODO Auto-generated method stub
		for(Command command : list) {
			command.excute();
		}
	}

	@Override
	public void add(Command command) {
		// TODO Auto-generated method stub
		list.add(command);
	}

	@Override
	public void remove(Command command) {
		// TODO Auto-generated method stub
		list.remove(command);
	}

}
