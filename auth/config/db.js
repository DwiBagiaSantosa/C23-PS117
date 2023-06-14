const mysql = require('mysql2/promise');

exports.pool = mysql.createPool({
  host: 'localhost',
  user: 'user',
  password: 'password',
  database: 'db_name',
});
