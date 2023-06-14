const mysql = require('mysql2/promise');

exports.pool = mysql.createPool({
  host: 'localhost',
  user: 'root',
  password: 'root',
  database: 'node-auth',
});
