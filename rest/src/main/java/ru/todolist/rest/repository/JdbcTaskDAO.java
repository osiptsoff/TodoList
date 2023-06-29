package ru.todolist.rest.repository;

import java.sql.Date;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.todolist.rest.model.Task;


@Component
public class JdbcTaskDAO implements TaskDAO {
	private final JdbcTemplate jdbcTemplate;
	private final BeanPropertyRowMapper<Task> mapper = new BeanPropertyRowMapper<>(Task.class);
	
	@Autowired
	public JdbcTaskDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Task> getAll() throws DataAccessException {
		return jdbcTemplate.query("SELECT idtask, description, duedate, actual, iduser FROM task", mapper);
	}

	//@Override
	public Task getById(int id) throws DataAccessException {
		final int[] types = new int[] {Types.INTEGER};
		
		return jdbcTemplate.queryForObject(
				"SELECT idtask, description, duedate, actual, iduser FROM task WHERE idtask = ?",
				new Object[] {id}, types, mapper);
	}

	@Override
	public int insert(Task obj) throws DataAccessException {
		Object[] values = new Object[] {obj.getDescription(),
				Date.valueOf(obj.getDuedate()), obj.getActual(), obj.getIduser()};
		final int[] types = new int[] {Types.NVARCHAR, Types.DATE, Types.BIT, Types.INTEGER};
		
		return jdbcTemplate.update("INSERT INTO task VALUES(default, ?, ?, ?, ?)",
				values, types);
	}
	
	@Override
	public int update(Task obj) throws DataAccessException {
		Object[] values = new Object[] {obj.getDescription(),
				Date.valueOf(obj.getDuedate()), obj.getActual(), obj.getIdtask()};
		final int[] types = new int[] {Types.NVARCHAR, Types.DATE, Types.BIT, Types.INTEGER};
		
		return jdbcTemplate.update("UPDATE task SET description = ?, duedate = ?, actual = ? WHERE idtask = ?",
				values, types);
	}

	@Override
	public int removeById(int id) throws DataAccessException {
		final int[] types = new int[] {Types.INTEGER};
		
		return jdbcTemplate.update("DELETE FROM task WHERE idtask = ?",
				new Object[] {id}, types);
	}
	
	@Override
	public int remove(Task obj) {
		return removeById(obj.getIdtask());
	}
	
	@Override
	public List<Task> getTasksOfUser(int iduser) throws DataAccessException {
		final int[] types = new int[] {Types.INTEGER};
		
		return jdbcTemplate.query(
				"SELECT idtask, description, duedate, actual, iduser FROM task WHERE iduser = ?",
				new Object[] {iduser}, types, mapper);
	}

	@Override
	public List<Task> getTasksOfUserByActuality(int iduser, boolean actual) throws DataAccessException {
		final int[] types = new int[] {Types.INTEGER, Types.BIT};
		
		return jdbcTemplate.query(
				"SELECT idtask, description, duedate, actual, iduser FROM task WHERE iduser = ? AND actual = ?",
				new Object[] {iduser, actual}, types, mapper);
	}

	@Override
	public List<Task> getTasksOfUserByDate(int iduser, String date) throws DataAccessException, IllegalArgumentException {
		final int[] types = new int[] {Types.INTEGER, Types.DATE};
		
		return jdbcTemplate.query(
				"SELECT idtask, description, duedate, actual, iduser FROM task WHERE iduser = ? AND duedate = ?",
				new Object[] {iduser, Date.valueOf(date)}, types, mapper);
	}

	@Override
	public int removeTaskOfUserById(int iduser, int idtask) {
		final int[] types = new int[] {Types.INTEGER, Types.INTEGER};
		
		return jdbcTemplate.update("DELETE FROM task WHERE idtask = ? AND iduser = ?",
				new Object[] {idtask, iduser}, types);
	}

	@Override
	public int updateTaskOfUser(int iduser, Task task) {
		Object[] values = new Object[] {task.getDescription(),
				Date.valueOf(task.getDuedate()), task.getActual(), task.getIdtask(), iduser};
		final int[] types = new int[] {Types.NVARCHAR, Types.DATE, Types.BIT, Types.INTEGER, Types.INTEGER};
		
		return jdbcTemplate.update("""
				UPDATE task SET description = ?, duedate = ?, actual = ?
				WHERE idtask = ? AND iduser = ?""",
				values, types);
	}
}

