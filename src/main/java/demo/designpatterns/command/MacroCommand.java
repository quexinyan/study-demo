package demo.designpatterns.command;

/**
 * 
* @ClassName: MacroCommand  
* @Description: 宏命令接口  
* @author gaox  
* @date 2018年5月24日 上午11:16:16
 */
public interface MacroCommand extends Command{

	// 宏命令聚集的管理方法-可以添加成员命令
	public void add(Command command);
	
	// 宏命令聚集的管理方法-可以移除成员命令
	public void remove(Command command);
}
