const Sequelize = require("sequelize");

const sequelize = new Sequelize('db_name', 'db_user', 'db_password', {
    host: 'db_host',
    dialect: 'mysql',
  });

module.exports = sequelize;