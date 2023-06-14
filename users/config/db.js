const mysql = require('mysql2/promise');

exports.pool = mysql.createPool({
  host: '34.101.219.120',
  user: 'root',
  password: 'dbpassword',
  database: 'users',
});
