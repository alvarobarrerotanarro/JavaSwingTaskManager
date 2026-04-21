# Java Swing Task Manager

## Deployment

Es necesario que la app este conectada a una base de datos con los esquemas adecuados. La configuracion de conexion a la base de datos se puede establecer desde el fichero `app_config.json`.

### Ejemplo de configuracion de una BBDD

Para este ejemplo de configuracion de la base de datos para la aplicacion utilizare docker, pues es la opcion mas facil y rapida.

- **Clonar la imagen**:

```sh
docker pull mysql:latest
```

- **Crear el contenedor**:

```sh
docker run --name mi-contenedor -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:latest
```

- **Poblar con el esquema**:

A continuacion entramos al contenedor utilizando la consola de mysql.

```sh
docker exec -it mi-contenedor mysql -uroot -p
```

A continuacion en la consola interactiva de mysql, podemos hacer referencia a un fichero script. En tal fichero script debemos pegar las sentencias SQL que se mencionan a continuacion.

```mysql
mysql> SOURCE ./ruta/al/script.sql
```

Sentencias SQL:

```sql
CREATE TABLE IF NOT EXISTS subject (
  subject_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS teacher (
  teacher_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS task (
  task_id INT PRIMARY KEY AUTO_INCREMENT,
  subject_id INT REFERENCES subject(subject_id),
  teacher_id INT REFERENCES teacher(teacher_id),
  title VARCHAR(64) DEFAULT '' NOT NULL,
  description VARCHAR(256) DEFAULT '' NOT NULL,
  time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP() NOT NULL,
  done BOOLEAN DEFAULT false,
  deadline TIMESTAMP DEFAULT (TIMESTAMPADD(DAY, 1, CURRENT_TIMESTAMP())) NOT NULL,
  priority VARCHAR(64) DEFAULT 'low' CHECK (priority IN ('low', 'medium', 'high')) NOT NULL
);
```
