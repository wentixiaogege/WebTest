package jacktest.mysql.jdbc;

import java.util.ArrayList;
import java.util.List;

import com.itu.bean.Command;

public class JDBCCommandTableAccess extends JDBCBaseDAO<Command>{

	 // 添加员工信息的操作
    public boolean addCommand(final Command Command) throws Exception {
        save(Command);
        return true;
    }

    // 将员工信息添加到表格中
    public List<Command> addCommand(int id) throws Exception {
        List<Command> lstCommand = new ArrayList<Command>();
        Command command = findById(id);
        // 将当前封转好的数据装入对象中
        lstCommand.add(command);
        return lstCommand;
    }

    public void deleteCommand(final Command entity) throws Exception {
        this.delete(entity);
    }

    public void updateCommand(final Command entity) throws Exception {
        this.update(entity);
    }

}
