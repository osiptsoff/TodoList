package ru.todolist.rest.repository;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.todolist.rest.model.User;

@Component
public class JdbcUserDAO implements DAO<User> {
	private final JdbcTemplate jdbcTemplate;
	private final BeanPropertyRowMapper<User> mapper = new BeanPropertyRowMapper<>(User.class);
	
	@Autowired
	public JdbcUserDAO(JdbcTemplate jdbcTemplate) throws DataAccessException {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<User> getAll() {
		return jdbcTemplate.query("SELECT iduser, login, password FROM user", mapper);
	}

	@Override
	public User getById(int id) throws DataAccessException {
		final int[] types = new int[] {Types.INTEGER};
		
		return jdbcTemplate.queryForObject(
				"SELECT iduser, login, password FROM user WHERE iduser = ?",
				new Object[] {id}, types, mapper);
	}

	@Override
	public int insert(User obj) throws DataAccessException {
		Object[] values = new Object[] {obj.getLogin(), obj.getPassword() };
		final int[] types = new int[] { Types.VARCHAR, Types.VARCHAR };
		
		return jdbcTemplate.update("INSERT INTO user VALUES (default, ?, ?)",
				values, types);
	}

	@Override
	public int update(User obj) throws DataAccessException {
		Object[] values = new Object[] {obj.getLogin(), obj.getPassword(), obj.getIduser() };
		final int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER };
		
		return jdbcTemplate.update("UPDATE user SET login = ?, password = ? WHERE iduser = ?",
				values, types);
	}

	@Override
	public int removeById(int id) throws DataAccessException {
		final int[] types = new int[] {Types.INTEGER};
		
		return jdbcTemplate.update("DELETE FROM user WHERE iduser = ?",
				new Object[] {id}, types);
	}

	@Override
	public int remove(User obj) throws DataAccessException {
		return removeById(obj.getIduser());
	}

}
