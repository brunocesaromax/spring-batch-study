package com.springbatch.skipexception.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.skipexception.dominio.Cliente;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class SkipExceptionReaderConfig {
	@Bean
	public ItemReader<Cliente> skipExceptionReader(@Qualifier("appDataSource") DataSource dataSource) {
		return new JdbcCursorItemReaderBuilder<Cliente>()
				.name("skipExceptionReader")
				.dataSource(dataSource)
				.sql("select * from cliente")
				.rowMapper(rowMapper())
				.build();
	}

	private RowMapper<Cliente> rowMapper() {
		return new RowMapper<Cliente>() {
			@Override
			public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
				if (resultSet.getRow() == 11) { // Numero de clientes no banco de dados
					throw new SQLException(String.format("Encerrando a execução - Cliente inválido %s",
							resultSet.getString("email")));
				}

				return clienteRowMapper(resultSet);
			}
		};
	}

	private Cliente clienteRowMapper(ResultSet rs) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setNome(rs.getString("nome"));
		cliente.setSobrenome(rs.getString("sobrenome"));
		cliente.setIdade(rs.getString("idade"));
		cliente.setEmail(rs.getString("email"));

		return cliente;
	}
}
